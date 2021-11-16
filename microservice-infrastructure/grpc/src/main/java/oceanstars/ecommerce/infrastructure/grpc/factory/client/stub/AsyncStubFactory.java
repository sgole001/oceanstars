package oceanstars.ecommerce.infrastructure.grpc.factory.client.stub;

import io.grpc.stub.AbstractAsyncStub;
import io.grpc.stub.AbstractStub;

/**
 * 创建异步Stub工厂类
 *
 * @author Clover
 * @version 1.0.0
 * @since 2021/11/15 7:01 下午
 */
public class AsyncStubFactory extends AbstractStubFactory {

  @Override
  public <T extends AbstractStub<T>> boolean isApplicable(Class<T> stubType) {
    return AbstractAsyncStub.class.isAssignableFrom(stubType);
  }

  @Override
  protected String getFactoryMethodName() {
    return "newStub";
  }
}
