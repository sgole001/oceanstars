package oceanstars.ecommerce.infrastructure.redis.builder;

import java.time.Duration;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.redis.serializer.RedisSerializationContext.SerializationPair;

/**
 * 缓存配置信息Bean
 *
 * @author Clover
 * @version 1.0.0
 * @since 2021/11/4 10:58 上午
 */
public class CacheConfig {

  /**
   * 有效时间
   */
  private Duration ttl;

  /**
   * NULL值是否缓存标志位
   */
  private Boolean cacheNullValues;

  /**
   * 缓存Key前缀
   */
  private String keyPrefix;

  /**
   * 是否使用前缀标志位
   */
  private Boolean usePrefix;

  /**
   * 缓存键值序列化工具
   */
  private SerializationPair<String> keySerializationPair;

  /**
   * 缓存值序列化工具
   */
  private SerializationPair<Object> valueSerializationPair;

  /**
   * 类型转换服务
   */
  private ConversionService conversionService;

  public Duration getTtl() {
    return ttl;
  }

  public void setTtl(Duration ttl) {
    this.ttl = ttl;
  }

  public Boolean getCacheNullValues() {
    return cacheNullValues;
  }

  public void setCacheNullValues(Boolean cacheNullValues) {
    this.cacheNullValues = cacheNullValues;
  }

  public String getKeyPrefix() {
    return keyPrefix;
  }

  public void setKeyPrefix(String keyPrefix) {
    this.keyPrefix = keyPrefix;
  }

  public Boolean getUsePrefix() {
    return usePrefix;
  }

  public void setUsePrefix(Boolean usePrefix) {
    this.usePrefix = usePrefix;
  }

  public SerializationPair<String> getKeySerializationPair() {
    return keySerializationPair;
  }

  public void setKeySerializationPair(SerializationPair<String> keySerializationPair) {
    this.keySerializationPair = keySerializationPair;
  }

  public SerializationPair<Object> getValueSerializationPair() {
    return valueSerializationPair;
  }

  public void setValueSerializationPair(SerializationPair<Object> valueSerializationPair) {
    this.valueSerializationPair = valueSerializationPair;
  }

  public ConversionService getConversionService() {
    return conversionService;
  }

  public void setConversionService(ConversionService conversionService) {
    this.conversionService = conversionService;
  }
}
