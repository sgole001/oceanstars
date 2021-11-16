package oceanstars.ecommerce.infrastructure.grpc.interceptor.server;

import static java.util.Objects.requireNonNull;

import io.grpc.Metadata;
import io.grpc.ServerCall;
import io.grpc.ServerCall.Listener;
import io.grpc.ServerCallHandler;
import io.grpc.ServerInterceptor;
import org.springframework.core.Ordered;

/**
 * 服务拦截器实现类，为指定的拦截器分配顺序
 *
 * @author Clover
 * @version 1.0.0
 * @since 2021/11/9 11:49 上午
 */
public class OrderedServerInterceptor implements ServerInterceptor, Ordered {

  /**
   * 服务拦截器
   */
  private final ServerInterceptor serverInterceptor;

  /**
   * 拦截器顺序
   */
  private final int order;

  /**
   * 构造函数
   *
   * @param serverInterceptor 服务拦截器
   * @param order             拦截器顺序
   */
  public OrderedServerInterceptor(ServerInterceptor serverInterceptor, int order) {
    this.serverInterceptor = requireNonNull(serverInterceptor, "serverInterceptor");
    this.order = order;
  }

  @Override
  public <ReqT, RespT> Listener<ReqT> interceptCall(ServerCall<ReqT, RespT> serverCall, Metadata metadata,
      ServerCallHandler<ReqT, RespT> serverCallHandler) {
    return this.serverInterceptor.interceptCall(serverCall, metadata, serverCallHandler);
  }

  @Override
  public int getOrder() {
    return this.order;
  }

  @Override
  public String toString() {
    return "OrderedServerInterceptor [interceptor=" + this.serverInterceptor + ", order=" + this.order + "]";
  }
}
