package oceanstars.ecommerce.infrastructure.grpc.config.server;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.convert.DataSizeUnit;
import org.springframework.boot.convert.DurationUnit;
import org.springframework.util.SocketUtils;
import org.springframework.util.unit.DataSize;
import org.springframework.util.unit.DataUnit;

/**
 * gRPC服务器属性配置
 *
 * @author Clover
 * @version 1.0.0
 * @since 2021/11/8 11:01 下午
 */
@ConfigurationProperties("grpc.server")
public class GrpcServerProperties {

  /**
   * 常量： 服务可以监听任何IPv4和IPv6的地址.
   */
  public static final String ANY_IP_ADDRESS = "*";

  /**
   * 常量： 服务可以监听IPv4的地址.
   */
  public static final String ANY_IPv4_ADDRESS = "0.0.0.0";

  /**
   * 常量： 服务可以监听IPv6的地址.
   */
  public static final String ANY_IPv6_ADDRESS = "::";

  /**
   * 服务需要绑定的地址
   */
  private String address = ANY_IP_ADDRESS;

  /**
   * 服务将要监听的端口
   */
  private int port = 9090;

  /**
   * In-process服务名（例如同一个JMV中gRPC调用）
   */
  private String inProcessName;

  /**
   * gRPC服务优雅关闭需要等待的时间
   */
  @DurationUnit(ChronoUnit.SECONDS)
  private Duration shutdownGracePeriod = Duration.of(30, ChronoUnit.SECONDS);

  /**
   * 标志位：是否keep alive
   */
  private boolean enableKeepAlive = false;

  /**
   * Netty:发送keep alive前的默认延迟
   */
  @DurationUnit(ChronoUnit.SECONDS)
  private Duration keepAliveTime = Duration.of(2, ChronoUnit.HOURS);

  /**
   * Netty:Keep Alive Ping请求的默认超时时间
   */
  @DurationUnit(ChronoUnit.SECONDS)
  private Duration keepAliveTimeout = Duration.of(20, ChronoUnit.SECONDS);

  /**
   * Netty:客户端允许配置的最积极Keep Alive时间
   */
  @DurationUnit(ChronoUnit.SECONDS)
  private Duration permitKeepAliveTime = Duration.of(5, ChronoUnit.MINUTES);

  /**
   * Netty:是否允许客户端发送保持活动的HTTP/2 ping，即使已经没有未完成的RPC连接
   */
  @DurationUnit(ChronoUnit.SECONDS)
  private boolean permitKeepAliveWithoutCalls = false;

  /**
   * Netty:最大的连接空闲时间
   */
  @DurationUnit(ChronoUnit.SECONDS)
  private Duration maxConnectionIdle = null;

  /**
   * Netty:最大的连接期限
   */
  @DurationUnit(ChronoUnit.SECONDS)
  private Duration maxConnectionAge = null;

  /**
   * Netty:最大的连接期限到期后的缓冲时间
   */
  @DurationUnit(ChronoUnit.SECONDS)
  private Duration maxConnectionAgeGrace = null;

  /**
   * 服务器允许接收的最大消息大小
   */
  @DataSizeUnit(DataUnit.BYTES)
  private DataSize maxInboundMessageSize = null;

  /**
   * 允许接收的元数据的最大大小
   */
  @DataSizeUnit(DataUnit.BYTES)
  private DataSize maxInboundMetadataSize = null;

  /**
   * 是否启用gRPC运行状况服务
   */
  private boolean healthServiceEnabled = true;

  /**
   * 是否启用proto反射服务
   */
  private boolean reflectionServiceEnabled = true;

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  public int getPort() {
    if (this.port == 0) {
      this.port = SocketUtils.findAvailableTcpPort();
    }
    return this.port;
  }

  public void setPort(int port) {
    this.port = port;
  }

  public String getInProcessName() {
    return inProcessName;
  }

  public void setInProcessName(String inProcessName) {
    this.inProcessName = inProcessName;
  }

