package oceanstars.ecommerce.infrastructure.grpc.interceptor.server;

import io.grpc.Context;
import io.grpc.Metadata;
import io.grpc.ServerCall;
import io.grpc.ServerCall.Listener;
import io.grpc.ServerCallHandler;
import oceanstars.ecommerce.infrastructure.grpc.security.server.authentication.GrpcAuthenticationReader;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * <此类的功能说明>
 *
 * @author Clover
 * @version 1.0.0
 * @since 2024/1/21 15:50
 */
public class DefaultAuthenticatingServerInterceptor implements AuthenticatingServerInterceptor {

  /**
   * Spring Security认证管理器
   */
  private final AuthenticationManager authenticationManager;

  /**
   * gRPC认证读取器
   */
  private final GrpcAuthenticationReader grpcAuthenticationReader;

  /**
   * 构造函数
   *
   * @param authenticationManager Spring Security认证管理器
   * @param authenticationReader  gRPC认证读取器
   */
  public DefaultAuthenticatingServerInterceptor(final AuthenticationManager authenticationManager,
      final GrpcAuthenticationReader authenticationReader) {
    this.authenticationManager = authenticationManager;
    this.grpcAuthenticationReader = authenticationReader;
  }

  @Override
  public <ReqT, RespT> Listener<ReqT> interceptCall(ServerCall<ReqT, RespT> serverCall, Metadata metadata,
      ServerCallHandler<ReqT, RespT> serverCallHandler) {

    // 读取gRPC认证信息
    Authentication authentication = this.grpcAuthenticationReader.readAuthentication(serverCall, metadata);
    // 如果认证信息为空，则直接调用服务调用处理器
    if (authentication == null) {
      try {
        return serverCallHandler.startCall(serverCall, metadata);
      } catch (final AccessDeniedException e) {
        throw newNoCredentialsException(e);
      }
    }

    // 如果认证信息不为空，检查身份验证的详细信息，确保在后续身份验证过程中能够访问到额外的详细信息
    if (authentication.getDetails() == null && authentication instanceof AbstractAuthenticationToken) {
      ((AbstractAuthenticationToken) authentication).setDetails(serverCall.getAttributes());
    }
    try {
      // 执行身份验证
      authentication = this.authenticationManager.authenticate(authentication);
    } catch (final AuthenticationException e) {
      // 处理身份验证失败的情况，并重新抛出异常
      this.onUnsuccessfulAuthentication(serverCall, metadata, e);
      throw e;
    }

    // 如果身份验证成功，则创建并设置安全上下文
    final SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
    securityContext.setAuthentication(authentication);
    SecurityContextHolder.setContext(securityContext);
    // 创建gRPC上下文,使用常量标记认证安全上下文
    final Context grpcContext = Context.current().withValue(SECURITY_CONTEXT_KEY, securityContext);
    // 将创建的 gRPC 上下文附加到当前线程
    final Context previousContext = grpcContext.attach();
    // 处理成功身份验证
    onSuccessfulAuthentication(serverCall, metadata, authentication);

    try {
      // 创建身份验证后的服务调用监听器
      return new AuthenticatingServerCallListener<>(serverCallHandler.startCall(serverCall, metadata), grpcContext, securityContext);
    }
    // 授权失败，捕获AccessDeniedException异常
    catch (final AccessDeniedException ex) {
      // 如果是匿名用户，则抛出无凭证异常，否则重新抛出原始的 AccessDeniedException 异常
      if (authentication instanceof AnonymousAuthenticationToken) {
        throw newNoCredentialsException(ex);
      }
      throw ex;
    } finally {
      // 清除安全上下文
      SecurityContextHolder.clearContext();
      // 清除gRPC上下文
      grpcContext.detach(previousContext);
    }
  }

  /**
   * 钩子函数，在身份验证成功时调用
   *
   * @param call           gRPC服务调用
   * @param metadata       gRPC元数据
   * @param authentication 身份验证信息
   */
  protected void onSuccessfulAuthentication(final ServerCall<?, ?> call, final Metadata metadata, final Authentication authentication) {
  }

  /**
   * 钩子函数，在身份验证失败时调用
   *
   * @param call     gRPC服务调用
   * @param metadata gRPC元数据
   * @param failed   身份验证异常
   */
  protected void onUnsuccessfulAuthentication(final ServerCall<?, ?> call, final Metadata metadata, final AuthenticationException failed) {
  }

  /**
   * 将AccessDeniedException包装入AuthenticationException
   *
   * @param denied AccessDeniedException异常
   * @return AuthenticationException异常
   */
  private static AuthenticationException newNoCredentialsException(final AccessDeniedException denied) {
    return new BadCredentialsException("请求中没有找到凭据!", denied);
  }

  /**
   * 内部私有类，用于gRPC服务端实现认证监听器
   *
   * @author Clover
   * @version 1.0.0
   * @since 2024/1/21 15:57
   */
  private static class AuthenticatingServerCallListener<ReqT> extends AbstractAuthenticatingServerCallListener<ReqT> {

    /**
     * Spring Security安全上下文
     */
    private final SecurityContext securityContext;

    /**
     * 构造函数
     *
     * @param delegate        委托监听器
     * @param grpcContext     gRPC上下文
     * @param securityContext Spring Security安全上下文
     */
    public AuthenticatingServerCallListener(final Listener<ReqT> delegate, final Context grpcContext,
        final SecurityContext securityContext) {
      super(delegate, grpcContext);
      this.securityContext = securityContext;
    }

    @Override
    protected void attachAuthenticationContext() {
      SecurityContextHolder.setContext(this.securityContext);
    }

    @Override
    protected void detachAuthenticationContext() {
      SecurityContextHolder.clearContext();
    }

    @Override
    public void onHalfClose() {
      try {
        super.onHalfClose();
      } catch (final AccessDeniedException ex) {
        // 如果是匿名用户，则抛出无凭证异常
        if (this.securityContext.getAuthentication() instanceof AnonymousAuthenticationToken) {
          throw newNoCredentialsException(ex);
        } else {
          throw ex;
        }
      }
    }
  }
}
