package oceanstars.ecommerce.infrastructure.grpc.service.provider;

import com.google.common.collect.Lists;
import io.grpc.BindableService;
import io.grpc.ServerInterceptor;
import io.grpc.ServerInterceptors;
import io.grpc.ServerServiceDefinition;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import oceanstars.ecommerce.infrastructure.grpc.interceptor.server.GlobalServerInterceptorRegistry;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.lang.NonNull;

/**
 * 遍历搜索带有GrpcService注解的Bean
 *
 * @author Clover
 * @version 1.0.0
 * @since 2021/11/8 11:32 下午
 */
public class AnnotationGrpcServiceDiscoverer implements ApplicationContextAware, GrpcServiceDiscoverer {

  /**
   * Spring应用程序上下文环境
   */
  private ApplicationContext applicationContext;

  /**
   * 日志管理器
   */
  private final Logger logger = LogManager.getLogger(AnnotationGrpcServiceDiscoverer.class.getName());

  @Override
  public void setApplicationContext(@NonNull final ApplicationContext applicationContext) throws BeansException {
    this.applicationContext = applicationContext;
  }

  @Override
  public Collection<GrpcServiceDefinition> findGrpcServices() {

    // 获取带有GrpcService注解的Bean名列表
    Collection<String> beanNames = Arrays.asList(this.applicationContext.getBeanNamesForAnnotation(GrpcService.class));
    // 初始化gRPC服务定义列表
    List<GrpcServiceDefinition> definitions = Lists.newArrayListWithCapacity(beanNames.size());
    // 获取全局服务拦截器注册Bean实例
    GlobalServerInterceptorRegistry globalServerInterceptorRegistry = applicationContext.getBean(GlobalServerInterceptorRegistry.class);

    for (String beanName : beanNames) {

      // 根据Bean名获取实现BindableService接口的gRPC服务类实例
      BindableService bindableService = this.applicationContext.getBean(beanName, BindableService.class);
      // 获取gRPC服务定义信息实例
      ServerServiceDefinition serviceDefinition = bindableService.bindService();
      // 根据Bean名获取GrpcService注解实例
      GrpcService grpcServiceAnnotation = applicationContext.findAnnotationOnBean(beanName, GrpcService.class);
      // 绑定服务拦截器
      serviceDefinition = bindInterceptors(serviceDefinition, grpcServiceAnnotation, globalServerInterceptorRegistry);
      // 添加gRPC服务定义数据
      definitions.add(new GrpcServiceDefinition(beanName, bindableService.getClass(), serviceDefinition));

      // 调试日志：输出gRPC服务信息
      logger.log(Level.DEBUG, "Found gRPC service: {}, bean: {}, class: {}", serviceDefinition.getServiceDescriptor().getName(), beanName,
          bindableService.getClass().getName());
    }
    return definitions;
  }

  /**
   * 根据指定的gRPC服务，绑定相应配置的服务拦截器
   *
   * @param serviceDefinition               gRPC服务定义
   * @param grpcServiceAnnotation           gRPC服务配置注解
   * @param globalServerInterceptorRegistry 全局gRPC服务拦截器注册器
   * @return 绑定拦截器后的gRPC服务
   */
  private ServerServiceDefinition bindInterceptors(final ServerServiceDefinition serviceDefinition, final GrpcService grpcServiceAnnotation,
      final GlobalServerInterceptorRegistry globalServerInterceptorRegistry) {

    if (null == grpcServiceAnnotation) {
      return serviceDefinition;
    }

    // 初始化服务拦截器列表
    final List<ServerInterceptor> interceptors = Lists.newArrayList();

    // 添加全局服务拦截器
    interceptors.addAll(globalServerInterceptorRegistry.getServerInterceptors());

    // 根据注解指定拦截器类添加服务拦截器
    for (final Class<? extends ServerInterceptor> interceptorClass : grpcServiceAnnotation.interceptors()) {

      // 获取服务拦截器
      final ServerInterceptor serverInterceptor;

      // 指定拦截器已经在加载至Spring容器
      if (this.applicationContext.getBeanNamesForType(interceptorClass).length > 0) {
        serverInterceptor = this.applicationContext.getBean(interceptorClass);
      } else {
        // 创建服务拦截器实例
        try {
          serverInterceptor = interceptorClass.getConstructor().newInstance();
        } catch (final Exception e) {
          throw new BeanCreationException("创建服务拦截器失败！", e);
        }
      }
      // 添加服务拦截器
      interceptors.add(serverInterceptor);
    }

    // 根据注解指定拦截器类名添加服务拦截器
    for (final String interceptorName : grpcServiceAnnotation.interceptorNames()) {

      // 获取服务拦截器
      final ServerInterceptor serverInterceptor = this.applicationContext.getBean(interceptorName, ServerInterceptor.class);

      if (!interceptors.contains(serverInterceptor)) {
        interceptors.add(this.applicationContext.getBean(interceptorName, ServerInterceptor.class));
      }
    }

    // 启动服务拦截器排序
    if (grpcServiceAnnotation.sortInterceptors()) {
      globalServerInterceptorRegistry.sortInterceptors(interceptors);
    }

    return ServerInterceptors.interceptForward(serviceDefinition, interceptors);
  }
}
