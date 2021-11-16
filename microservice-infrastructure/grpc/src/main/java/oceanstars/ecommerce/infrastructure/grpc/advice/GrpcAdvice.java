package oceanstars.ecommerce.infrastructure.grpc.advice;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springframework.stereotype.Component;

/**
 * 注解：特殊组件用以标注全局gRPC异常处理
 *
 * @author Clover
 * @version 1.0.0
 * @since 2021/11/9 10:52 下午
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
public @interface GrpcAdvice {

}
