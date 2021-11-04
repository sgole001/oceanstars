package oceanstars.ecommerce.infrastructure.shiro.builder.impl;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.Filter;
import oceanstars.ecommerce.common.spring.ApplicationContextProvider;
import oceanstars.ecommerce.infrastructure.shiro.builder.ShiroBuilder;
import oceanstars.ecommerce.infrastructure.shiro.constant.ShiroConstant;
import oceanstars.ecommerce.infrastructure.shiro.filter.HttpRequestFilter;
import oceanstars.ecommerce.infrastructure.shiro.filter.MarriottShiroFilterFactoryBean;
import oceanstars.ecommerce.infrastructure.shiro.filter.RestInvalidRequestFilter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.web.filter.mgt.DefaultFilterChainManager;
import org.apache.shiro.web.filter.mgt.PathMatchingFilterChainResolver;
import org.apache.shiro.web.servlet.AbstractShiroFilter;
import org.springframework.stereotype.Component;

/**
 * 默认Shiro构建器
 *
 * @author Clover
 * @version 1.0.0
 * @since 2021/11/4 1:34 下午
 */
@Component
public class DefaultShiroBuilder implements ShiroBuilder {

  /**
   * 日志管理器
   */
  private final Logger logger = LogManager.getLogger(DefaultShiroBuilder.class.getName());

  @Override
  public List<Realm> buildRealms() {
    return null;
  }

  /**
   * 构建Shiro过滤器
   * <p>
   * 内置过滤器
   * <p>
   * a.认证过滤器 : anon, authc, authcBasic, user b.授权过滤器 : rest, port, perms, roles, ssl
   * <p>
   * 1. rest rest风格拦截器，自动根据请求方法构建权限字符串（GET=read, POST=create,PUT=update,DELETE=delete,HEAD=read,TRACE=read,OPTIONS=read, MKCOL=create ex:
   * /users=rest[user]
   * <p>
   * 2. port 端口拦截器 ex: /test=port[80]
   * <p>
   * 3. perms 权限授权拦截器，验证用户是否拥有所有权限 ex: /user/**=perms["user:create"]
   * <p>
   * 4. roles 角色授权拦截器，验证用户是否拥有所有角色 ex: /admin/**=roles[admin]
   * <p>
   * 5. anon 匿名拦截器，即不需要登录即可访问；一般用于静态资源过滤 ex: /static/**=anon
   * <p>
   * 6. authc 基于表单的拦截器 ex: /**=authc
   * <p>
   * 7. authcBasic Basic HTTP身份验证拦截器
   * <p>
   * 8. ssl SSL拦截器，只有请求协议是https才能通过；否则自动跳转会https端口（443）；其他和port拦截器一样；
   * <p>
   * 9. user 用户拦截器，用户已经身份验证/记住我登录的都可
   *
   * @return Shiro过滤器集合映射
   */
  @Override
  public Map<String, Filter> buildFilters() {

    // 过滤器集合映射
    Map<String, Filter> filters = new LinkedHashMap<>();
    // Rest请求安全过滤器(Global)
    filters.put(RestInvalidRequestFilter.FILTER_ALIAS, new RestInvalidRequestFilter());
    // Web请求过滤器(Global)
    filters.put(HttpRequestFilter.FILTER_ALIAS, new HttpRequestFilter());

    return filters;
  }

  /**
   * 构建Shiro过滤对象链
   *
   * @return Shiro过滤对象链集合映射
   */
  @Override
  public Map<String, String> buildFilterChain() {
    return null;
  }

  @Override
  public synchronized void reloadFilterChain() {

    try {
      // 构建Shiro过滤对象链
      final Map<String, String> filterChains = this.buildFilterChain();
      // 获取Shiro过滤服务配置接口
      final MarriottShiroFilterFactoryBean shiroFilterFactoryBean = ApplicationContextProvider.getBean(
          MarriottShiroFilterFactoryBean.class);
      // 获取Shiro过滤器对象
      AbstractShiroFilter shiroFilter = (AbstractShiroFilter) shiroFilterFactoryBean.getObject();
      // 重载Shiro拦截链信息发生异常，未能获取Shiro拦截器对象！
      if (null == shiroFilter) {
        logger.error(ShiroConstant.MSG_SHIRO_SYS_00001);
        return;
      }

      // 获取Shiro请求路径过滤解析器
      PathMatchingFilterChainResolver resolver = (PathMatchingFilterChainResolver) shiroFilter.getFilterChainResolver();
      // 获取Shiro过滤管理器
      DefaultFilterChainManager manager = (DefaultFilterChainManager) resolver.getFilterChainManager();
      // 清除拦截链配置
      shiroFilterFactoryBean.getFilterChainDefinitionMap().clear();
      // 清除拦截链配置
      manager.getFilterChains().clear();

      // 重新创建Global以外的拦截链配置
      filterChains.forEach((target, filter) -> {
        shiroFilterFactoryBean.getFilterChainDefinitionMap().put(target, filter);
        manager.createChain(target, filter.replace(" ", ""));
      });
      // 重新创建Global拦截链配置
      manager.createDefaultChain("/**");

    } catch (Exception e) {
      logger.error(ShiroConstant.MSG_SHIRO_SYS_00002, e);
    }
  }
}
