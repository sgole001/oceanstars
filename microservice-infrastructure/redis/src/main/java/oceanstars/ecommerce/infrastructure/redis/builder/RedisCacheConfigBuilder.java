package oceanstars.ecommerce.infrastructure.redis.builder;

import java.util.Map;
import org.springframework.data.redis.cache.RedisCacheConfiguration;

/**
 * 缓存配置构建接口
 *
 * @author Clover
 * @version 1.0.0
 * @since 2021/11/4 10:57 上午
 */
public interface RedisCacheConfigBuilder {

  /**
   * 构建默认缓存配置
   *
   * @return 默认缓存配置
   */
  RedisCacheConfiguration buildDefaultCacheConfiguration();

  /**
   * 构建初始化缓存配置集合
   *
   * @return 特定缓存配置集合
   */
  Map<String, RedisCacheConfiguration> buildInitialCacheConfigurations();
}
