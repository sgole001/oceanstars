package oceanstars.ecommerce.infrastructure.grpc.factory.server;

import com.google.common.net.InetAddresses;
import io.grpc.netty.GrpcSslContexts;
import io.grpc.netty.NettyServerBuilder;
import io.netty.channel.epoll.EpollEventLoopGroup;
import io.netty.channel.epoll.EpollServerDomainSocketChannel;
import io.netty.channel.unix.DomainSocketAddress;
import io.netty.handler.ssl.SslContextBuilder;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.util.List;
import java.util.concurrent.TimeUnit;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLException;
import javax.net.ssl.TrustManagerFactory;
import oceanstars.ecommerce.common.tools.KeyStoreUtil;
import oceanstars.ecommerce.infrastructure.grpc.config.server.ClientAuth;
import oceanstars.ecommerce.infrastructure.grpc.config.server.GrpcServerProperties;
import oceanstars.ecommerce.infrastructure.grpc.config.server.GrpcServerProperties.Security;
import oceanstars.ecommerce.infrastructure.grpc.util.GrpcUtils;
import org.springframework.core.io.Resource;

/**
 * gRPC Netty连接 服务器工厂类
 *
 * @author Clover
 * @version 1.0.0
 * @since 2021/11/9 4:58 下午
 */
public class NettyGrpcServerFactory extends AbstractGrpcServerFactory<NettyServerBuilder> {

  /**
   * 构造函数
   *
   * @param properties        gRPC服务器属性配置
   * @param serverConfigurers gRPC服务器配置列表
   */
  public NettyGrpcServerFactory(final GrpcServerProperties properties, final List<GrpcServerConfigurer> serverConfigurers) {
    super(properties, serverConfigurers);
  }

  @Override
  protected NettyServerBuilder newServerBuilder() {

    // 获取gRPC服务器绑定地址
    final String address = getAddress();
    // 获取gRPC服务器使用的本地监听端口
    final int port = getPort();

    // 服务器绑定地址为Unix域套接字地址
    if (address.startsWith(GrpcUtils.DOMAIN_SOCKET_ADDRESS_PREFIX)) {
      final String path = GrpcUtils.extractDomainSocketAddressPath(address);
      return NettyServerBuilder.forAddress(new DomainSocketAddress(path))
          .channelType(EpollServerDomainSocketChannel.class)
          .bossEventLoopGroup(new EpollEventLoopGroup(1))
          .workerEventLoopGroup(new EpollEventLoopGroup());
    }
    // 服务器绑定地址为任意IP地址（包含IPv4和IPv6）
    else if (GrpcServerProperties.ANY_IP_ADDRESS.equals(address)) {
      return NettyServerBuilder.forPort(port);
    }
    // 其他
    else {
      return NettyServerBuilder.forAddress(new InetSocketAddress(InetAddresses.forString(address), port));
    }
  }

  @Override
  protected void configureKeepAlive(final NettyServerBuilder builder) {

    if (this.properties.isEnableKeepAlive()) {
      builder.keepAliveTime(this.properties.getKeepAliveTime().toNanos(), TimeUnit.NANOSECONDS)
          .keepAliveTimeout(this.properties.getKeepAliveTimeout().toNanos(), TimeUnit.NANOSECONDS);
    }

    builder.permitKeepAliveTime(this.properties.getPermitKeepAliveTime().toNanos(), TimeUnit.NANOSECONDS)
        .permitKeepAliveWithoutCalls(this.properties.isPermitKeepAliveWithoutCalls());
  }

  @Override
  protected void configureConnectionLimits(final NettyServerBuilder builder) {

    if (this.properties.getMaxConnectionIdle() != null) {
      builder.maxConnectionIdle(this.properties.getMaxConnectionIdle().toNanos(), TimeUnit.NANOSECONDS);
    }
    if (this.properties.getMaxConnectionAge() != null) {
      builder.maxConnectionAge(this.properties.getMaxConnectionAge().toNanos(), TimeUnit.NANOSECONDS);
    }
    if (this.properties.getMaxConnectionAgeGrace() != null) {
      builder.maxConnectionAgeGrace(this.properties.getMaxConnectionAgeGrace().toNanos(), TimeUnit.NANOSECONDS);
    }
  }


