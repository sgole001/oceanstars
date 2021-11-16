package oceanstars.ecommerce.infrastructure.grpc.error;

import io.grpc.ForwardingServerCallListener.SimpleForwardingServerCallListener;
import io.grpc.ServerCall;

/**
 * gRPC服务请求异常处理回调
 *
 * @author Clover
 * @version 1.0.0
 * @since 2021/11/10 5:36 下午
 */
public class GrpcExceptionListener<ReqT, RespT> extends SimpleForwardingServerCallListener<ReqT> {

  /**
   * gRPC调用过程异常处理器接口
   */
  private final GrpcExceptionResponseHandler exceptionHandler;

  /**
   * 接收远程客户端调用接口
   */
  private final ServerCall<ReqT, RespT> serverCall;

  /**
   * 构造函数
   *
   * @param delegate         gRPC服务请求消费回调代理
   * @param serverCall       接收远程客户端gRPC调用接口
   * @param exceptionHandler gRPC调用过程异常处理器接口
   */
  protected GrpcExceptionListener(final ServerCall.Listener<ReqT> delegate, final ServerCall<ReqT, RespT> serverCall,
      final GrpcExceptionResponseHandler exceptionHandler) {

    super(delegate);
    this.serverCall = serverCall;
    this.exceptionHandler = exceptionHandler;
  }

  @Override
  public void onMessage(final ReqT message) {
    // 请求消息已经被接受的时候触发
    try {
      super.onMessage(message);
    } catch (final Throwable error) {
      this.exceptionHandler.handleError(this.serverCall, error);
    }
  }

  @Override
  public void onHalfClose() {
    // 客户端所有消息发送完成
    try {
      super.onHalfClose();
    } catch (final Throwable error) {
      this.exceptionHandler.handleError(this.serverCall, error);
    }
  }
}
