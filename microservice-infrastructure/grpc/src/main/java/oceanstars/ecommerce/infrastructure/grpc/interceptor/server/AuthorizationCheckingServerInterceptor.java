package oceanstars.ecommerce.infrastructure.grpc.interceptor.server;

import io.grpc.Metadata;
import io.grpc.ServerCall;
import io.grpc.ServerCall.Listener;
import io.grpc.ServerCallHandler;
import io.grpc.ServerInterceptor;
import oceanstars.ecommerce.infrastructure.grpc.security.server.check.AccessPredicateManager;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * <此类的功能说明>
 *
 * @author Clover
 * @version 1.0.0
 * @since 2024/1/21 20:47
 */
public class AuthorizationCheckingServerInterceptor implements ServerInterceptor {

  /**
   * 权限管理器
   */
  private final AccessPredicateManager authorizationManager;

  /**
   * 构造函数
   *
   * @param authorizationManager 权限管理器
   */
  public AuthorizationCheckingServerInterceptor(final AccessPredicateManager authorizationManager) {
    this.authorizationManager = authorizationManager;
  }

  @Override
  public <ReqT, RespT> Listener<ReqT> interceptCall(ServerCall<ReqT, RespT> serverCall, Metadata metadata,
      ServerCallHandler<ReqT, RespT> serverCallHandler) {

    // 验证gRPC请求是否有权限访问
    this.authorizationManager.verify(() -> SecurityContextHolder.getContextHolderStrategy().getContext().getAuthentication(), serverCall);
    return serverCallHandler.startCall(serverCall, metadata);
  }
}
