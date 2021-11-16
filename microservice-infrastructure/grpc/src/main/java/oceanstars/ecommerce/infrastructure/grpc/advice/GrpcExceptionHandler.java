package oceanstars.ecommerce.infrastructure.grpc.advice;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 注解：处理被标记的方法相关的异常，方法返回类型必须是{@link io.grpc.Status}, {@link io.grpc.StatusException},{@link io.grpc.StatusRuntimeException} or {@link Throwable}.
 *
 * @author Clover
 * @version 1.0.0
 * @since 2021/11/10 12:37 上午
 */
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface GrpcExceptionHandler {

  /**
   * 标记当前方法所有处理的异常列表，默认为空处理所有方法抛出的异常
   */
  Class<? extends Throwable>[] value() default {};
}
