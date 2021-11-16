package oceanstars.ecommerce.infrastructure.grpc.codec;

import java.util.Collection;

/**
 * gRPC编解码器发现函数式接口
 *
 * @author Clover
 * @version 1.0.0
 * @since 2021/11/11 1:20 下午
 */
@FunctionalInterface
public interface GrpcCodecDiscoverer {

  /**
   * 查找客户端/服务端应该被使用的gRPC编解码器集合
   *
   * @return gRPC编解码器集合（不会为NULL）
   */
  Collection<GrpcCodecDefinition> findGrpcCodecs();
}
