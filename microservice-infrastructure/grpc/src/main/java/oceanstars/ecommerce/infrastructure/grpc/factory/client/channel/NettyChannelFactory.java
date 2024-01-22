package oceanstars.ecommerce.infrastructure.grpc.factory.client.channel;

import static oceanstars.ecommerce.infrastructure.grpc.util.GrpcUtils.DOMAIN_SOCKET_ADDRESS_SCHEME;

import io.grpc.netty.GrpcSslContexts;
import io.grpc.netty.NettyChannelBuilder;
import io.netty.channel.epoll.EpollDomainSocketChannel;
import io.netty.channel.epoll.EpollEventLoopGroup;
import io.netty.channel.unix.DomainSocketAddress;
import io.netty.handler.ssl.SslContextBuilder;
import java.io.InputStream;
import java.net.URI;
import java.util.List;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLException;
import javax.net.ssl.TrustManagerFactory;
import oceanstars.ecommerce.common.tools.KeyStoreUtil;
import oceanstars.ecommerce.infrastructure.grpc.config.client.GrpcChannelProperties;
import oceanstars.ecommerce.infrastructure.grpc.config.client.GrpcChannelProperties.Security;
import oceanstars.ecommerce.infrastructure.grpc.config.client.GrpcChannelsProperties;
import oceanstars.ecommerce.infrastructure.grpc.config.client.NegotiationType;
import oceanstars.ecommerce.infrastructure.grpc.interceptor.client.GlobalClientInterceptorRegistry;
import oceanstars.ecommerce.infrastructure.grpc.util.GrpcUtils;
import org.springframework.core.io.Resource;

/**
 * gRPC Netty连接 客户端连接通道工厂类
 *
 * @author Clover
 * @version 1.0.0
 * @since 2021/11/15 5:14 下午
 */
public class NettyChannelFactory extends AbstractChannelFactory<NettyChannelBuilder> {

  /**
   * 构造函数
   *
   * @param properties                      gRPC客户端连接通道属性配置
   * @param globalClientInterceptorRegistry 全局客户端拦截器注册类
   * @param channelConfigurers              gRPC客户端连接通道配置列表
   */
  public NettyChannelFactory(final GrpcChannelsProperties properties,
      final GlobalClientInterceptorRegistry globalClientInterceptorRegistry,
      final List<GrpcChannelConfigurer> channelConfigurers) {
    super(properties, globalClientInterceptorRegistry, channelConfigurers);
  }

  @Override
  protected NettyChannelBuilder newChannelBuilder(String name) {

    // 根据指定gRPC客户端名获取对应的连接通道属性设定
    final GrpcChannelProperties properties = super.getPropertiesFor(name);

    // 获取连接目标地址
    URI address = properties.getAddress();
    if (address == null) {
      address = URI.create(name);
    }

    // 连接协议unix的情况下
    if (DOMAIN_SOCKET_ADDRESS_SCHEME.equals(address.getScheme())) {

      // 提取域套接字地址特定路径
      final String path = GrpcUtils.extractDomainSocketAddressPath(address.toString());

      // 创建普通Netty连接通道构建器
      return NettyChannelBuilder.forAddress(new DomainSocketAddress(path))
          .channelType(EpollDomainSocketChannel.class)
          .eventLoopGroup(new EpollEventLoopGroup());
    }
    // 其他情况
    else {
      // 创建普通Netty连接通道构建器
      return NettyChannelBuilder.forTarget(address.toString());
    }
  }


  @Override
  protected void configureSecurity(final NettyChannelBuilder builder, final String name) {

    // 根据指定gRPC客户端名获取对应的连接通道属性设定
    final GrpcChannelProperties properties = super.getPropertiesFor(name);
    // 获取连接协商类型
    final NegotiationType negotiationType = properties.getNegotiationType();

    // 设置连接协商类型
    builder.negotiationType(of(negotiationType));

    // 如果连接协商类型为TLS, 则进行以下安全配置处理
    if (negotiationType == NegotiationType.TLS) {
      // 获取连接通道安全配置
      final Security security = properties.getSecurity();

      // 获取连接通道安全配置中的认证头
      final String authorityOverwrite = security.getAuthorityOverride();
      // 如果认证头不为空, 则覆盖连接通道构建器中的认证头
      if (authorityOverwrite != null && !authorityOverwrite.isEmpty()) {
        builder.overrideAuthority(authorityOverwrite);
      }

      // 创建Shaded Netty SSL上下文构建器
      final SslContextBuilder sslContextBuilder = GrpcSslContexts.forClient();
      // 配置安全证书信息至SSL上下文构建器
      this.configureProvidedClientCertificate(security, sslContextBuilder);
      // 配置信任证书信息至SSL上下文构建器
      this.configureAcceptedServerCertificates(security, sslContextBuilder);

      // 设置SSL连接密码套件
      if (security.getCiphers() != null && !security.getCiphers().isEmpty()) {
        sslContextBuilder.ciphers(security.getCiphers());
      }
      // 设置SSL连接协议
      if (security.getProtocols() != null && security.getProtocols().length > 0) {
        sslContextBuilder.protocols(security.getProtocols());
      }

      try {
        // 设置Shaded Netty连接通道构建器的SSL上下文
        builder.sslContext(sslContextBuilder.build());
      } catch (final SSLException e) {
        // SSL上下文构建失败，抛出异常
        throw new IllegalStateException("gRPC客户端SSL上下文构建失败！", e);
      }
    }
  }

