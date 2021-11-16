package oceanstars.ecommerce.infrastructure.grpc.service.provider;

import io.grpc.ServerInterceptor;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

/**
 * 注解: 标记需要被注册进gRPC服务器的gRPC服务。只能应用于(GrpcService-ImplBase)的实现。
 *
 * @author Clover
 * @version 1.0.0
 * @since 2021/11/8 11:08 下午
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Service
@Bean
public @interface GrpcService {

  /**
   * 配置当前服务拦截器类列表
   *
   * @return 当前服务拦截器类列表
   */
  Class<? extends ServerInterceptor>[] interceptors() default {};

  /**
   * 配置当前服务拦截器类名列表
   *
   * @return 当前服务拦截器类名列表
   */
  String[] interceptorNames() default {};

  /**
   * 自定义拦截器是否混入全局拦截器中并进行排序
   *
   * @return 自定义拦截器混入全局拦截器中并进行排的序启动标志位
   */
  boolean sortInterceptors() default false;
}
