package oceanstars.ecommerce.infrastructure.grpc.service.provider;

import java.util.Collection;

/**
 * gRPC服务发现用函数式接口
 *
 * @author Clover
 * @version 1.0.0
 * @since 2021/11/8 11:26 下午
 */
@FunctionalInterface
public interface GrpcServiceDiscoverer {

  /**
   * 查找服务器应提供的grpc服务
   *
   * @return grpc服务列表
   */
  Collection<GrpcServiceDefinition> findGrpcServices();
}
