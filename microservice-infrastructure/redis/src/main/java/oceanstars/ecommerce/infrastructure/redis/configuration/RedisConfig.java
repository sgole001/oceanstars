package oceanstars.ecommerce.infrastructure.redis.configuration;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import oceanstars.ecommerce.common.constant.CommonConstant;
import oceanstars.ecommerce.common.tools.PropertyUtil;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.context.properties.bind.Bindable;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisPassword;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import redis.clients.jedis.JedisPoolConfig;

/**
 * Redis配置信息
 *
 * @author Clover
 * @version 1.0.0
 * @since 2021/11/4 11:09 上午
 */
@AutoConfiguration
public class RedisConfig {

  /**
   * 构建Redis配置信息集合
   *
   * @return Redis配置信息集合
   */
  @Bean
  public Map<String, RedisParametersBean> redisParametersBeans() {

    // 获取Redis配置信息集合前缀信息
    // 没有指定配置集合定义时，给与默认设置（遵循约定）
    String redisConfigurations = PropertyUtil.BINDER.bind("redis.configuration", Bindable.of(String.class))
        .orElse(RedisParametersBean.DEFAULT_REDIS_CONFIG);

    // 获取所有Redis配置信息
    final String[] redisConfigurationArray = redisConfigurations.split(CommonConstant.SEPARATOR_COMMA);
    // 创建Redis配置信息映射表
    final Map<String, RedisParametersBean> redisParametersBeans = new HashMap<>(redisConfigurationArray.length);

    for (String redisConfiguration : redisConfigurationArray) {

      // 获取Redis具体配置信息
      final RedisParametersBean redisParametersBean = PropertyUtil.BINDER
          .bind(redisConfiguration.concat(RedisParametersBean.REDIS_CONFIG_PREFIX), Bindable.of(RedisParametersBean.class)).get();

      redisParametersBeans.put(redisConfiguration, redisParametersBean);
    }

    return redisParametersBeans;
  }

  @Bean
  public Map<String, JedisPoolConfig> jedisPoolConfigs(Map<String, RedisParametersBean> redisParametersBeans) {

    final Map<String, JedisPoolConfig> jedisPoolConfigs = new HashMap<>(redisParametersBeans.size());

    for (Entry<String, RedisParametersBean> redisParametersEntry : redisParametersBeans.entrySet()) {

      // 获取Redis参数配置信息
      final RedisParametersBean redisParametersBean = redisParametersEntry.getValue();

      final JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
      // 最大空闲数
      jedisPoolConfig.setMaxIdle(Optional.ofNullable(redisParametersBean.getMaxIdle()).orElse(300));
      // 连接池的最大数据库连接数
      jedisPoolConfig.setMaxTotal(Optional.ofNullable(redisParametersBean.getMaxTotal()).orElse(1000));
      // 最大建立连接等待时间
      jedisPoolConfig.setMaxWaitMillis(Optional.ofNullable(redisParametersBean.getMaxWaitMillis()).orElse(1000));
      // 逐出连接的最小空闲时间 默认1800000毫秒(30分钟)
      jedisPoolConfig.setMinEvictableIdleTimeMillis(Optional.ofNullable(redisParametersBean.getMinEvictableIdleTimeMillis()).orElse(300000));
      // 每次逐出检查时 逐出的最大数目 如果为负数就是 : 1/abs(n), 默认3
      jedisPoolConfig.setNumTestsPerEvictionRun(Optional.ofNullable(redisParametersBean.getNumTestsPerEvictionRun()).orElse(1024));
      // 逐出扫描的时间间隔(毫秒) 如果为负数,则不运行逐出线程, 默认-1
      jedisPoolConfig.setTimeBetweenEvictionRunsMillis(Optional.ofNullable(redisParametersBean.getTimeBetweenEvictionRunsMillis()).orElse(30000L));
      // 是否在从池中取出连接前进行检验,如果检验失败,则从池中去除连接并尝试取出另一个
      jedisPoolConfig.setTestOnBorrow(Optional.ofNullable(redisParametersBean.getTestOnBorrow()).orElse(Boolean.FALSE));
      // 在空闲时检查有效性, 默认false
      jedisPoolConfig.setTestWhileIdle(Optional.ofNullable(redisParametersBean.getTestWhileIdle()).orElse(Boolean.FALSE));

      jedisPoolConfigs.put(redisParametersEntry.getKey(), jedisPoolConfig);
    }

    return jedisPoolConfigs;
  }

