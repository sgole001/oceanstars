package oceanstars.ecommerce.infrastructure.grpc.service.provider;

import io.grpc.ServerServiceDefinition;

/**
 * 包含有关grpc服务的所有相关信息的容器类
 *
 * @param beanName   Spring容器中gRPC服务Bean名
 * @param beanClazz  gRPC服务Bean类
 * @param definition gRPC服务定义数据
 * @author Clover
 * @version 1.0.0
 * @since 2021/11/8 11:24 下午
 */
public record GrpcServiceDefinition(String beanName, Class<?> beanClazz, ServerServiceDefinition definition) {

  /**
   * 构造函数，创建gRPC服务定义
   *
   * @param beanName   Spring容器中gRPC服务Bean名
   * @param beanClazz  gRPC服务Bean类
   * @param definition gRPC服务定义数据
   */
  public GrpcServiceDefinition {
  }

  /**
   * 获取gRPC服务Bean名
   *
   * @return gRPC服务Bean名
   */
  @Override
  public String beanName() {
    return this.beanName;
  }

  /**
   * 获取gRPC服务Bean类
   *
   * @return gRPC服务Bean类
   */
  @Override
  public Class<?> beanClazz() {
    return this.beanClazz;
  }

  /**
   * 获取gRPC服务定义数据
   *
   * @return gRPC服务定义数据
   */
  @Override
  public ServerServiceDefinition definition() {
    return this.definition;
  }
}
