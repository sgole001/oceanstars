package oceanstars.ecommerce.infrastructure.grpc.interceptor.server;

import io.grpc.ForwardingServerCallListener.SimpleForwardingServerCallListener;
import io.grpc.Metadata;
import io.grpc.ServerCall;
import io.grpc.ServerCall.Listener;
import io.grpc.ServerCallHandler;
import io.grpc.ServerInterceptor;
import io.grpc.Status;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;

/**
 * 服务端全局拦截器：处理AuthenticationException和AccessDeniedException异常适配合适的gRPC响应状态
 *
 * @author Clover
 * @version 1.0.0
 * @since 2024/1/21 15:07
 */
public class ExceptionTranslatingServerInterceptor implements ServerInterceptor {

  /**
   * 认证失败响应状态描述
   */
  public static final String UNAUTHENTICATED_DESCRIPTION = "认证失败";

  /**
   * 授权失败拒绝访问响应状态描述
   */
  public static final String ACCESS_DENIED_DESCRIPTION = "拒绝访问";

  @Override
  public <ReqT, RespT> Listener<ReqT> interceptCall(ServerCall<ReqT, RespT> serverCall, Metadata metadata,
      ServerCallHandler<ReqT, RespT> serverCallHandler) {

    try {
      // 在客户端完成消息发送时检查是否有认证失败或授权失败的异常，并进行相应的处理
      return new ExceptionTranslatorServerCallListener<>(serverCallHandler.startCall(serverCall, metadata), serverCall);
    } catch (final AuthenticationException ex) {
      // 认证失败，返回UNAUTHENTICATED状态
      this.closeCallUnauthenticated(serverCall, ex);
    } catch (final AccessDeniedException ex) {
      // 授权失败，返回PERMISSION_DENIED状态
      this.closeCallAccessDenied(serverCall, ex);
    }
    // 返回无操作的gRPC服务调用监听器
    return this.noOpCallListener();
  }

  /**
   * 无操作的gRPC服务调用监听器
   *
   * @param <ReqT> gRPC请求类型
   * @return 无操作的gRPC服务调用监听器
   */
  protected <ReqT> Listener<ReqT> noOpCallListener() {
    return new Listener<ReqT>() {
    };
  }

  /**
   * 关闭gRPC服务调用请求
   *
   * @param call      gRPC服务调用请求对象
   * @param exception 认证异常
   */
  protected void closeCallUnauthenticated(final ServerCall<?, ?> call, final AuthenticationException exception) {
    call.close(Status.UNAUTHENTICATED.withCause(exception).withDescription(UNAUTHENTICATED_DESCRIPTION), new Metadata());
  }

  /**
   * 关闭gRPC服务调用请求
   *
   * @param call      gRPC服务调用请求对象
   * @param exception 授权异常
   */
  protected void closeCallAccessDenied(final ServerCall<?, ?> call, final AccessDeniedException exception) {
    call.close(Status.PERMISSION_DENIED.withCause(exception).withDescription(ACCESS_DENIED_DESCRIPTION), new Metadata());
  }

  /**
   * 内部私有类：在客户端完成消息发送时检查是否有认证失败或授权失败的异常，并进行相应的处理
   *
   * @author Clover
   * @version 1.0.0
   * @since 2024/1/21 15:18
   */
  private class ExceptionTranslatorServerCallListener<ReqT, RespT> extends SimpleForwardingServerCallListener<ReqT> {

    /**
     * gRPC服务调用请求对象
     */
    private final ServerCall<ReqT, RespT> call;

    /**
     * 构造函数
     *
     * @param delegate gRPC服务调用监听器
     * @param call     gRPC服务调用请求对象
     */
    protected ExceptionTranslatorServerCallListener(final Listener<ReqT> delegate, final ServerCall<ReqT, RespT> call) {
      super(delegate);
      this.call = call;
    }

    @Override
    public void onHalfClose() {

      // 客户端完成消息发送（仍然可以接收服务端的响应），服务端处理
      try {
        super.onHalfClose();
      } catch (final AuthenticationException ex) {
        // 认证失败，返回UNAUTHENTICATED状态
        closeCallUnauthenticated(this.call, ex);
      } catch (final AccessDeniedException ex) {
        // 授权失败，返回PERMISSION_DENIED状态
        closeCallAccessDenied(this.call, ex);
      }
    }
  }
}
