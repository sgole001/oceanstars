package oceanstars.ecommerce.infrastructure.grpc.service.consumer;

import io.grpc.stub.AbstractStub;

/**
 * gRPC客户端Stub改造用函数接口
 *
 * @author Clover
 * @version 1.0.0
 * @since 2021/11/16 12:39 上午
 */
@FunctionalInterface
public interface StubTransformer {

  /**
   * 改造指定gRPC客户端Stub
   *
   * @param name gRPC客户端名
   * @param stub gRPC客户端Stub
   * @return 改造后的gRPC客户端Stub
   */
  <T extends AbstractStub<T>> AbstractStub<T> transform(String name, AbstractStub<T> stub);
}