  public Duration getShutdownGracePeriod() {
    return shutdownGracePeriod;
  }

  public void setShutdownGracePeriod(Duration shutdownGracePeriod) {
    this.shutdownGracePeriod = shutdownGracePeriod;
  }

  public boolean isEnableKeepAlive() {
    return enableKeepAlive;
  }

  public void setEnableKeepAlive(boolean enableKeepAlive) {
    this.enableKeepAlive = enableKeepAlive;
  }

  public Duration getKeepAliveTime() {
    return keepAliveTime;
  }

  public void setKeepAliveTime(Duration keepAliveTime) {
    this.keepAliveTime = keepAliveTime;
  }

  public Duration getKeepAliveTimeout() {
    return keepAliveTimeout;
  }

  public void setKeepAliveTimeout(Duration keepAliveTimeout) {
    this.keepAliveTimeout = keepAliveTimeout;
  }

  public Duration getPermitKeepAliveTime() {
    return permitKeepAliveTime;
  }

  public void setPermitKeepAliveTime(Duration permitKeepAliveTime) {
    this.permitKeepAliveTime = permitKeepAliveTime;
  }

  public boolean isPermitKeepAliveWithoutCalls() {
    return permitKeepAliveWithoutCalls;
  }

  public void setPermitKeepAliveWithoutCalls(boolean permitKeepAliveWithoutCalls) {
    this.permitKeepAliveWithoutCalls = permitKeepAliveWithoutCalls;
  }

  public Duration getMaxConnectionIdle() {
    return maxConnectionIdle;
  }

  public void setMaxConnectionIdle(Duration maxConnectionIdle) {
    this.maxConnectionIdle = maxConnectionIdle;
  }

  public Duration getMaxConnectionAge() {
    return maxConnectionAge;
  }

  public void setMaxConnectionAge(Duration maxConnectionAge) {
    this.maxConnectionAge = maxConnectionAge;
  }

  public Duration getMaxConnectionAgeGrace() {
    return maxConnectionAgeGrace;
  }

  public void setMaxConnectionAgeGrace(Duration maxConnectionAgeGrace) {
    this.maxConnectionAgeGrace = maxConnectionAgeGrace;
  }

  public DataSize getMaxInboundMessageSize() {
    return maxInboundMessageSize;
  }

  public void setMaxInboundMessageSize(DataSize maxInboundMessageSize) {
    if (maxInboundMessageSize == null || maxInboundMessageSize.toBytes() >= 0) {
      this.maxInboundMessageSize = maxInboundMessageSize;
    } else if (maxInboundMessageSize.toBytes() == -1) {
      this.maxInboundMessageSize = DataSize.ofBytes(Integer.MAX_VALUE);
    } else {
      throw new IllegalArgumentException("Unsupported maxInboundMessageSize: " + maxInboundMessageSize);
    }
  }

  public DataSize getMaxInboundMetadataSize() {
    return maxInboundMetadataSize;
  }

  public void setMaxInboundMetadataSize(DataSize maxInboundMetadataSize) {
    if (maxInboundMetadataSize == null || maxInboundMetadataSize.toBytes() >= 0) {
      this.maxInboundMetadataSize = maxInboundMetadataSize;
    } else if (maxInboundMetadataSize.toBytes() == -1) {
      this.maxInboundMetadataSize = DataSize.ofBytes(Integer.MAX_VALUE);
    } else {
      throw new IllegalArgumentException("Unsupported maxInboundMetadataSize: " + maxInboundMetadataSize);
    }
  }

  public boolean isHealthServiceEnabled() {
    return healthServiceEnabled;
  }

  public void setHealthServiceEnabled(boolean healthServiceEnabled) {
    this.healthServiceEnabled = healthServiceEnabled;
  }

  public boolean isReflectionServiceEnabled() {
    return reflectionServiceEnabled;
  }

  public void setReflectionServiceEnabled(boolean reflectionServiceEnabled) {
    this.reflectionServiceEnabled = reflectionServiceEnabled;
  }
}
