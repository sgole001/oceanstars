package oceanstars.ecommerce.infrastructure.druid.configuration;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.spring.boot.autoconfigure.properties.DruidStatProperties;
import com.alibaba.druid.spring.boot.autoconfigure.stat.DruidFilterConfiguration;
import com.alibaba.druid.spring.boot.autoconfigure.stat.DruidSpringAopConfiguration;
import com.alibaba.druid.spring.boot.autoconfigure.stat.DruidStatViewServletConfiguration;
import com.alibaba.druid.spring.boot.autoconfigure.stat.DruidWebStatFilterConfiguration;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * 数据源信息配置
 *
 * @author Clover
 * @version 1.0.0
 * @since 2023/1/26 17:38
 */
@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties({DruidStatProperties.class, MultiDruidDataSource.class})
@ConditionalOnClass({DruidDataSource.class})
@Import({DruidSpringAopConfiguration.class, DruidStatViewServletConfiguration.class, DruidWebStatFilterConfiguration.class,
    DruidFilterConfiguration.class})
public class OceanStarsDataSourceAutoConfig {

  /**
   * 日志管理器
   */
  private static final Logger logger = LogManager.getLogger(OceanStarsDataSourceAutoConfig.class.getName());

  public OceanStarsDataSourceAutoConfig() {
    // ...
    logger.info("Data Source Auto Config...");
  }

  @Bean(initMethod = "init")
  @ConditionalOnMissingBean
  public MultiDruidDataSource dataSources() {
    logger.info("Druid DataSources init...");
    return new MultiDruidDataSource();
  }
}
