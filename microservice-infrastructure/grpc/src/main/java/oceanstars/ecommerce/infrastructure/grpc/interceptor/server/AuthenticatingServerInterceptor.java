package oceanstars.ecommerce.infrastructure.grpc.interceptor.server;

import io.grpc.Context;
import io.grpc.ServerInterceptor;
import org.springframework.security.core.context.SecurityContext;

/**
 * gRPC服务端认证拦截器接口
 *
 * @author Clover
 * @version 1.0.0
 * @since 2024/1/21 15:34
 */
public interface AuthenticatingServerInterceptor extends ServerInterceptor {

  /**
   * 安全上下文键
   */
  Context.Key<SecurityContext> SECURITY_CONTEXT_KEY = Context.key("security-context");
}
