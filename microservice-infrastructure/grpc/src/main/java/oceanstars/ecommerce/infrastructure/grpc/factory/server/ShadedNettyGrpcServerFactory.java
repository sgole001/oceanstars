package oceanstars.ecommerce.infrastructure.grpc.factory.server;

import com.google.common.net.InetAddresses;
import io.grpc.netty.shaded.io.grpc.netty.NettyServerBuilder;
import io.grpc.netty.shaded.io.netty.channel.epoll.EpollEventLoopGroup;
import io.grpc.netty.shaded.io.netty.channel.epoll.EpollServerDomainSocketChannel;
import io.grpc.netty.shaded.io.netty.channel.unix.DomainSocketAddress;
import java.net.InetSocketAddress;
import java.util.List;
import java.util.concurrent.TimeUnit;
import oceanstars.ecommerce.infrastructure.grpc.config.server.GrpcServerProperties;
import oceanstars.ecommerce.infrastructure.grpc.util.GrpcUtils;

/**
 * gRPC Shaded Netty连接 服务器工厂类 (gRPC推荐使用)
 *
 * @author Clover
 * @version 1.0.0
 * @since 2021/11/9 7:08 下午
 */
public class ShadedNettyGrpcServerFactory extends AbstractGrpcServerFactory<NettyServerBuilder> {

  /**
   * 构造函数
   *
   * @param properties        gRPC服务器属性配置
   * @param serverConfigurers gRPC服务器配置列表
   */
  public ShadedNettyGrpcServerFactory(final GrpcServerProperties properties, final List<GrpcServerConfigurer> serverConfigurers) {
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
  protected void configureKeepAlive(final NettyServerBuilder builder) {

    if (this.properties.isEnableKeepAlive()) {
      builder.keepAliveTime(this.properties.getKeepAliveTime().toNanos(), TimeUnit.NANOSECONDS)
          .keepAliveTimeout(this.properties.getKeepAliveTimeout().toNanos(), TimeUnit.NANOSECONDS);
    }
    builder.permitKeepAliveTime(this.properties.getPermitKeepAliveTime().toNanos(), TimeUnit.NANOSECONDS)
        .permitKeepAliveWithoutCalls(this.properties.isPermitKeepAliveWithoutCalls());
  }
}
