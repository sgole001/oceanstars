package oceanstars.ecommerce.infrastructure.grpc.interceptor.client;

import io.grpc.ClientInterceptor;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * 注解：标记{@link ClientInterceptor}作为gRPC客户端全局拦截器
 *
 * @author Clover
 * @version 1.0.0
 * @since 2021/11/12 1:21 下午
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
@Bean
public @interface GrpcGlobalClientInterceptor {

}
