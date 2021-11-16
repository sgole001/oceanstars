package oceanstars.ecommerce.infrastructure.grpc.interceptor.client;

import static java.util.Objects.requireNonNull;

import io.grpc.CallOptions;
import io.grpc.Channel;
import io.grpc.ClientCall;
import io.grpc.ClientInterceptor;
import io.grpc.MethodDescriptor;
import org.springframework.core.Ordered;

/**
 * 客户端拦截器实现类类，为指定的拦截器分配顺序
 *
 * @author Clover
 * @version 1.0.0
 * @since 2021/11/12 1:26 下午
 */
public class OrderedClientInterceptor implements ClientInterceptor, Ordered {

  /**
   * 客户端拦截器接口
   */
  private final ClientInterceptor clientInterceptor;

  /**
   * 拦截器顺序
   */
  private final int order;

  /**
   * 构造函数
   *
   * @param clientInterceptor 客户端拦截器接口
   * @param order             拦截器顺序
   */
  public OrderedClientInterceptor(ClientInterceptor clientInterceptor, int order) {
    this.clientInterceptor = requireNonNull(clientInterceptor, "clientInterceptor");
    this.order = order;
  }

  @Override
  public <ReqT, RespT> ClientCall<ReqT, RespT> interceptCall(MethodDescriptor<ReqT, RespT> methodDescriptor, CallOptions callOptions, Channel next) {
    return this.clientInterceptor.interceptCall(methodDescriptor, callOptions, next);
  }

  @Override
  public int getOrder() {
    return this.order;
  }
}
