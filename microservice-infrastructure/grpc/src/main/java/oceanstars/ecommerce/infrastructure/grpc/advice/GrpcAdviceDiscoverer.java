package oceanstars.ecommerce.infrastructure.grpc.advice;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.MethodIntrospector;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.lang.NonNull;
import org.springframework.util.Assert;
import org.springframework.util.ReflectionUtils.MethodFilter;

/**
 * 查找注解为{@link GrpcAdvice @GrpcAdvice}以及二级注解为{@link GrpcExceptionHandler @GrpcExceptionHandler}的Bean
 *
 * @author Clover
 * @version 1.0.0
 * @since 2021/11/10 12:52 上午
 */
public class GrpcAdviceDiscoverer implements InitializingBean, ApplicationContextAware {

  /**
   * 方法过滤条件：是否存在注解{@link GrpcExceptionHandler @GrpcExceptionHandler}
   */
  public static final MethodFilter EXCEPTION_HANDLER_METHODS = method -> AnnotatedElementUtils.hasAnnotation(method, GrpcExceptionHandler.class);

  /**
   * Spring应用程序上下文环境
   */
  private ApplicationContext applicationContext;

  /**
   * 被注解的Bean集合映射
   */
  private Map<String, Object> annotatedBeans;

  /**
   * 被注解的方法集合
   */
  private Set<Method> annotatedMethods;

  /**
   * 日志管理器
   */
  private final Logger logger = LogManager.getLogger(GrpcAdviceDiscoverer.class.getName());

  @Override
  public void setApplicationContext(@NonNull ApplicationContext applicationContext) throws BeansException {
    this.applicationContext = applicationContext;
  }

  @Override
  public void afterPropertiesSet() throws Exception {

    // 获取被GrpcAdvice注解的Bean集合映射
    this.annotatedBeans = applicationContext.getBeansWithAnnotation(GrpcAdvice.class);

    // 调试日志
    annotatedBeans.forEach((key, value) -> logger.debug("发现gRPC Advice: {}, class: {}", key, value.getClass().getName()));

    this.annotatedMethods = this.findAnnotatedMethods();
  }

  /**
   * 查找标记注解{@link GrpcExceptionHandler @GrpcExceptionHandler}的方法集合
   *
   * @return 标记注解{@link GrpcExceptionHandler @GrpcExceptionHandler}的方法集合
   */
  private Set<Method> findAnnotatedMethods() {
    return this.annotatedBeans.values().stream()
        .map(Object::getClass)
        .map(this::extractAnnotatedMethods)
        .flatMap(Collection::stream)
        .collect(Collectors.toSet());
  }

  /**
   * 提取标记注解{@link GrpcExceptionHandler @GrpcExceptionHandler}的方法
   *
   * @param clazz 提取方法所在的类
   * @return 标记注解{@link GrpcExceptionHandler @GrpcExceptionHandler}的方法集合
   */
  private Set<Method> extractAnnotatedMethods(final Class<?> clazz) {
    return MethodIntrospector.selectMethods(clazz, EXCEPTION_HANDLER_METHODS);
  }

  /**
   * 获取被注解的Bean集合映射
   *
   * @return 被注解的Bean集合映射
   */
  public Map<String, Object> getAnnotatedBeans() {
    Assert.state(annotatedBeans != null, "注解@GrpcAdvice扫描失败.");
    return annotatedBeans;
  }

  /**
   * 获取被注解的方法集合
   *
   * @return 被注解的方法集合
   */
  public Set<Method> getAnnotatedMethods() {
    Assert.state(annotatedMethods != null, "注解@GrpcExceptionHandler扫描失败.");
    return annotatedMethods;
  }
}
