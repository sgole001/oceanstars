package oceanstars.ecommerce.infrastructure.druid.configuration;

import com.alibaba.druid.pool.DruidDataSource;
import java.sql.SQLException;
import java.util.Map;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 多数据源Druid配置接口
 *
 * @author Clover
 * @version 1.0.0
 * @since 2023/1/26 23:43
 */
@ConfigurationProperties("oceanstars.datasource")
public class MultiDruidDataSource {

  public MultiDruidDataSource() {
    // ...
  }

  /**
   * 单数据源
   */
  private DruidDataSource druid;

  /**
   * 多数据源
   */
  private Map<String, DruidDataSource> druids;

  public Map<String, DruidDataSource> getDruids() {
    return druids;
  }

  public DruidDataSource getDruid() {
    return druid;
  }

  public void setDruid(DruidDataSource druid) {
    this.druid = druid;
  }

  public void setDruids(Map<String, DruidDataSource> druids) {
    this.druids = druids;
  }

  public void init() throws SQLException {

    // 所有datasource初始化处理
    if (null != this.druids) {
      for (DruidDataSource druidDataSource : this.druids.values()) {
        druidDataSource.init();
      }
    } else if (null != this.druid) {
      this.druid.init();
    }
  }
}
