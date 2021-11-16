package oceanstars.ecommerce.infrastructure.grpc.config.server;

import oceanstars.ecommerce.infrastructure.grpc.advice.GrpcAdviceDiscoverer;
import oceanstars.ecommerce.infrastructure.grpc.advice.GrpcAdviceExceptionHandler;
import oceanstars.ecommerce.infrastructure.grpc.advice.GrpcAdvicePremiseCondition;
import oceanstars.ecommerce.infrastructure.grpc.advice.GrpcExceptionHandlerMethodResolver;
import oceanstars.ecommerce.infrastructure.grpc.error.GrpcExceptionInterceptor;
import oceanstars.ecommerce.infrastructure.grpc.interceptor.server.GrpcGlobalServerInterceptor;
import oceanstars.ecommerce.infrastructure.grpc.util.InterceptorOrder;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

/**
 * gRPC异常配置类
 *
 * @author Clover
 * @version 1.0.0
 * @since 2021/11/11 5:57 下午
 */
@Configuration(proxyBeanMethods = false)
@Conditional(GrpcAdvicePremiseCondition.class)
@AutoConfigureBefore(GrpcServerFactoryConfiguration.class)
public class GrpcAdviceConfiguration {

  /**
   * 配置注入gRPC异常处理方法发现Bean
   *
   * @return gRPC异常处理方法发现Bean
   */
  @Bean
  public GrpcAdviceDiscoverer grpcAdviceDiscoverer() {
    return new GrpcAdviceDiscoverer();
  }

  /**
   * 配置注入gRPC异常处理方法解析Bean
   *
   * @param grpcAdviceDiscoverer gRPC异常处理方法发现接口
   * @return gRPC异常处理方法解析Bean
   */
  @Bean
  public GrpcExceptionHandlerMethodResolver grpcExceptionHandlerMethodResolver(final GrpcAdviceDiscoverer grpcAdviceDiscoverer) {
    return new GrpcExceptionHandlerMethodResolver(grpcAdviceDiscoverer);
  }

  /**
   * 配置注入gRPC异常处理Bean
   *
   * @param grpcExceptionHandlerMethodResolver gRPC异常处理方法解析Bean
   * @return gRPC异常处理Bean
   */
  @Bean
  public GrpcAdviceExceptionHandler grpcAdviceExceptionHandler(GrpcExceptionHandlerMethodResolver grpcExceptionHandlerMethodResolver) {
    return new GrpcAdviceExceptionHandler(grpcExceptionHandlerMethodResolver);
  }

  /**
   * 配置注入gRPC服务端异常拦截器
   *
   * @param grpcAdviceExceptionHandler gRPC异常处理Bean
   * @return gRPC服务端异常拦截器
   */
  @GrpcGlobalServerInterceptor
  @Order(InterceptorOrder.ORDER_GLOBAL_EXCEPTION_HANDLING)
  public GrpcExceptionInterceptor grpcAdviceExceptionInterceptor(GrpcAdviceExceptionHandler grpcAdviceExceptionHandler) {
    return new GrpcExceptionInterceptor(grpcAdviceExceptionHandler);
  }
}