  @Bean
  public Map<String, RedisStandaloneConfiguration> redisStandaloneConfigurations(Map<String, RedisParametersBean> redisParametersBeans) {

    final Map<String, RedisStandaloneConfiguration> redisStandaloneConfigurations = new HashMap<>(redisParametersBeans.size());

    for (Entry<String, RedisParametersBean> redisParametersEntry : redisParametersBeans.entrySet()) {

      // 获取Redis参数配置信息
      final RedisParametersBean redisParametersBean = redisParametersEntry.getValue();

      final RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration();
      // Host名
      redisStandaloneConfiguration.setHostName(redisParametersBean.getHost());
      // 端口号
      redisStandaloneConfiguration.setPort(redisParametersBean.getPort());
      // 数据库索引
      redisStandaloneConfiguration.setDatabase(redisParametersBean.getDatabase());
      // 数据库密码
      redisStandaloneConfiguration.setPassword(RedisPassword.of(redisParametersBean.getPassword()));

      redisStandaloneConfigurations.put(redisParametersEntry.getKey(), redisStandaloneConfiguration);
    }

    return redisStandaloneConfigurations;
  }

  @Bean
  public Map<String, JedisConnectionFactory> redisConnectionFactories(Map<String, RedisParametersBean> redisParametersBeans,
      Map<String, JedisPoolConfig> jedisPoolConfigs, Map<String, RedisStandaloneConfiguration> redisStandaloneConfigurations) {

    final Map<String, JedisConnectionFactory> redisConnectionFactories = new HashMap<>(redisParametersBeans.size());

    for (Entry<String, RedisParametersBean> redisParametersEntry : redisParametersBeans.entrySet()) {

      // 获取Redis参数配置信息
      final RedisParametersBean redisParametersBean = redisParametersEntry.getValue();

      // 获取Redis线程池配置信息
      final JedisPoolConfig jedisPoolConfig = jedisPoolConfigs.get(redisParametersEntry.getKey());
      // 获取Redis数据库连接配置信息
      final RedisStandaloneConfiguration redisStandaloneConfiguration = redisStandaloneConfigurations.get(redisParametersEntry.getKey());

      // 创建Redis客户端配置信息
      JedisClientConfiguration jedisClientConfiguration = JedisClientConfiguration.builder().usePooling()
          .poolConfig(jedisPoolConfig).and()
          .readTimeout(redisParametersBean.getTimeout()).build();

      redisConnectionFactories.put(redisParametersEntry.getKey(), new JedisConnectionFactory(redisStandaloneConfiguration, jedisClientConfiguration));

    }

    return redisConnectionFactories;
  }

  @Bean
  public RedisTemplate<String, Object> redisTemplate(Map<String, JedisConnectionFactory> redisConnectionFactories) {

    // 使用jackson序列化替代jdk序列化
    final GenericJackson2JsonRedisSerializer redisSerializer = new GenericJackson2JsonRedisSerializer();
    final RedisTemplate<String, Object> template = new RedisTemplate<>();
    template.setConnectionFactory(redisConnectionFactories.get(RedisParametersBean.DEFAULT_REDIS_CONFIG));
    template.setKeySerializer(redisSerializer);
    template.setValueSerializer(redisSerializer);
    template.setHashKeySerializer(redisSerializer);
    template.setHashValueSerializer(redisSerializer);
    template.afterPropertiesSet();

    return template;
  }

  @Bean
  public Map<String, RedisTemplate<String, Object>> redisTemplates(Map<String, JedisConnectionFactory> redisConnectionFactories) {

    final Map<String, RedisTemplate<String, Object>> redisTemplates = new HashMap<>(redisConnectionFactories.size());

    for (Entry<String, JedisConnectionFactory> redisConnectionFactoryEntry : redisConnectionFactories.entrySet()) {
      // 使用jackson序列化替代jdk序列化
      final GenericJackson2JsonRedisSerializer redisSerializer = new GenericJackson2JsonRedisSerializer();
      final RedisTemplate<String, Object> template = new RedisTemplate<>();
      template.setConnectionFactory(redisConnectionFactoryEntry.getValue());
      template.setKeySerializer(redisSerializer);
      template.setValueSerializer(redisSerializer);
      template.setHashKeySerializer(redisSerializer);
      template.setHashValueSerializer(redisSerializer);
      template.afterPropertiesSet();

      redisTemplates.put(redisConnectionFactoryEntry.getKey(), template);
    }

    return redisTemplates;
  }
}
