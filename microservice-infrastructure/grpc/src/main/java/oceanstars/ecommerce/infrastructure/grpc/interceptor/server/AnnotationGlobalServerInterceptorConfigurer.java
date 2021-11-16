package oceanstars.ecommerce.infrastructure.grpc.interceptor.server;

import static com.google.common.collect.Maps.transformValues;
import static java.util.Objects.requireNonNull;

import io.grpc.ServerInterceptor;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.ApplicationContext;

/**
 * 全局服务拦截器配置实现类
 *
 * @author Clover
 * @version 1.0.0
 * @since 2021/11/9 1:35 下午
 */
public class AnnotationGlobalServerInterceptorConfigurer implements GlobalServerInterceptorConfigurer {

  /**
   * Spring应用程序上下文环境
   */
  private final ApplicationContext applicationContext;

  /**
   * 日志管理器
   */
  private final Logger logger = LogManager.getLogger(AnnotationGlobalServerInterceptorConfigurer.class.getName());

  /**
   * 构造函数
   *
   * @param applicationContext Spring应用程序上下文环境
   */
  public AnnotationGlobalServerInterceptorConfigurer(final ApplicationContext applicationContext) {
    this.applicationContext = requireNonNull(applicationContext, "applicationContext");
  }

  /**
   * 获取gRPC服务拦截器对象实例映射
   *
   * @return gRPC服务拦截器对象实例映射
   */
  protected Map<String, ServerInterceptor> getServerInterceptorBeans() {
    return transformValues(this.applicationContext.getBeansWithAnnotation(GrpcGlobalServerInterceptor.class), ServerInterceptor.class::cast);
  }

  @Override
  public void configureServerInterceptors(List<ServerInterceptor> interceptors) {
    for (final Entry<String, ServerInterceptor> entry : getServerInterceptorBeans().entrySet()) {
      final ServerInterceptor interceptor = entry.getValue();
      interceptors.add(interceptor);
      logger.debug("已经注册GlobalServerInterceptor: {} ({})", entry.getKey(), interceptor);
    }
  }
}
