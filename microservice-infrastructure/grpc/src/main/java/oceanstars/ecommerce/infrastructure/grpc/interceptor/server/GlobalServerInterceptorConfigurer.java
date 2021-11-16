package oceanstars.ecommerce.infrastructure.grpc.interceptor.server;

import io.grpc.ServerInterceptor;
import java.util.List;

/**
 * 全局服务拦截器配置函数式接口
 *
 * @author Clover
 * @version 1.0.0
 * @since 2021/11/9 12:03 上午
 */
@FunctionalInterface
public interface GlobalServerInterceptorConfigurer {

  /**
   * 配置给定的服务拦截器列表（增加新元素，删除不需要的元素或者重新排序等等）
   *
   * @param interceptors 服务拦截器列表
   */
  void configureServerInterceptors(List<ServerInterceptor> interceptors);
}
