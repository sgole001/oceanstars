package oceanstars.ecommerce.infrastructure.shiro.filter;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import oceanstars.ecommerce.infrastructure.shiro.resolver.RestPathMatchingFilterChainResolver;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.filter.mgt.DefaultFilter;
import org.apache.shiro.web.filter.mgt.FilterChainManager;
import org.apache.shiro.web.filter.mgt.FilterChainResolver;
import org.apache.shiro.web.mgt.WebSecurityManager;
import org.apache.shiro.web.servlet.AbstractShiroFilter;
import org.springframework.beans.factory.BeanInitializationException;

/**
 * 自定义Shiro过滤服务配置接口
 *
 * @author Clover
 * @version 1.0.0
 * @since 2021/11/4 11:45 上午
 */
public class MarriottShiroFilterFactoryBean extends ShiroFilterFactoryBean {

  /**
   * 日志管理器
   */
  private final Logger logger = LogManager.getLogger(MarriottShiroFilterFactoryBean.class.getName());

  /**
   * 默认构造器
   */
  public MarriottShiroFilterFactoryBean() {
    // 初始化全局拦截器
    List<String> globalFilters = new ArrayList<>();
    // HTTP非法字符拦截
    globalFilters.add(DefaultFilter.invalidRequest.name());
    // Http请求拦截
    globalFilters.add(HttpRequestFilter.FILTER_ALIAS);
    super.setGlobalFilters(globalFilters);
    // 初始化拦截规则配置
    super.setFilterChainDefinitionMap(new LinkedHashMap<>());
  }

  @Override
  protected AbstractShiroFilter createInstance() {

    // 获取SecurityManager
    SecurityManager securityManager = super.getSecurityManager();

    if (securityManager == null) {
      throw new BeanInitializationException("SecurityManager property must be set.");
    }

    if (!(securityManager instanceof WebSecurityManager)) {
      throw new BeanInitializationException("The security manager does not implement the WebSecurityManager interface.");
    }

    // 创建过滤链管理实例
    FilterChainManager manager = super.createFilterChainManager();
    // 设定过滤链解析接口（自定义实现REST拦截）
    RestPathMatchingFilterChainResolver chainResolver = new RestPathMatchingFilterChainResolver();
    chainResolver.setFilterChainManager(manager);

    return new SpringShiroFilter((WebSecurityManager) securityManager, chainResolver);
  }

  /**
   * 沿用ShiroFilterFactoryBean#SpringShiroFilter
   */
  private static final class SpringShiroFilter extends AbstractShiroFilter {

    private SpringShiroFilter(WebSecurityManager webSecurityManager, FilterChainResolver resolver) {
      super();
      if (webSecurityManager == null) {
        throw new IllegalArgumentException("WebSecurityManager property cannot be null.");
      }
      super.setSecurityManager(webSecurityManager);
      if (resolver != null) {
        super.setFilterChainResolver(resolver);
      }
    }
  }
}
