package oceanstars.ecommerce.infrastructure.pool.configuration;

import com.alibaba.druid.pool.DruidDataSource;
import com.zaxxer.hikari.HikariDataSource;
import java.util.Map;
import javax.sql.DataSource;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

/**
 * 多数据源配置接口
 *
 * @author Clover
 * @version 1.0.0
 * @since 2023/11/20 21:09
 */
@ConfigurationProperties("oceanstars.datasource")
public class OceanstarsDataSourceProperties {

  public OceanstarsDataSourceProperties() {
    // ...
  }

  /**
   * 数据源类型
   */
  private Class<? extends DataSource> type;

  /**
   * 默认数据源
   */
  private String defaultDataSource;

  /**
   * Hikari数据源
   */
  private Map<String, HikariDataSource> hikari;

  /**
   * Druid数据源
   */
  private Map<String, DruidDataSource> druid;

  public Class<? extends DataSource> getType() {
    return type;
  }

  public void setType(Class<? extends DataSource> type) {
    this.type = type;
  }

  public String getDefaultDataSource() {

    if (StringUtils.hasText(this.defaultDataSource)) {
      return defaultDataSource;
    }

    if (!CollectionUtils.isEmpty(this.hikari)) {
      return this.hikari.keySet().iterator().next();
    }

    if (!CollectionUtils.isEmpty(this.druid)) {
      return this.druid.keySet().iterator().next();
    }

    return null;
  }

  public void setDefaultDataSource(String defaultDataSource) {
    this.defaultDataSource = defaultDataSource;
  }

  public Map<String, DruidDataSource> getDruid() {
    return druid;
  }

  public void setDruid(Map<String, DruidDataSource> druid) {
    this.druid = druid;
  }

  public Map<String, HikariDataSource> getHikari() {
    return hikari;
  }

  public void setHikari(Map<String, HikariDataSource> hikari) {
    this.hikari = hikari;
  }
}
