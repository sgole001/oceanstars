package oceanstars.ecommerce.infrastructure.pool.configuration;

import java.util.HashMap;
import java.util.Map;
import javax.sql.DataSource;

/**
 * 多数据源配置接口
 *
 * @author Clover
 * @version 1.0.0
 * @since 2023/11/22 12:20
 */
public class OceanstarsDataSource {

  /**
   * 默认数据源名称
   */
  public static final String DEFAULT_DATASOURCE_NAME = "default";

  /**
   * 单数据源
   */
  private DataSource source;

  /**
   * 多数据源
   */
  private Map<String, DataSource> sources;

  public DataSource getSource() {
    return source;
  }

  public void setSource(DataSource source) {
    this.source = source;
  }

  public Map<String, DataSource> getSources() {
    if (null == sources) {
      sources = new HashMap<>();
    }
    return sources;
  }

  public void setSources(Map<String, DataSource> sources) {
    this.sources = sources;
  }
}
