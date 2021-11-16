package oceanstars.ecommerce.infrastructure.grpc.factory.client.stub;

import io.grpc.stub.AbstractFutureStub;
import io.grpc.stub.AbstractStub;

/**
 * 创建异步并行guava future(direct | callback) Stub工厂类
 *
 * @author Clover
 * @version 1.0.0
 * @since 2021/11/15 7:27 下午
 */
public class FutureStubFactory extends AbstractStubFactory {

  @Override
  public <T extends AbstractStub<T>> boolean isApplicable(Class<T> stubType) {
    return AbstractFutureStub.class.isAssignableFrom(stubType);
  }

  @Override
  protected String getFactoryMethodName() {
    return "newFutureStub";
  }
}
