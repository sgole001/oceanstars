package oceanstars.ecommerce.infrastructure.grpc.error;

import io.grpc.ForwardingServerCall.SimpleForwardingServerCall;
import io.grpc.Metadata;
import io.grpc.ServerCall;
import io.grpc.Status;
import io.grpc.Status.Code;

/**
 * ServerCal实现类：处理gRPC调用过程中的异常处理
 *
 * @author Clover
 * @version 1.0.0
 * @since 2021/11/10 5:31 下午
 */
public class GrpcExceptionServerCall<ReqT, RespT> extends SimpleForwardingServerCall<ReqT, RespT> {

  /**
   * 异常处理器接口：处理在gRPC调用执行过程中发生的异常错误信息
   */
  private final GrpcExceptionResponseHandler exceptionHandler;

  /**
   * 构造函数
   *
   * @param delegate         服务调用请求代理
   * @param exceptionHandler 异常处理器接口
   */
  protected GrpcExceptionServerCall(final ServerCall<ReqT, RespT> delegate, final GrpcExceptionResponseHandler exceptionHandler) {
    super(delegate);
    this.exceptionHandler = exceptionHandler;
  }

  @Override
  public void close(final Status status, final Metadata trailers) {

    // 通过StreamObserver#onError发送错误响应信息
    if (status.getCode() == Code.UNKNOWN && status.getCause() != null) {
      final Throwable cause = status.getCause();
      this.exceptionHandler.handleError(delegate(), cause);
    } else {
      super.close(status, trailers);
    }
  }
}
