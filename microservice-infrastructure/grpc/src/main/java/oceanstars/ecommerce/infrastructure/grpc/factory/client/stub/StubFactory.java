package oceanstars.ecommerce.infrastructure.grpc.factory.client.stub;

import io.grpc.Channel;
import io.grpc.stub.AbstractStub;

/**
 * gRPC客户端Stub工厂类
 *
 * @author Clover
 * @version 1.0.0
 * @since 2021/11/15 6:19 下午
 */
public interface StubFactory {

  /**
   * 根据指定类型创建gRPC客户端Stub
   *
   * @param stubType gRPC客户端Stub类型
   * @param channel  gRPC客户端连接通道
   * @return gRPC客户端Stub
   */
  <T extends AbstractStub<T>> AbstractStub<T> createStub(Class<T> stubType, Channel channel);

  /**
   * 判定是否匹配的Stub类型
   *
   * @param stubType Stub类型
   * @return 匹配结果
   */
  <T extends AbstractStub<T>> boolean isApplicable(Class<T> stubType);
}
