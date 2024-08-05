package oceanstars.ecommerce.infrastructure.redis.configuration;

import java.util.Map;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Redis配置信息
 *
 * @author Clover
 * @version 1.0.0
 * @since 2024/7/4 16:40
 */
@ConfigurationProperties("oceanstars.data")
public class OceanstarsRedisProperties {

  /**
   * Redis默认配置组名
   */
  public static final String DEFAULT_REDIS_CONFIG = "default";

  /**
   * Redis配置信息(多数据源配置)
   */
  private Map<String, RedisProperties> redis;

  public Map<String, RedisProperties> getRedis() {
    return redis;
  }

  public void setRedis(Map<String, RedisProperties> redis) {
    this.redis = redis;
  }
}
