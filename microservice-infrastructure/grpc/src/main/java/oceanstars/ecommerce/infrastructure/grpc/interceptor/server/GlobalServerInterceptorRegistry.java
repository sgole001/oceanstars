package oceanstars.ecommerce.infrastructure.grpc.interceptor.server;

import static java.util.Objects.requireNonNull;

import com.google.common.collect.ImmutableList;
import io.grpc.ServerInterceptor;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.springframework.context.ApplicationContext;
import org.springframework.core.annotation.AnnotationAwareOrderComparator;

/**
 * 全局服务拦截器注册类
 *
 * @author Clover
 * @version 1.0.0
 * @since 2021/11/8 11:44 下午
 */
public class GlobalServerInterceptorRegistry {

  /**
   * Spring应用程序上下文环境
   */
  private final ApplicationContext applicationContext;

  /**
   * 服务拦截器列表（线程安全不可变）
   */
  private ImmutableList<ServerInterceptor> sortedServerInterceptors;

  /**
   * 构造函数：创建全局服务拦截器注册实例
   *
   * @param applicationContext Spring应用程序上下文环境
   */
  public GlobalServerInterceptorRegistry(final ApplicationContext applicationContext) {
    this.applicationContext = requireNonNull(applicationContext, "applicationContext");
  }

  /**
   * 获取gRPC服务拦截器列表
   *
   * @return gRPC服务拦截器列表
   */
  public ImmutableList<ServerInterceptor> getServerInterceptors() {
    if (this.sortedServerInterceptors == null) {
      this.sortedServerInterceptors = ImmutableList.copyOf(this.initServerInterceptors());
    }
    return this.sortedServerInterceptors;
  }

  /**
   * 初始化gRPC服务拦截器列表
   *
   * @return gRPC服务拦截器列表
   */
  protected List<ServerInterceptor> initServerInterceptors() {

    final List<ServerInterceptor> interceptors = new ArrayList<>();

    // 获取全局服务拦截器配置列表
    Collection<GlobalServerInterceptorConfigurer> configurers =
        this.applicationContext.getBeansOfType(GlobalServerInterceptorConfigurer.class).values();

    // 遍历拦截器列表，并进行相关配置设定
    for (final GlobalServerInterceptorConfigurer configurer : configurers) {
      configurer.configureServerInterceptors(interceptors);
    }
    // 排序拦截器
    sortInterceptors(interceptors);

    return interceptors;
  }

  /**
   * 排序给定的gRPC服务拦截器列表
   *
   * @param interceptors gRPC服务拦截器列表
   */
  public void sortInterceptors(final List<? extends ServerInterceptor> interceptors) {
    interceptors.sort(AnnotationAwareOrderComparator.INSTANCE);
  }
}
