package oceanstars.ecommerce.infrastructure.grpc.factory.server;

import io.grpc.Server;
import oceanstars.ecommerce.infrastructure.grpc.service.provider.GrpcServiceDefinition;

/**
 * gRPC服务器工厂接口
 *
 * @author Clover
 * @version 1.0.0
 * @since 2021/11/9 1:41 下午
 */
public interface GrpcServerFactory {

  /**
   * 根据已有配置选项，创建gRPC服务器
   *
   * @return gRPC服务器实例
   */
  Server createServer();

  /**
   * 获取gRPC服务器绑定的IP地址
   *
   * @return gRPC服务器绑定的IP地址
   */
  String getAddress();

  /**
   * 获取gRPC服务器使用的本地监听端口
   *
   * @return gRPC服务器绑定的IP地址
   */
  int getPort();

  /**
   * 添加gRPC服务至gRPC服务器
   *
   * @param service gRPC服务
   */
  void addService(GrpcServiceDefinition service);
}
