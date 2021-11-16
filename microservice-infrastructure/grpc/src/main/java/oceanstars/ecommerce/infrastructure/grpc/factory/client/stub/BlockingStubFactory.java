package oceanstars.ecommerce.infrastructure.grpc.factory.client.stub;

import io.grpc.stub.AbstractBlockingStub;
import io.grpc.stub.AbstractStub;

/**
 * 创建一元阻塞Stub工厂类
 *
 * @author Clover
 * @version 1.0.0
 * @since 2021/11/15 7:25 下午
 */
public class BlockingStubFactory extends AbstractStubFactory {

  @Override
  public <T extends AbstractStub<T>> boolean isApplicable(Class<T> stubType) {
    return AbstractBlockingStub.class.isAssignableFrom(stubType);
  }

  @Override
  protected String getFactoryMethodName() {
    return "newBlockingStub";
  }
}
