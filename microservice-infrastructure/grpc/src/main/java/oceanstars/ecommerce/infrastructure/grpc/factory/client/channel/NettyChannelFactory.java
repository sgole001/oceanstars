package oceanstars.ecommerce.infrastructure.grpc.factory.client.channel;

import static oceanstars.ecommerce.infrastructure.grpc.util.GrpcUtils.DOMAIN_SOCKET_ADDRESS_SCHEME;

import io.grpc.netty.NettyChannelBuilder;
import io.netty.channel.epoll.EpollDomainSocketChannel;
import io.netty.channel.epoll.EpollEventLoopGroup;
import io.netty.channel.unix.DomainSocketAddress;
import java.net.URI;
import java.util.List;
import oceanstars.ecommerce.infrastructure.grpc.config.client.GrpcChannelProperties;
import oceanstars.ecommerce.infrastructure.grpc.config.client.GrpcChannelsProperties;
import oceanstars.ecommerce.infrastructure.grpc.interceptor.client.GlobalClientInterceptorRegistry;
import oceanstars.ecommerce.infrastructure.grpc.util.GrpcUtils;

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
}
