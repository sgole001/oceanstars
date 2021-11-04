package oceanstars.ecommerce.infrastructure.redis.builder.impl;


import oceanstars.ecommerce.infrastructure.redis.builder.CacheConfig;
import oceanstars.ecommerce.infrastructure.redis.builder.RedisCacheConfigBuilder;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.cache.RedisCacheConfiguration;

/**
 * 默认缓存配置构建实现类
 *
 * @author sgole
 * @version v1.0
 * @since 2019-05-22 17:02
 */
public abstract class AbstractRedisCacheConfigBuilder implements RedisCacheConfigBuilder {

  /**
   * 构建缓存配置
   *
   * @param config 缓存配置信息Bean
   * @return 缓存配置
   */
  protected RedisCacheConfiguration buildRedisCacheConfiguration(final CacheConfig config) {

    if (config == null) {
      return null;
    }

    // 初始化构建缓存配置构
    RedisCacheConfiguration redisCacheConfiguration = RedisCacheConfiguration.defaultCacheConfig();

    // 有效时间
    if (config.getTtl() != null) {
      redisCacheConfiguration = redisCacheConfiguration.entryTtl(config.getTtl());
    }
    // NULL值是否缓存标志位
    if (config.getCacheNullValues() != null && !config.getCacheNullValues()) {
      redisCacheConfiguration = redisCacheConfiguration.disableCachingNullValues();
    }
    // 是否使用前缀标志位
    if (config.getUsePrefix() != null && !config.getUsePrefix()) {
      redisCacheConfiguration = redisCacheConfiguration.disableKeyPrefix();
    }
    // 缓存Key前缀
    if (StringUtils.isNotEmpty(config.getKeyPrefix())) {
      redisCacheConfiguration = redisCacheConfiguration.prefixCacheNameWith(config.getKeyPrefix());
    }
    // 缓存键值序列化工具
    if (config.getKeySerializationPair() != null) {
      redisCacheConfiguration = redisCacheConfiguration.serializeKeysWith(config.getKeySerializationPair());
    }
    // 缓存值序列化工具
    if (config.getValueSerializationPair() != null) {
      redisCacheConfiguration = redisCacheConfiguration.serializeValuesWith(config.getValueSerializationPair());
    }
    // 类型转换服务
    if (config.getConversionService() != null) {
      redisCacheConfiguration = redisCacheConfiguration.withConversionService(config.getConversionService());
    }

    return redisCacheConfiguration;
  }
}
