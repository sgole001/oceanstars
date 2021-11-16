package oceanstars.ecommerce.infrastructure.grpc.interceptor.server;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * 注解: 标记gRPC服务拦截器作为全局配置
 *
 * @author Clover
 * @version 1.0.0
 * @since 2021/11/9 11:42 上午
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
@Bean
public @interface GrpcGlobalServerInterceptor {

}