  @Override
  protected void configureSecurity(final io.grpc.netty.NettyServerBuilder builder) {

    // 获取安全配置
    final Security security = this.properties.getSecurity();

    // 如果开启了安全性，则进行以下安全配置处理
    if (security.isEnabled()) {

      // 创建SSL上下文构建器
      final SslContextBuilder sslContextBuilder = newServerSslContextBuilder(security);
      // 配置可接受的客户端证书
      configureAcceptedClientCertificates(security, sslContextBuilder);
      // 配置SSL连接密码套件
      if (security.getCiphers() != null && !security.getCiphers().isEmpty()) {
        sslContextBuilder.ciphers(security.getCiphers());
      }
      // 配置SSL连接协议
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
   * 创建Netty SSL上下文构建器
   *
   * @param security 安全配置
   * @return Netty SSL上下文构建器
   */
  protected static SslContextBuilder newServerSslContextBuilder(final Security security) {

    try {
      // 获取私钥
      final Resource privateKey = security.getPrivateKey();
      // 获取KeyStore
      final Resource keyStore = security.getKeyStore();

      // 如果私钥不为空，则使用私钥进行客户端认证
      if (privateKey != null) {
        // 获取证书链
        final Resource certificateChain = security.getCertificateChain();
        // 获取私钥密码
        final String privateKeyPassword = security.getPrivateKeyPassword();

        // 将证书链、私钥和私钥密码添加到 SSL 上下文构建器中进行统一管理
        try (InputStream certificateChainStream = certificateChain.getInputStream(); InputStream privateKeyStream = privateKey.getInputStream()) {
          return GrpcSslContexts.forServer(certificateChainStream, privateKeyStream, privateKeyPassword);
        }

      }
      // 如果私钥为空，但KeyStore不为空，则使用KeyStore进行客户端认证
      else if (keyStore != null) {
        // 加载密钥管理工厂
        final KeyManagerFactory keyManagerFactory = KeyStoreUtil.loadKeyManagerFactory(security.getKeyStoreFormat(), keyStore,
            security.getKeyStorePassword());
        // 将密钥管理工厂添加到 SSL 上下文构建器中进行统一管理
        return GrpcSslContexts.configure(SslContextBuilder.forServer(keyManagerFactory));

      }
      // 其他情况
      else {
        throw new IllegalStateException("私钥和keystore文件都未配置！");
      }
    } catch (final Exception e) {
      // SSL上下文构建失败，抛出异常
      throw new IllegalArgumentException("SSL上下文构建失败（keystore|key）!", e);
    }
  }

  /**
   * 配置可接受的客户端证书
   *
   * @param security          安全配置
   * @param sslContextBuilder Netty SSL上下文构建器
   */
  protected static void configureAcceptedClientCertificates(final Security security, final SslContextBuilder sslContextBuilder) {

    // 如果客户端认证不为NONE，则设置客户端认证
    if (security.getClientAuth() != ClientAuth.NONE) {

      // 设置客户端认证
      sslContextBuilder.clientAuth(of(security.getClientAuth()));

      try {
        // 获取客户端信任证书集合
        final Resource trustCertCollection = security.getTrustCertCollection();
        // 获取客户端信任证书库
        final Resource trustStore = security.getTrustStore();

        // 如果客户端信任证书集合不为空，则使用客户端信任证书集合进行客户端认证
        if (trustCertCollection != null) {
          try (InputStream trustCertCollectionStream = trustCertCollection.getInputStream()) {
            sslContextBuilder.trustManager(trustCertCollectionStream);
          }

        }
        // 如果客户端信任证书库不为空，则使用客户端信任证书库进行客户端认证
        else if (trustStore != null) {
          // 加载信任证书管理工厂
          final TrustManagerFactory trustManagerFactory = KeyStoreUtil.loadTrustManagerFactory(security.getTrustStoreFormat(), trustStore,
              security.getTrustStorePassword());
          // 将信任证书管理工厂添加到 SSL 上下文构建器中进行统一管理
          sslContextBuilder.trustManager(trustManagerFactory);

        }
        // 其他情况使用系统默认
        else {
        }
      } catch (final Exception e) {
        throw new IllegalArgumentException("SSL上下文构建失败!(TrustStore)", e);
      }
    }
  }

  /**
   * 转换gRPC客户端认证枚举类型至Netty客户端认证枚举类型
   *
   * @param clientAuth gRPC客户端认证枚举类型
   * @return Netty客户端认证枚举类型
   */
  protected static io.netty.handler.ssl.ClientAuth of(final ClientAuth clientAuth) {
    return switch (clientAuth) {
      case NONE -> io.netty.handler.ssl.ClientAuth.NONE;
      case OPTIONAL -> io.netty.handler.ssl.ClientAuth.OPTIONAL;
      case REQUIRE -> io.netty.handler.ssl.ClientAuth.REQUIRE;
    };
  }
}
