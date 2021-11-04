package oceanstars.ecommerce.infrastructure.druid.configuration;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import com.alibaba.druid.util.StringUtils;
import javax.sql.DataSource;
import oceanstars.ecommerce.infrastructure.druid.configuration.bean.StatViewServletConfBean;
import oceanstars.ecommerce.infrastructure.druid.configuration.bean.StatViewServletConfBean.ConfigKey;
import oceanstars.ecommerce.infrastructure.druid.configuration.bean.WebStatFilterConfBean;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

/**
 * 数据源配置
 *
 * @author Clover
 * @version 1.0.0
 * @since 2021/11/3 11:21 下午
 */
@Configuration
public class DataSourceConfig {

  /**
   * 日志管理器
   */
  private Logger logger = LogManager.getLogger(DataSourceConfig.class.getName());

  /**
   * 获取Druid监控后端管理Servlet配置
   *
   * @return Druid监控后端管理Servlet配置
   */
  @ConfigurationProperties(prefix = "druid.stat-view-servlet")
  @Bean(name = "statViewServlet")
  public StatViewServletConfBean getStatViewServletConf() {
    return new StatViewServletConfBean();
  }

  /**
   * 获取Web监控过滤配置
   *
   * @return Web监控过滤配置
   */
  @ConfigurationProperties(prefix = "druid.web-stat-filter")
  @Bean(name = "webStatFilter")
  public WebStatFilterConfBean getWebStatFilterConf() {
    return new WebStatFilterConfBean();
  }

  /**
   * Druid数据源配置
   *
   * @return 数据源配置
   */
  @Primary
  @ConfigurationProperties(prefix = "druid")
  @Bean(name = "dataSource")
  public DataSource druidDataSource() {

    return DataSourceBuilder.create().type(DruidDataSource.class).build();
  }

  /**
   * Druid监控服务配置
   *
   * @return 监控服务配置
   */
  @Bean
  @ConditionalOnProperty(name = "druid.stat-view-servlet.enabled", havingValue = "true")
  public ServletRegistrationBean<StatViewServlet> druidServlet(StatViewServletConfBean statViewServlet) {

    // Druid监控服务Servlet注册
    ServletRegistrationBean<StatViewServlet> registrationBean = new ServletRegistrationBean<>();
    registrationBean.setServlet(new StatViewServlet());

    // 服务URL
    registrationBean.addUrlMappings(statViewServlet.getUrlPattern());
    // 服务登录用户名
    if (!StringUtils.isEmpty(statViewServlet.getLoginUsername())) {
      registrationBean.addInitParameter(ConfigKey.LOGIN_USER_NAME.getName(), statViewServlet.getLoginUsername());
    }
    // 服务登录密码
    if (!StringUtils.isEmpty(statViewServlet.getLoginPassword())) {
      registrationBean.addInitParameter(ConfigKey.LOGIN_PASSWORD.getName(), statViewServlet.getLoginPassword());
    }
    // 服务访问控制：允许
    registrationBean.addInitParameter(ConfigKey.ALLOW.getName(), statViewServlet.getAllow());
    // 服务访问控制：拒绝
    if (!StringUtils.isEmpty(statViewServlet.getDeny())) {
      registrationBean.addInitParameter(ConfigKey.DENY.getName(), statViewServlet.getDeny());
    }
    // 允许清空统计数据标记位
    if (statViewServlet.getResetEnable() != null) {
      registrationBean.addInitParameter(ConfigKey.RESET_ENABLE.getName(), statViewServlet.getResetEnable().toString());
    }

    return registrationBean;
  }

  @Bean
  @ConditionalOnProperty(name = "druid.web-stat-filter.enabled", havingValue = "true")
  public FilterRegistrationBean<WebStatFilter> druidWebStatFilter(WebStatFilterConfBean webStatFilter) {

    // Druid Web过滤规则注册
    FilterRegistrationBean<WebStatFilter> registrationBean = new FilterRegistrationBean<>();
    registrationBean.setFilter(new WebStatFilter());

    // URL过滤规则
    registrationBean.addUrlPatterns(webStatFilter.getUrlPattern());
    // URL排除规则
    registrationBean.addInitParameter(WebStatFilterConfBean.ConfigKey.EXCLUSIONS.getName(), webStatFilter.getExclusions());
    // 用户信息保存至Cookie中的名字
    if (!StringUtils.isEmpty(webStatFilter.getPrincipalCookieName())) {
      registrationBean.addInitParameter(WebStatFilterConfBean.ConfigKey.PRINCIPAL_COOKIE_NAME.getName(), webStatFilter.getPrincipalCookieName());
    }
    // 用户信息保存至Session中的名字
    if (!StringUtils.isEmpty(webStatFilter.getPrincipalSessionName())) {
      registrationBean.addInitParameter(WebStatFilterConfBean.ConfigKey.PRINCIPAL_SESSION_NAME.getName(), webStatFilter.getPrincipalSessionName());
    }
    // 监控单个url调用的sql列表
    if (webStatFilter.getProfileEnable() != null) {
      registrationBean.addInitParameter(WebStatFilterConfBean.ConfigKey.PROFILE_ENABLE.getName(), webStatFilter.getProfileEnable().toString());
    }
    // session统计功能
    if (webStatFilter.getSessionStatEnable() != null) {
      registrationBean.addInitParameter(WebStatFilterConfBean.ConfigKey.SESSION_STAT_ENABLE.getName(),
          webStatFilter.getSessionStatEnable().toString());
    }
    // session统计上限
    if (webStatFilter.getSessionStatMaxCount() != null) {
      registrationBean.addInitParameter(WebStatFilterConfBean.ConfigKey.SESSION_STAT_MAX_COUNT.getName(),
          webStatFilter.getSessionStatMaxCount().toString());
    }

    return registrationBean;
  }
}
