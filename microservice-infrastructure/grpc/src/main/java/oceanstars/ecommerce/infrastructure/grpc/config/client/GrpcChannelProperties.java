package oceanstars.ecommerce.infrastructure.grpc.config.client;

import io.grpc.internal.GrpcUtil;
import io.grpc.netty.NegotiationType;
import java.net.URI;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import org.springframework.boot.convert.DataSizeUnit;
import org.springframework.boot.convert.DurationUnit;
import org.springframework.util.unit.DataSize;
import org.springframework.util.unit.DataUnit;

/**
 * gRPC客户端Channel属性
 *
 * @author Clover
 * @version 1.0.0
 * @since 2021/11/11 10:31 下午
 */
public class GrpcChannelProperties {

  /**
   * 目标地址
   */
  private URI address = null;

  /**
   * 是否开启连接KeepAlive
   */
  private Boolean enableKeepAlive = false;

  /**
   * Netty:发送keep alive前的默认延迟
   */
  @DurationUnit(ChronoUnit.SECONDS)
  private Duration keepAliveTime = Duration.of(5, ChronoUnit.MINUTES);

  /**
   * Netty:Keep Alive Ping请求的默认超时时间
   */
  @DurationUnit(ChronoUnit.SECONDS)
  private Duration keepAliveTimeout = Duration.of(20, ChronoUnit.SECONDS);

  /**
   * Netty:当连接上没有未完成的RPC时是否执行keepAlive
   */
  private Boolean keepAliveWithoutCalls = false;

  /**
   * 等待通道正常关闭的时间。 如果设置为负值，则通道永远等待，如果设置为0，则通道立即关闭。
   */
  @DurationUnit(ChronoUnit.SECONDS)
  private Duration shutdownGracePeriod = Duration.ofSeconds(30);

  /**
   * 通道允许接收的最大消息大小。如果没有设置，则默认{@link GrpcUtil#DEFAULT_MAX_MESSAGE_SIZE}，如果设置为-1，则使用可能的最大值（不推荐）
   */
  @DataSizeUnit(DataUnit.BYTES)
  private DataSize maxInboundMessageSize = null;

  /**
   * 是否应启用入站流的全流解压
   */
  private Boolean fullStreamDecompression = false;

  /**
   * 连接协议类型
   */
  private NegotiationType negotiationType = NegotiationType.PLAINTEXT;

  /**
   * 应用程序启动时的连接超时。
   */
  private Duration immediateConnectTimeout = Duration.ZERO;

  public URI getAddress() {
    return address;
  }

  public void setAddress(URI address) {
    this.address = address;
  }

  /**
   * 根据指定地址字符串，设定目标地址。字符串格式：{@code schema:[//[authority]][/path]}.
   *
   * @param address 地址字符串
   */
  public void setAddress(final String address) {
    this.address = address == null ? null : URI.create(address);
  }

  public Boolean isEnableKeepAlive() {
    return enableKeepAlive;
  }

  public void setEnableKeepAlive(Boolean enableKeepAlive) {
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

  public Boolean isKeepAliveWithoutCalls() {
    return keepAliveWithoutCalls;
  }

  public void setKeepAliveWithoutCalls(Boolean keepAliveWithoutCalls) {
    this.keepAliveWithoutCalls = keepAliveWithoutCalls;
  }

  public Duration getShutdownGracePeriod() {
    return shutdownGracePeriod;
  }

  public void setShutdownGracePeriod(Duration shutdownGracePeriod) {
    this.shutdownGracePeriod = shutdownGracePeriod;
  }

  public DataSize getMaxInboundMessageSize() {
    return maxInboundMessageSize;
  }

  public void setMaxInboundMessageSize(DataSize maxInboundMessageSize) {
    this.maxInboundMessageSize = maxInboundMessageSize;
  }

  public Boolean isFullStreamDecompression() {
    return fullStreamDecompression;
  }

  public void setFullStreamDecompression(Boolean fullStreamDecompression) {
    this.fullStreamDecompression = fullStreamDecompression;
  }

  public NegotiationType getNegotiationType() {
    return negotiationType;
  }

  public void setNegotiationType(NegotiationType negotiationType) {
    this.negotiationType = negotiationType;
  }

  public Duration getImmediateConnectTimeout() {
    return immediateConnectTimeout;
  }

  public void setImmediateConnectTimeout(Duration immediateConnectTimeout) {
    this.immediateConnectTimeout = immediateConnectTimeout;
  }

  /**
   * gRPC客户端连接通道属性复制
   *
   * @param config 属性复制源数据
   */
  public void copyDefaultsFrom(final GrpcChannelProperties config) {

    if (this == config) {
      return;
    }
    if (this.address == null) {
      this.address = config.address;
    }
    if (this.enableKeepAlive == null) {
      this.enableKeepAlive = config.enableKeepAlive;
    }
    if (this.keepAliveTime == null) {
      this.keepAliveTime = config.keepAliveTime;
    }
    if (this.keepAliveTimeout == null) {
      this.keepAliveTimeout = config.keepAliveTimeout;
    }
    if (this.keepAliveWithoutCalls == null) {
      this.keepAliveWithoutCalls = config.keepAliveWithoutCalls;
    }
    if (this.shutdownGracePeriod == null) {
      this.shutdownGracePeriod = config.shutdownGracePeriod;
    }
    if (this.maxInboundMessageSize == null) {
      this.maxInboundMessageSize = config.maxInboundMessageSize;
    }
    if (this.fullStreamDecompression == null) {
      this.fullStreamDecompression = config.fullStreamDecompression;
    }
    if (this.negotiationType == null) {
      this.negotiationType = config.negotiationType;
    }
    if (this.immediateConnectTimeout == null) {
      this.immediateConnectTimeout = config.immediateConnectTimeout;
    }
  }
}
