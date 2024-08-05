package oceanstars.ecommerce.infrastructure.redis.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.lettuce.core.ClientOptions;
import io.lettuce.core.SocketOptions;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisPassword;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettucePoolingClientConfiguration;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import redis.clients.jedis.JedisPoolConfig;

/**
 * Redis配置信息
 *
 * @author Clover
 * @version 1.0.0
 * @since 2021/11/4 11:09 上午
 */
@AutoConfiguration
@EnableConfigurationProperties({OceanstarsRedisProperties.class})
public class RedisConfig {

  /**
   * Redis连接工厂映射
   */
  private final Map<String, LettuceConnectionFactory> redisConnectionFactories;

  /**
   * 构造函数
   *
   * @param redisProperties Redis配置信息
   */
  public RedisConfig(OceanstarsRedisProperties redisProperties) {
    this.redisConnectionFactories = this.redisConnectionFactories(redisProperties);
  }

  /**
   * 创建默认数据源RedisConnectionFactory
   *
   * @return 默认数据源RedisConnectionFactory
   */
  @Bean
  @Primary
  public LettuceConnectionFactory redisConnectionFactory() {
    return this.redisConnectionFactories.get(OceanstarsRedisProperties.DEFAULT_REDIS_CONFIG);
  }

  /**
   * 创建RedisTemplate(默认数据源)
   *
   * @param redisConnectionFactory Redis连接工厂
   * @param objectMapper           对象映射
   * @return RedisTemplate(默认数据源)
   */
  @Bean
  public RedisTemplate<String, Object> redisTemplate(LettuceConnectionFactory redisConnectionFactory, ObjectMapper objectMapper) {
    return this.buildRedisTemplate(redisConnectionFactory, objectMapper);
  }

  /**
   * 创建ReactiveRedisTemplate(默认数据源)
   *
   * @param reactiveRedisConnectionFactory Redis连接工厂
   * @param objectMapper                   对象映射
   * @return ReactiveRedisTemplate(默认数据源)
   */
  @Bean
  public ReactiveRedisTemplate<Object, Object> reactiveRedisTemplate(ReactiveRedisConnectionFactory reactiveRedisConnectionFactory,
      ObjectMapper objectMapper) {
    return this.buildReactiveRedisTemplate(reactiveRedisConnectionFactory, objectMapper);
  }

  /**
   * 创建多数据源RedisTemplate
   *
   * @param objectMapper 对象映射
   * @return 多数据源RedisTemplate
   */
  @Bean
  public Map<String, RedisTemplate<String, Object>> redisTemplates(ObjectMapper objectMapper) {

    // 创建多数据源RedisTemplate映射
    final Map<String, RedisTemplate<String, Object>> redisTemplates = new HashMap<>(redisConnectionFactories.size());

    // 遍历多数据源Redis连接工厂
    this.redisConnectionFactories.forEach((src, factory) -> redisTemplates.put(src, this.buildRedisTemplate(factory, objectMapper)));

    return redisTemplates;
  }

  /**
   * 创建Redis连接工厂
   *
   * @param redisProperties Redis配置信息
   * @return Redis连接工厂
   */
  private Map<String, LettuceConnectionFactory> redisConnectionFactories(OceanstarsRedisProperties redisProperties) {

    // 获取Redis配置信息
    final Map<String, RedisProperties> properties = redisProperties.getRedis();

    // 创建多数据源Redis连接工厂映射
    final Map<String, LettuceConnectionFactory> redisConnectionFactories = new HashMap<>(properties.size());

    properties.forEach((src, prop) -> {

      // 构建Redis数据库连接配置信息
      final RedisStandaloneConfiguration redisStandaloneConfiguration = this.buildRedisStandaloneConfiguration(prop);
      // 创建Lettuce客户端配置信息
      final LettuceClientConfiguration lettuceClientConfiguration = this.lettuceClientConfiguration(prop);
      // 创建Lettuce连接工厂
      final LettuceConnectionFactory lettuceConnectionFactory = new LettuceConnectionFactory(redisStandaloneConfiguration,
          lettuceClientConfiguration);
      // 关闭共享链接，才能池化生效
      lettuceConnectionFactory.setShareNativeConnection(Boolean.FALSE);
      // 初始化Lettuce连接工厂
      lettuceConnectionFactory.start();

      redisConnectionFactories.put(src, lettuceConnectionFactory);
    });

    return redisConnectionFactories;
  }

