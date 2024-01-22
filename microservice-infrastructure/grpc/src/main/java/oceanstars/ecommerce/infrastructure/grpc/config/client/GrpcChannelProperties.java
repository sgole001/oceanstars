package oceanstars.ecommerce.infrastructure.grpc.config.client;

import io.grpc.internal.GrpcUtil;
import java.net.URI;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.List;
import oceanstars.ecommerce.common.tools.KeyStoreUtil;
import org.springframework.boot.convert.DataSizeUnit;
import org.springframework.boot.convert.DurationUnit;
import org.springframework.core.io.Resource;
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

  /**
   * 安全配置
   */
  private final Security security = new Security();

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

  public Security getSecurity() {
    return security;
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
    this.security.copyDefaultsFrom(config.security);
  }

  public static class Security {

    /**
     * 是否启用TLS
     */
    private Boolean clientAuthEnabled = false;

    /**
     * 证书链资源
     */
    private Resource certificateChain = null;

    /**
     * 私钥
     */
    private Resource privateKey = null;

    /**
     * 私钥密码
     */
    private String privateKeyPassword = null;

    /**
     * keyStore类型
     */
    private String keyStoreFormat = KeyStoreUtil.FORMAT_AUTODETECT;

    /**
     * keyStore资源
     */
    private Resource keyStore = null;

    /**
     * keyStore密码
     */
    private String keyStorePassword = null;

    /**
     * 信任证书集合资源
     */
    private Resource trustCertCollection = null;

    /**
     * 信任证书集合类型
     */
    private String trustStoreFormat = KeyStoreUtil.FORMAT_AUTODETECT;

    /**
     * 信任证书资源
     */
    private Resource trustStore = null;

    /**
     * 信任证书密码
     */
    private String trustStorePassword = null;

    /**
     * 权限覆盖
     */
    private String authorityOverride = null;

    /**
     * TLS Ciphers
     */
    private List<String> ciphers = null;

    /**
     * TLS协议
     */
    private String[] protocols = null;

    public Boolean getClientAuthEnabled() {
      return clientAuthEnabled;
    }

    public void setClientAuthEnabled(Boolean clientAuthEnabled) {
      this.clientAuthEnabled = clientAuthEnabled;
    }

    public Resource getCertificateChain() {
      return certificateChain;
    }

    public void setCertificateChain(Resource certificateChain) {
      this.certificateChain = certificateChain;
    }

    public Resource getPrivateKey() {
      return privateKey;
    }

    public void setPrivateKey(Resource privateKey) {
      this.privateKey = privateKey;
    }

    public String getPrivateKeyPassword() {
      return privateKeyPassword;
    }

    public void setPrivateKeyPassword(String privateKeyPassword) {
      this.privateKeyPassword = privateKeyPassword;
    }

    public String getKeyStoreFormat() {
      return keyStoreFormat;
    }

    public void setKeyStoreFormat(String keyStoreFormat) {
      this.keyStoreFormat = keyStoreFormat;
    }

    public Resource getKeyStore() {
      return keyStore;
    }

    public void setKeyStore(Resource keyStore) {
      this.keyStore = keyStore;
    }

    public String getKeyStorePassword() {
      return keyStorePassword;
    }

    public void setKeyStorePassword(String keyStorePassword) {
      this.keyStorePassword = keyStorePassword;
    }

    public Resource getTrustCertCollection() {
      return trustCertCollection;
    }

    public void setTrustCertCollection(Resource trustCertCollection) {
      this.trustCertCollection = trustCertCollection;
    }

    public String getTrustStoreFormat() {
      return trustStoreFormat;
    }

    public void setTrustStoreFormat(String trustStoreFormat) {
      this.trustStoreFormat = trustStoreFormat;
    }

    public Resource getTrustStore() {
      return trustStore;
    }

    public void setTrustStore(Resource trustStore) {
      this.trustStore = trustStore;
    }

    public String getTrustStorePassword() {
      return trustStorePassword;
    }

    public void setTrustStorePassword(String trustStorePassword) {
      this.trustStorePassword = trustStorePassword;
    }

    public String getAuthorityOverride() {
      return authorityOverride;
    }

    public void setAuthorityOverride(String authorityOverride) {
      this.authorityOverride = authorityOverride;
    }

    public List<String> getCiphers() {
      return ciphers;
    }

    public void setCiphers(List<String> ciphers) {
      this.ciphers = ciphers;
    }

    public String[] getProtocols() {
      return protocols;
    }

    public void setProtocols(String[] protocols) {
      this.protocols = protocols;
    }

    /**
     * Security默认配置设定
     *
     * @param config 默认配置
     */
    public void copyDefaultsFrom(final Security config) {
      if (this == config) {
        return;
      }
      if (this.clientAuthEnabled == null) {
        this.clientAuthEnabled = config.clientAuthEnabled;
      }
      if (this.certificateChain == null) {
        this.certificateChain = config.certificateChain;
      }
      if (this.privateKey == null) {
        this.privateKey = config.privateKey;
      }
      if (this.privateKeyPassword == null) {
        this.privateKeyPassword = config.privateKeyPassword;
      }
      if (this.keyStoreFormat == null) {
        this.keyStoreFormat = config.keyStoreFormat;
      }
      if (this.keyStore == null) {
        this.keyStore = config.keyStore;
      }
      if (this.keyStorePassword == null) {
        this.keyStorePassword = config.keyStorePassword;
      }
      if (this.trustCertCollection == null) {
        this.trustCertCollection = config.trustCertCollection;
      }
      if (this.trustStoreFormat == null) {
        this.trustStoreFormat = config.trustStoreFormat;
      }
      if (this.trustStore == null) {
        this.trustStore = config.trustStore;
      }
      if (this.trustStorePassword == null) {
        this.trustStorePassword = config.trustStorePassword;
      }
      if (this.authorityOverride == null) {
        this.authorityOverride = config.authorityOverride;
      }
      if (this.ciphers == null) {
        this.ciphers = config.ciphers;
      }
      if (this.protocols == null) {
        this.protocols = config.protocols;
      }
    }
  }
}