  /**
   * 配置安全证书信息至SSL上下文构建器
   *
   * @param security          安全配置
   * @param sslContextBuilder SSL上下文构建器
   */
  protected void configureProvidedClientCertificate(final Security security, final SslContextBuilder sslContextBuilder) {

    // 如果客户端认证启用
    if (security.getClientAuthEnabled()) {

      try {
        // 获取客户端认证私钥
        final Resource privateKey = security.getPrivateKey();
        // 获取客户端认证keyStore资源
        final Resource keyStore = security.getKeyStore();

        // 如果私钥不为空, 则使用私钥进行客户端认证
        if (privateKey != null) {
          // 获取客户端认证证书链资源
          final Resource certificateChain = security.getCertificateChain();
          // 获取客户端认证私钥密码
          final String privateKeyPassword = security.getPrivateKeyPassword();
          // 将证书链、私钥和私钥密码添加到 SSL 上下文构建器中进行统一管理
          try (InputStream certificateChainStream = certificateChain.getInputStream(); InputStream privateKeyStream = privateKey.getInputStream()) {
            sslContextBuilder.keyManager(certificateChainStream, privateKeyStream, privateKeyPassword);
          }
        }
        // 如果KeyStore不为空
        else if (keyStore != null) {
          // 加载密钥管理工厂
          final KeyManagerFactory keyManagerFactory = KeyStoreUtil.loadKeyManagerFactory(security.getKeyStoreFormat(), keyStore,
              security.getKeyStorePassword());
          // 将密钥管理工厂添加到 SSL 上下文构建器中进行统一管理
          sslContextBuilder.keyManager(keyManagerFactory);
        }
        // 其他情况异常抛出
        else {
          throw new IllegalStateException("私钥和keystore文件都未配置！");
        }
      } catch (final Exception e) {
        // SSL上下文构建失败，抛出异常
        throw new IllegalArgumentException("SSL上下文构建失败（keystore|key）!", e);
      }
    }
  }

  /**
   * 配置信任证书信息至SSL上下文构建器
   *
   * @param security          安全配置
   * @param sslContextBuilder SSL上下文构建器
   */
  protected void configureAcceptedServerCertificates(final Security security, final SslContextBuilder sslContextBuilder) {

    try {
      // 获取信任证书集合资源
      final Resource trustCertCollection = security.getTrustCertCollection();
      // 获取信任证书库资源
      final Resource trustStore = security.getTrustStore();

      // 如果信任证书集合不为空, 则使用信任证书集合进行信任证书管理
      if (trustCertCollection != null) {
        try (InputStream trustCertCollectionStream = trustCertCollection.getInputStream()) {
          sslContextBuilder.trustManager(trustCertCollectionStream);
        }
      }
      // 如果信任证书库不为空, 则使用信任证书库进行信任证书管理
      else if (trustStore != null) {
        final TrustManagerFactory trustManagerFactory = KeyStoreUtil.loadTrustManagerFactory(security.getTrustStoreFormat(), trustStore,
            security.getTrustStorePassword());
        sslContextBuilder.trustManager(trustManagerFactory);
      }
      // 其他情况使用系统默认
      else {
        // ...
      }
    } catch (final Exception e) {
      // SSL上下文构建失败，抛出异常
      throw new IllegalArgumentException("SSL上下文构建失败!(TrustStore)", e);
    }
  }

  /**
   * 将gRPC客户端连接协商类型转换为Shaded Netty连接协商类型
   *
   * @param negotiationType gRPC客户端连接协商类型
   * @return Shaded Netty连接协商类型
   */
  protected static io.grpc.netty.NegotiationType of(final NegotiationType negotiationType) {
    return switch (negotiationType) {
      case PLAINTEXT -> io.grpc.netty.NegotiationType.PLAINTEXT;
      case PLAINTEXT_UPGRADE -> io.grpc.netty.NegotiationType.PLAINTEXT_UPGRADE;
      case TLS -> io.grpc.netty.NegotiationType.TLS;
    };
  }
}
