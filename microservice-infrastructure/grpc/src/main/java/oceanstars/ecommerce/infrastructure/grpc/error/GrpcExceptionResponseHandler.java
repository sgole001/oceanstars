package oceanstars.ecommerce.infrastructure.grpc.error;

import io.grpc.ServerCall;
import io.grpc.Status;

/**
 * 异常处理器接口：处理在gRPC调用执行过程中发生的异常错误信息
 *
 * @author Clover
 * @version 1.0.0
 * @since 2021/11/10 3:37 下午
 */
public interface GrpcExceptionResponseHandler {

  /**
   * 通过关闭gRPC调用，使用合适的{@link Status}处理异常信息
   *
   * @param serverCall 服务端调用接口（包装服务端收到的来自客户端远程调用信息）
   * @param error      异常信息
   */
  void handleError(final ServerCall<?, ?> serverCall, final Throwable error);
}
