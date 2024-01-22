package oceanstars.ecommerce.infrastructure.grpc.factory.client.channel;

import io.grpc.inprocess.InProcessChannelBuilder;
import java.util.Collections;
import java.util.List;
import oceanstars.ecommerce.infrastructure.grpc.config.client.GrpcChannelsProperties;
import oceanstars.ecommerce.infrastructure.grpc.interceptor.client.GlobalClientInterceptorRegistry;

/**
 * gRPC in-process 客户端连接通道工厂类(基本用于测试环境，JUnit等相同JVM环境的情况)
 *
 * @author Clover
 * @version 1.0.0
 * @since 2021/11/15 4:58 下午
 */
public class InProcessChannelFactory extends AbstractChannelFactory<InProcessChannelBuilder> {

  /**
   * 构造函数（使用默认通道属性配置）
   *
   * @param properties                      gRPC客户端连接通道属性配置
   * @param globalClientInterceptorRegistry 全局客户端拦截器注册类
   */
  public InProcessChannelFactory(final GrpcChannelsProperties properties, final GlobalClientInterceptorRegistry globalClientInterceptorRegistry) {
    this(properties, globalClientInterceptorRegistry, Collections.emptyList());
  }

  /**
   * 构造函数
   *
   * @param properties                      gRPC客户端连接通道属性配置
   * @param globalClientInterceptorRegistry 全局客户端拦截器注册类
   * @param channelConfigurers              gRPC客户端连接通道配置列表
   */
  public InProcessChannelFactory(final GrpcChannelsProperties properties,
      final GlobalClientInterceptorRegistry globalClientInterceptorRegistry,
      final List<GrpcChannelConfigurer> channelConfigurers) {
    super(properties, globalClientInterceptorRegistry, channelConfigurers);
  }

  @Override
  protected InProcessChannelBuilder newChannelBuilder(String name) {
    return InProcessChannelBuilder.forName(name);
  }

  @Override
  protected void configureSecurity(final InProcessChannelBuilder builder, final String name) {
  }
}
