package oceanstars.ecommerce.infrastructure.grpc.interceptor.client;

import io.grpc.ClientInterceptor;
import java.util.List;

/**
 * 全局客户端拦截器配置函数式接口
 *
 * @author Clover
 * @version 1.0.0
 * @since 2021/11/12 1:29 下午
 */
@FunctionalInterface
public interface GlobalClientInterceptorConfigurer {

  /**
   * 配置给定的客户端拦截器属性
   *
   * @param interceptors 等待配置的客户端拦截器列表
   */
  void configureClientInterceptors(List<ClientInterceptor> interceptors);
}
