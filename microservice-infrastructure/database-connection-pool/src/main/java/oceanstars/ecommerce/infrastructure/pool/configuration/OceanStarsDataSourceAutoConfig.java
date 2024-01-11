package oceanstars.ecommerce.infrastructure.pool.configuration;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.spring.boot.autoconfigure.properties.DruidStatProperties;
import com.alibaba.druid.spring.boot.autoconfigure.stat.DruidFilterConfiguration;
import com.alibaba.druid.spring.boot.autoconfigure.stat.DruidSpringAopConfiguration;
import com.alibaba.druid.spring.boot.autoconfigure.stat.DruidStatViewServletConfiguration;
import com.alibaba.druid.spring.boot.autoconfigure.stat.DruidWebStatFilterConfiguration;
import com.zaxxer.hikari.HikariDataSource;
import java.sql.SQLException;
import java.util.Map;
import java.util.Map.Entry;
import javax.sql.DataSource;
import oceanstars.ecommerce.common.exception.SystemException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * 数据源信息配置
 *
 * @author Clover
 * @version 1.0.0
 * @since 2023/11/20 21:03
 */
@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties({OceanstarsDataSourceProperties.class, DruidStatProperties.class})
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

  @Bean(name = "dataSources")
  public OceanstarsDataSource dataSources(final OceanstarsDataSourceProperties oceanstarsDataSource) {

    logger.info("DataSources init start.");

    // 初始化数据源对象
    final OceanstarsDataSource dataSource = new OceanstarsDataSource();

    Map<String, ? extends DataSource> dataSourcesProperties;
    // 获取数据源类型
    final Class<? extends DataSource> dataSourceType = oceanstarsDataSource.getType();

    if (null == dataSourceType || HikariDataSource.class.isAssignableFrom(dataSourceType)) {
      // 默认使用Hikari数据源
      dataSourcesProperties = oceanstarsDataSource.getHikari();
    } else if (DruidDataSource.class.isAssignableFrom(dataSourceType)) {
      try {
        for (DruidDataSource druidDataSource : oceanstarsDataSource.getDruid().values()) {
          druidDataSource.init();
        }
        dataSourcesProperties = oceanstarsDataSource.getDruid();
      } catch (SQLException e) {
        throw new SystemException("Druid数据源初始化失败！");
      }
    } else {
      throw new SystemException("不支持的数据源类型！");
    }

    // 多数据源的情况
    if (dataSourcesProperties.size() > 1) {
      for (Entry<String, ? extends DataSource> properties : dataSourcesProperties.entrySet()) {
        dataSource.getSources().put(properties.getKey(), properties.getValue());
      }
    }
    // 单数据源的情况
    else {
      dataSource.setSource(dataSourcesProperties.values().iterator().next());
    }

    logger.info("DataSources init end.");

    return dataSource;
  }
}
