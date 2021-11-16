package oceanstars.ecommerce.infrastructure.grpc.interceptor.client;

import static java.util.Objects.requireNonNull;

import com.google.common.collect.ImmutableList;
import io.grpc.ClientInterceptor;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.springframework.context.ApplicationContext;
import org.springframework.core.annotation.AnnotationAwareOrderComparator;

/**
 * 全局客户端拦截器注册类
 *
 * @author Clover
 * @version 1.0.0
 * @since 2021/11/12 1:44 下午
 */
public class GlobalClientInterceptorRegistry {

  /**
   * Spring应用程序上下文环境
   */
  private final ApplicationContext applicationContext;

  /**
   * 排过顺的客户端拦截器列表
   */
  private ImmutableList<ClientInterceptor> sortedClientInterceptors;

  /**
   * 构造函数
   *
   * @param applicationContext Spring应用程序上下文环境
   */
  public GlobalClientInterceptorRegistry(final ApplicationContext applicationContext) {
    this.applicationContext = requireNonNull(applicationContext, "applicationContext");
  }

  /**
   * 获取客户端拦截器列表（已排序）
   *
   * @return 客户端拦截器列表（已排序）
   */
  public ImmutableList<ClientInterceptor> getClientInterceptors() {

    // 获取客户端拦截器列表（已排序）
    if (this.sortedClientInterceptors == null) {
      this.sortedClientInterceptors = ImmutableList.copyOf(initClientInterceptors());
    }

    return this.sortedClientInterceptors;
  }

  /**
   * 排序指定的客户端拦截器列表
   *
   * @param interceptors 客户端拦截器列表
   */
  public void sortInterceptors(final List<? extends ClientInterceptor> interceptors) {
    interceptors.sort(AnnotationAwareOrderComparator.INSTANCE);
  }

  /**
   * 初始化注册gRPC客户端全局拦截器
   *
   * @return gRPC客户端全局拦截器列表
   */
  protected List<ClientInterceptor> initClientInterceptors() {

    // 初始化客户端拦截器列表
    final List<ClientInterceptor> interceptors = new ArrayList<>();

    // 获取客户端全局拦截器配置集合映射
    final Map<String, GlobalClientInterceptorConfigurer> configurerMap = this.applicationContext.getBeansOfType(
        GlobalClientInterceptorConfigurer.class);

    // 遍历拦截器配置集合
    for (final GlobalClientInterceptorConfigurer configurer : configurerMap.values()) {
      // 配置全局客户端拦截器
      configurer.configureClientInterceptors(interceptors);
    }

    // 排序拦截器
    this.sortInterceptors(interceptors);
    return interceptors;
  }
}
