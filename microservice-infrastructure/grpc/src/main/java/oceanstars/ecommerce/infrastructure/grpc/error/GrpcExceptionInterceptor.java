package oceanstars.ecommerce.infrastructure.grpc.error;

import static java.util.Objects.requireNonNull;

import io.grpc.Metadata;
import io.grpc.ServerCall;
import io.grpc.ServerCall.Listener;
import io.grpc.ServerCallHandler;
import io.grpc.ServerInterceptor;

/**
 * gRPC服务端异常拦截器
 *
 * @author Clover
 * @version 1.0.0
 * @since 2021/11/11 6:05 下午
 */
public class GrpcExceptionInterceptor implements ServerInterceptor {

  /**
   * 异常处理器接口：处理在gRPC调用执行过程中发生的异常错误信息
   */
  private final GrpcExceptionResponseHandler exceptionHandler;

  /**
   * 构造函数
   *
   * @param grpcAdviceExceptionHandler 异常处理器接口
   */
  public GrpcExceptionInterceptor(final GrpcExceptionResponseHandler grpcAdviceExceptionHandler) {
    this.exceptionHandler = requireNonNull(grpcAdviceExceptionHandler, "grpcAdviceExceptionHandler");
  }

  @Override
  public <ReqT, RespT> Listener<ReqT> interceptCall(ServerCall<ReqT, RespT> call, Metadata headers, ServerCallHandler<ReqT, RespT> next) {

    try {

      // 创建gRPC调用过程中的异常处理的ServerCall实例对象
      final GrpcExceptionServerCall<ReqT, RespT> handledCall = new GrpcExceptionServerCall<>(call, this.exceptionHandler);
      // 处理拦截器链条上的下一个处理
      final Listener<ReqT> delegate = next.startCall(handledCall, headers);

      // 返回non-null的gRPC调用异常监听器处理不同阶段的请求处理回调
      return new GrpcExceptionListener<>(delegate, call, this.exceptionHandler);
    } catch (final Throwable error) {
      this.exceptionHandler.handleError(call, error);
      return noOpCallListener();
    }
  }

  /**
   * 创建no-op请求回调（请求可以既不返回null也不抛出异常）
   *
   * @param <ReqT> 请求泛型
   * @return 服务请求回调实例
   */
  protected <ReqT> Listener<ReqT> noOpCallListener() {
    return new Listener<>() {
    };
  }
}
