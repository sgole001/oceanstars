package oceanstars.ecommerce.infrastructure.grpc.interceptor.client;

import static com.google.common.collect.Maps.transformValues;
import static java.util.Objects.requireNonNull;

import io.grpc.ClientInterceptor;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.ApplicationContext;

/**
 * 全局客户端拦截器配置实现类
 *
 * @author Clover
 * @version 1.0.0
 * @since 2021/11/12 1:33 下午
 */
public class AnnotationGlobalClientInterceptorConfigurer implements GlobalClientInterceptorConfigurer {

  /**
   * Spring应用程序上下文环境
   */
  private final ApplicationContext applicationContext;

  /**
   * 日志管理器
   */
  private final Logger logger = LogManager.getLogger(AnnotationGlobalClientInterceptorConfigurer.class.getName());

  /**
   * 构造函数
   *
   * @param applicationContext Spring应用程序上下文环境
   */
  public AnnotationGlobalClientInterceptorConfigurer(final ApplicationContext applicationContext) {
    this.applicationContext = requireNonNull(applicationContext, "applicationContext");
  }


  @Override
  public void configureClientInterceptors(List<ClientInterceptor> interceptors) {

    // 获取并遍历被注解{@link GrpcGlobalClientInterceptor}标记的客户端拦截Bean集合
    for (final Entry<String, ClientInterceptor> entry : this.getClientInterceptorBeans().entrySet()) {

      // 获取拦截器对象实例
      final ClientInterceptor interceptor = entry.getValue();
      // 添加拦截器
      interceptors.add(interceptor);

      logger.debug("注册GlobalClientInterceptor: {} ({})", entry.getKey(), interceptor);
    }
  }

  /**
   * 获取注解{@link GrpcGlobalClientInterceptor}标记的客户端拦截Bean对象映射
   *
   * @return 注解{@link GrpcGlobalClientInterceptor}标记的客户端拦截Bean对象映射
   */
  protected Map<String, ClientInterceptor> getClientInterceptorBeans() {
    return transformValues(this.applicationContext.getBeansWithAnnotation(GrpcGlobalClientInterceptor.class), ClientInterceptor.class::cast);
  }
}
