package oceanstars.ecommerce.infrastructure.grpc.config.server;

import oceanstars.ecommerce.infrastructure.grpc.interceptor.server.AuthenticatingServerInterceptor;
import oceanstars.ecommerce.infrastructure.grpc.interceptor.server.AuthorizationCheckingServerInterceptor;
import oceanstars.ecommerce.infrastructure.grpc.interceptor.server.DefaultAuthenticatingServerInterceptor;
import oceanstars.ecommerce.infrastructure.grpc.interceptor.server.ExceptionTranslatingServerInterceptor;
import oceanstars.ecommerce.infrastructure.grpc.interceptor.server.GrpcGlobalServerInterceptor;
import oceanstars.ecommerce.infrastructure.grpc.security.server.authentication.GrpcAuthenticationReader;
import oceanstars.ecommerce.infrastructure.grpc.security.server.check.AccessPredicateManager;
import oceanstars.ecommerce.infrastructure.grpc.security.server.check.GrpcSecurityMetadataSource;
import oceanstars.ecommerce.infrastructure.grpc.util.InterceptorOrder;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;

/**
 * gRPC服务端安全配置
 *
 * @author Clover
 * @version 1.0.0
 * @since 2024/1/21 21:10
 */
@AutoConfiguration(after = {SecurityAutoConfiguration.class})
@ConditionalOnBean(AuthenticationManager.class)
public class GrpcServerSecurityConfiguration {

  @Bean
  @ConditionalOnMissingBean
  @GrpcGlobalServerInterceptor
  @Order(InterceptorOrder.ORDER_SECURITY_EXCEPTION_HANDLING)
  public ExceptionTranslatingServerInterceptor exceptionTranslatingServerInterceptor() {
    return new ExceptionTranslatingServerInterceptor();
  }

  @Bean
  @ConditionalOnMissingBean(AuthenticatingServerInterceptor.class)
  @ConditionalOnBean({AuthenticationManager.class, GrpcAuthenticationReader.class})
  @GrpcGlobalServerInterceptor
  @Order(InterceptorOrder.ORDER_SECURITY_AUTHENTICATION)
  public DefaultAuthenticatingServerInterceptor authenticatingServerInterceptor(final AuthenticationManager authenticationManager,
      final GrpcAuthenticationReader authenticationReader) {
    return new DefaultAuthenticatingServerInterceptor(authenticationManager, authenticationReader);
  }

  @Bean
  @ConditionalOnMissingBean
  @ConditionalOnBean({GrpcSecurityMetadataSource.class})
  public AccessPredicateManager accessPredicateManager(final GrpcSecurityMetadataSource grpcSecurityMetadataSource) {
    return new AccessPredicateManager(grpcSecurityMetadataSource);
  }

  @Bean
  @ConditionalOnMissingBean
  @ConditionalOnBean({AccessPredicateManager.class})
  @GrpcGlobalServerInterceptor
  @Order(InterceptorOrder.ORDER_SECURITY_AUTHORISATION)
  public AuthorizationCheckingServerInterceptor authorizationCheckingServerInterceptor(final AccessPredicateManager accessPredicateManager) {
    return new AuthorizationCheckingServerInterceptor(accessPredicateManager);
  }
}
