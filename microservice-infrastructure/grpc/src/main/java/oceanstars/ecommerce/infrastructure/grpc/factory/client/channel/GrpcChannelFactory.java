package oceanstars.ecommerce.infrastructure.grpc.factory.client.channel;

import io.grpc.Channel;
import io.grpc.ClientInterceptor;
import io.grpc.ConnectivityState;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * gRPC客户端通道工厂接口
 *
 * @author Clover
 * @version 1.0.0
 * @since 2021/11/12 1:09 下午
 */
public interface GrpcChannelFactory extends AutoCloseable {

  /**
   * 为指定gRPC服务创建连接通道
   *
   * @param name gRPC远程调用服务名
   * @return 连接指定服务的gRPC通道
   */
  default Channel createChannel(final String name) {
    return createChannel(name, Collections.emptyList());
  }

  /**
   * 为指定gRPC服务创建连接通道,并为通道指定客户端拦截器{@link ClientInterceptor}列表（不排序拦截器）
   *
   * @param name         gRPC远程调用服务名
   * @param interceptors 客户端拦截器列表
   * @return 连接指定服务的gRPC通道
   */
  default Channel createChannel(final String name, final List<ClientInterceptor> interceptors) {
    return createChannel(name, interceptors, false);
  }

  /**
   * 为指定gRPC服务创建连接通道,并为通道指定客户端拦截器{@link ClientInterceptor}列表,可指定是否排序拦截器
   *
   * @param name             gRPC远程调用服务名
   * @param interceptors     客户端拦截器列表
   * @param sortInterceptors 是否排序拦截器
   * @return 连接指定服务的gRPC通道
   */
  Channel createChannel(String name, List<ClientInterceptor> interceptors, boolean sortInterceptors);

  /**
   * 获取通道和当前其状态的映射
   *
   * @return 通道和当前其状态的映射
   */
  default Map<String, ConnectivityState> getConnectivityState() {
    return Collections.emptyMap();
  }

  /**
   * 资源自动关闭
   */
  @Override
  void close();

}
