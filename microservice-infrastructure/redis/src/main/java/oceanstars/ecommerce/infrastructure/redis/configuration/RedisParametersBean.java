package oceanstars.ecommerce.infrastructure.redis.configuration;

/**
 * Redis参数配置Bean
 *
 * @author Clover
 * @version 1.0.0
 * @since 2021/11/4 11:08 上午
 */
public class RedisParametersBean {

  /**
   * Redis默认配置组名
   */
  public static final String DEFAULT_REDIS_CONFIG = "default";

  /**
   * Redis配置前缀（配置组名后统一配置）
   */
  public static final String REDIS_CONFIG_PREFIX = ".redis";

  /**
   * spring.redis.host
   */
  private String host;

  /**
   * spring.redis.port
   */
  private Integer port;

  /**
   * spring.redis.database
   */
  private Integer database;

  /**
   * spring.redis.password
   */
  private String password;

  /**
   * spring.redis.timeout:3000
   */
  private Long timeout;

  /**
   * spring.redis.maxIdle:300
   */
  private Integer maxIdle;

  /**
   * spring.redis.maxTotal:1000
   */
  private Integer maxTotal;

  /**
   * spring.redis.maxWaitMillis:1000
   */
  private Integer maxWaitMillis;

  /**
   * spring.redis.minEvictableIdleTimeMillis:300000
   */
  private Integer minEvictableIdleTimeMillis;

  /**
   * spring.redis.numTestsPerEvictionRun:1024
   */
  private Integer numTestsPerEvictionRun;

  /**
   * spring.redis.timeBetweenEvictionRunsMillis:30000
   */
  private Long timeBetweenEvictionRunsMillis;

  /**
   * spring.redis.testOnBorrow:true
   */
  private Boolean testOnBorrow;

  /**
   * spring.redis.testWhileIdle:true
   */
  private Boolean testWhileIdle;

  public String getHost() {
    return host;
  }

  public void setHost(String host) {
    this.host = host;
  }

  public Integer getPort() {
    return port;
  }

  public void setPort(Integer port) {
    this.port = port;
  }

  public Integer getDatabase() {
    return database;
  }

  public void setDatabase(Integer database) {
    this.database = database;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public Long getTimeout() {
    return timeout;
  }

  public void setTimeout(Long timeout) {
    this.timeout = timeout;
  }

  public Integer getMaxIdle() {
    return maxIdle;
  }

  public void setMaxIdle(Integer maxIdle) {
    this.maxIdle = maxIdle;
  }

  public Integer getMaxTotal() {
    return maxTotal;
  }

  public void setMaxTotal(Integer maxTotal) {
    this.maxTotal = maxTotal;
  }

  public Integer getMaxWaitMillis() {
    return maxWaitMillis;
  }

  public void setMaxWaitMillis(Integer maxWaitMillis) {
    this.maxWaitMillis = maxWaitMillis;
  }

  public Integer getMinEvictableIdleTimeMillis() {
    return minEvictableIdleTimeMillis;
  }

  public void setMinEvictableIdleTimeMillis(Integer minEvictableIdleTimeMillis) {
    this.minEvictableIdleTimeMillis = minEvictableIdleTimeMillis;
  }

  public Integer getNumTestsPerEvictionRun() {
    return numTestsPerEvictionRun;
  }

  public void setNumTestsPerEvictionRun(Integer numTestsPerEvictionRun) {
    this.numTestsPerEvictionRun = numTestsPerEvictionRun;
  }

  public Long getTimeBetweenEvictionRunsMillis() {
    return timeBetweenEvictionRunsMillis;
  }

  public void setTimeBetweenEvictionRunsMillis(Long timeBetweenEvictionRunsMillis) {
    this.timeBetweenEvictionRunsMillis = timeBetweenEvictionRunsMillis;
  }

  public Boolean getTestOnBorrow() {
    return testOnBorrow;
  }

  public void setTestOnBorrow(Boolean testOnBorrow) {
    this.testOnBorrow = testOnBorrow;
  }

  public Boolean getTestWhileIdle() {
    return testWhileIdle;
  }

  public void setTestWhileIdle(Boolean testWhileIdle) {
    this.testWhileIdle = testWhileIdle;
  }
}