  /**
   * 构建Redis数据库连接配置信息
   *
   * @param redisProperties Redis配置信息
   * @return Redis数据库连接配置信息
   */
  private RedisStandaloneConfiguration buildRedisStandaloneConfiguration(final RedisProperties redisProperties) {

    // 创建Redis数据库连接配置信息
    final RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration();
    // Host名
    redisStandaloneConfiguration.setHostName(redisProperties.getHost());
    // 端口号
    redisStandaloneConfiguration.setPort(redisProperties.getPort());
    // 数据库索引
    redisStandaloneConfiguration.setDatabase(redisProperties.getDatabase());
    // 数据库用户名
    redisStandaloneConfiguration.setUsername(redisProperties.getUsername());
    // 数据库密码
    redisStandaloneConfiguration.setPassword(RedisPassword.of(redisProperties.getPassword()));

    return redisStandaloneConfiguration;
  }

  /**
   * 创建Lettuce客户端配置信息
   *
   * @param redisProperties Redis配置信息
   * @return Lettuce客户端配置信息
   */
  private LettuceClientConfiguration lettuceClientConfiguration(final RedisProperties redisProperties) {

    // 配置Redis客户端与服务器之前网络套接字
    final SocketOptions socketOptions = SocketOptions.builder().connectTimeout(redisProperties.getConnectTimeout()).build();

    // 创建Redis客户端配置信息
    final ClientOptions clientOptions = ClientOptions.builder()
        // 自动重连
        .autoReconnect(Boolean.TRUE)
        // ping命令在激活连接之前
        .pingBeforeActivateConnection(Boolean.TRUE)
        // 断开连接时的行为
        .disconnectedBehavior(ClientOptions.DisconnectedBehavior.ACCEPT_COMMANDS)
        // 连接超时
        .socketOptions(socketOptions)
        // 实施构建
        .build();

    // 创建Lettuce连接池配置信息
    return LettucePoolingClientConfiguration.builder()
        // 连接池配置信息
        .poolConfig(this.buildLettucePoolConfig(redisProperties))
        // 连接超时
        .commandTimeout(redisProperties.getTimeout())
        // Redis客户端配置信息
        .clientOptions(clientOptions)
        // 实施构建
        .build();
  }

  /**
   * 创建Lettuce连接池配置信息
   *
   * @param redisProperties Redis配置信息
   * @return Lettuce连接池配置信息
   */
  private GenericObjectPoolConfig<?> buildLettucePoolConfig(final RedisProperties redisProperties) {

    // 创建Redis连接池配置信息
    final GenericObjectPoolConfig<?> poolConfig = new GenericObjectPoolConfig<>();

    // 获取Redis连接池配置信息
    final RedisProperties.Pool pool = redisProperties.getLettuce().getPool();

    // 连接池的最小连接数
    poolConfig.setMinIdle(pool.getMinIdle());
    // 连接池的最大空闲连接数
    poolConfig.setMaxIdle(pool.getMaxIdle());
    // 连接池的最大连接数
    poolConfig.setMaxTotal(pool.getMaxActive());
    // 连接池耗尽后是否需要等待，默认true表示等待。当值为true时，setMaxWait才会生效
    poolConfig.setBlockWhenExhausted(Boolean.TRUE);
    // 连接池耗尽后获取连接的最大等待时间，默认-1表示一直等待
    poolConfig.setMaxWait(pool.getMaxWait());
    // 创建连接时校验有效性(ping)，默认false
    poolConfig.setTestOnCreate(Boolean.FALSE);
    // 获取连接时校验有效性(ping)，默认false，业务量大时建议设置为false减少开销
    poolConfig.setTestOnBorrow(Boolean.TRUE);
    // 归还连接时校验有效性(ping)，默认false，业务量大时建议设置为false减少开销
    poolConfig.setTestOnReturn(Boolean.FALSE);
    // 是否开启空闲连接检测，如为false，则不剔除空闲连接
    poolConfig.setTestWhileIdle(Boolean.TRUE);
    // 连接空闲多久后逐出，当空闲时间>该值，并且空闲连接>最大空闲数时直接逐出
    poolConfig.setSoftMinEvictableIdleDuration(Duration.ofMillis(30 * 60 * 1000));
    // 关闭根据MinEvictableIdleTimeMillis判断逐出
    poolConfig.setMinEvictableIdleDuration(Duration.ofMillis(-1));
    // 空闲连接逐出的检测周期，默认为60s
    if (null != pool.getTimeBetweenEvictionRuns()) {
      poolConfig.setTimeBetweenEvictionRuns(pool.getTimeBetweenEvictionRuns());
    }

    return poolConfig;
  }

