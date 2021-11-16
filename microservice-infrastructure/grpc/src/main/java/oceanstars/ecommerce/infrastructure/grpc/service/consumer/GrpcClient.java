package oceanstars.ecommerce.infrastructure.grpc.service.consumer;

import io.grpc.Channel;
import io.grpc.ClientInterceptor;
import io.grpc.stub.AbstractStub;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 注解: 标记类型为{@link Channel}或者{@link AbstractStub}的子类类型的字段/grpc客户端服务。不能和{@link Autowired}一起使用
 *
 * @author Clover
 * @version 1.0.0
 * @since 2021/11/11 11:52 下午
 */
@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface GrpcClient {

  /**
   * 指定gRPC客户端名。可以通过此名获取对应的通道属性
   *
   * @return grp客户端名
   */
  String value();

  /**
   * 指定gRPC客户端拦截器Class列表。
   *
   * @return gRPC客户端拦截器Class列表。
   */
  Class<? extends ClientInterceptor>[] interceptors() default {};

  /**
   * 指定gRPC客户端拦截器类名列表。
   *
   * @return gRPC客户端拦截器类名列表
   */
  String[] interceptorNames() default {};

  /**
   * 自定义拦截器是否混入全局拦截器中并进行排序
   *
   * @return 自定义拦截器混入全局拦截器中并进行排的序启动标志位
   */
  boolean sortInterceptors() default false;
}
