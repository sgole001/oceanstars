package oceanstars.ecommerce.infrastructure.shiro.configuration;

import java.util.List;
import javax.annotation.Resource;
import oceanstars.ecommerce.infrastructure.shiro.builder.ShiroBuilder;
import oceanstars.ecommerce.infrastructure.shiro.filter.JwtSubjectFactory;
import oceanstars.ecommerce.infrastructure.shiro.filter.MarriottShiroFilterFactoryBean;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.mgt.DefaultSessionStorageEvaluator;
import org.apache.shiro.mgt.DefaultSubjectDAO;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.CollectionUtils;

/**
 * Shiro配置
 *
 * @author Clover
 * @version 1.0.0
 * @since 2021/11/4 1:32 下午
 */
@Configuration
public class ShiroConfig {

  @Resource
  private ShiroBuilder shiroBuilder;

  /**
   * 配置shiro过滤器
   *
   * @param securityManager Shiro安全管理器
   * @return shiro过滤器
   */
  @Bean(name = "MarriottShiroFilterFactoryBean")
  public ShiroFilterFactoryBean shiroFilter(SecurityManager securityManager) {

    // 1.定义shiroFactoryBean
    MarriottShiroFilterFactoryBean shiroFilterFactoryBean = new MarriottShiroFilterFactoryBean();
    // 2.设置securityManager
    shiroFilterFactoryBean.setSecurityManager(securityManager);
    // 3.拦截器配置
    shiroFilterFactoryBean.setFilters(shiroBuilder.buildFilters());
    // 4.拦截规则配置
    shiroFilterFactoryBean.setFilterChainDefinitionMap(shiroBuilder.buildFilterChain());

    return shiroFilterFactoryBean;
  }

  /**
   * 配置shiro安全管理器
   *
   * @return shiro安全管理器
   */
  @Bean
  public SecurityManager securityManager() {

    DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();

    DefaultSubjectDAO subjectDAO = (DefaultSubjectDAO) securityManager.getSubjectDAO();
    DefaultSessionStorageEvaluator evaluator = (DefaultSessionStorageEvaluator) subjectDAO.getSessionStorageEvaluator();
    securityManager.setSubjectFactory(new JwtSubjectFactory(evaluator));
    // 构建ShiroRealm
    final List<Realm> realms = shiroBuilder.buildRealms();
    if (!CollectionUtils.isEmpty(realms)) {
      securityManager.setRealms(shiroBuilder.buildRealms());
    }

    SecurityUtils.setSecurityManager(securityManager);

    return securityManager;
  }
}