  /**
   * 构建Redis线程池配置信息
   *
   * @param redisProperties Redis配置信息
   * @return Redis线程池配置信息
   */
  private JedisPoolConfig buildJedisPoolConfig(final RedisProperties redisProperties) {

    // 创建Redis线程池配置信息
    final JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();

    // 获取Redis连接池配置信息
    final RedisProperties.Pool pool = redisProperties.getJedis().getPool();

    // 最大空闲数
    jedisPoolConfig.setMaxIdle(Optional.of(pool.getMaxIdle()).orElse(300));
    // 连接池的最大数据库连接数
    jedisPoolConfig.setMaxTotal(Optional.of(pool.getMaxActive()).orElse(1000));
    // 最大建立连接等待时间
    jedisPoolConfig.setMaxWait(Optional.ofNullable(pool.getMaxWait()).orElse(Duration.ofMillis(1000)));
    // 逐出扫描的时间间隔(毫秒) 如果为负数,则不运行逐出线程, 默认-1
    jedisPoolConfig.setTimeBetweenEvictionRuns(Optional.ofNullable(pool.getTimeBetweenEvictionRuns()).orElse(Duration.ofMillis(30000)));
    // 逐出连接的最小空闲时间 默认1800000毫秒(30分钟)
    jedisPoolConfig.setMinEvictableIdleDuration(Duration.ofMillis(300000));
    // 每次逐出检查时 逐出的最大数目 如果为负数就是 : 1/abs(n), 默认3
    jedisPoolConfig.setNumTestsPerEvictionRun(1024);
    // 是否在从池中取出连接前进行检验,如果检验失败,则从池中去除连接并尝试取出另一个
    jedisPoolConfig.setTestOnBorrow(Boolean.FALSE);
    // 在空闲时检查有效性, 默认false
    jedisPoolConfig.setTestWhileIdle(Boolean.FALSE);

    return jedisPoolConfig;
  }

  /**
   * 创建RedisTemplate
   *
   * @param factory Redis连接工厂
   * @return RedisTemplate
   */
  private RedisTemplate<String, Object> buildRedisTemplate(final RedisConnectionFactory factory, ObjectMapper objectMapper) {

    // 使用jackson序列化替代jdk序列化
    final GenericJackson2JsonRedisSerializer redisSerializer = new GenericJackson2JsonRedisSerializer(objectMapper);
    // 创建RedisTemplate
    final RedisTemplate<String, Object> template = new RedisTemplate<>();
    // 设置Redis连接工厂
    template.setConnectionFactory(factory);
    // 设置Redis Key值序列化器
    template.setKeySerializer(redisSerializer);
    // 设置Redis Value值序列化器
    template.setValueSerializer(redisSerializer);
    // 设置Redis Hash Key值序列化器
    template.setHashKeySerializer(redisSerializer);
    // 设置Redis Hash Value值序列化器
    template.setHashValueSerializer(redisSerializer);
    // 初始化RedisTemplate
    template.afterPropertiesSet();

    return template;
  }

  /**
   * 创建ReactiveRedisTemplate
   *
   * @param factory Redis连接工厂
   * @return ReactiveRedisTemplate
   */
  private ReactiveRedisTemplate<Object, Object> buildReactiveRedisTemplate(final ReactiveRedisConnectionFactory factory, ObjectMapper objectMapper) {

    // 使用jackson序列化替代jdk序列化
    final GenericJackson2JsonRedisSerializer redisSerializer = new GenericJackson2JsonRedisSerializer(objectMapper);
    // 构建Redis序列化上下文
    final RedisSerializationContext<Object, Object> serializationContext = RedisSerializationContext.newSerializationContext()
        // 设置Key值序列化器
        .key(redisSerializer)
        // 设置Value值序列化器
        .value(redisSerializer)
        // 设置Hash Key值序列化器
        .hashKey(redisSerializer)
        // 设置Hash Value值序列化器
        .hashValue(redisSerializer)
        // 实施构建
        .build();

    // 创建ReactiveRedisTemplate
    return new ReactiveRedisTemplate<>(factory, serializationContext);
  }
}
