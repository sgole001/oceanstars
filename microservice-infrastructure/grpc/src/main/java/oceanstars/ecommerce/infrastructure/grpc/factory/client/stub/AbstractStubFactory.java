package oceanstars.ecommerce.infrastructure.grpc.factory.client.stub;

import io.grpc.Channel;
import io.grpc.stub.AbstractStub;
import java.lang.reflect.Method;
import org.springframework.beans.BeanInstantiationException;

/**
 * 根据gRPC提供的标准java类库创建客户端Stub的工厂虚类
 *
 * @author Clover
 * @version 1.0.0
 * @since 2021/11/15 6:43 下午
 */
public abstract class AbstractStubFactory implements StubFactory {

  @Override
  public <T extends AbstractStub<T>> AbstractStub<T> createStub(Class<T> stubType, Channel channel) {

    try {
      // 根据给定的Stub类型，获取对应的标准gRPC Java类库提供的创建Stub的静态方法名
      final String methodName = this.getFactoryMethodName();
      // 获取指定Stub类型的封闭类
      final Class<?> enclosingClass = stubType.getEnclosingClass();
      // 获取创建Stub的静态方法
      final Method factoryMethod = enclosingClass.getMethod(methodName, Channel.class);

      // 反射执行创建Stub的静态方法
      return stubType.cast(factoryMethod.invoke(null, channel));
    } catch (final Exception e) {
      throw new BeanInstantiationException(stubType, "gRPC客户端创建失败。", e);
    }
  }

  /**
   * 根据给定的Stub类型，获取对应的标准gRPC Java类库提供的创建Stub的静态方法名
   *
   * @return 标准gRPC Java类库提供的创建Stub的静态方法名
   */
  protected abstract String getFactoryMethodName();
}
