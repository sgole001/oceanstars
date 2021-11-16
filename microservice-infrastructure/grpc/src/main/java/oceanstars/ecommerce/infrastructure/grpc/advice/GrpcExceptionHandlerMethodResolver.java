package oceanstars.ecommerce.infrastructure.grpc.advice;

import static java.util.Objects.requireNonNull;

import java.lang.reflect.Method;
import java.text.MessageFormat;
import java.util.AbstractMap.SimpleImmutableEntry;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.ExceptionDepthComparator;
import org.springframework.util.Assert;

/**
 * 收集整理被注解{@link GrpcAdvice @GrpcAdvice}标记的类和{@link GrpcExceptionHandler @GrpcExceptionHandler}标记的方法给出的异常类型，并提供相关联的处理方式
 *
 * @author Clover
 * @version 1.0.0
 * @since 2021/11/10 11:15 上午
 */
public class GrpcExceptionHandlerMethodResolver implements InitializingBean {

  /**
   * 异常类型映射相关方法
   */
  private final Map<Class<? extends Throwable>, Method> mappedMethods = new HashMap<>(16);

  /**
   * 异常注解相关类和方法发现实例
   */
  private final GrpcAdviceDiscoverer grpcAdviceDiscoverer;

  /**
   * 被注解指定的异常集合
   */
  private Class<? extends Throwable>[] annotatedExceptions;

  /**
   * 构造函数
   *
   * @param grpcAdviceDiscoverer 异常注解相关类和方法发现实例
   */
  public GrpcExceptionHandlerMethodResolver(final GrpcAdviceDiscoverer grpcAdviceDiscoverer) {
    this.grpcAdviceDiscoverer = requireNonNull(grpcAdviceDiscoverer, "grpcAdviceDiscoverer");
  }

  @Override
  public void afterPropertiesSet() {
    grpcAdviceDiscoverer.getAnnotatedMethods().forEach(this::extractAndMapExceptionToMethod);
  }

  /**
   * 异常类型映射对应的处理方法
   *
   * @param method 被异常注解的方法
   */
  private void extractAndMapExceptionToMethod(Method method) {

    // 根据指定方法获取GrpcExceptionHandler注解实例
    GrpcExceptionHandler annotation = method.getDeclaredAnnotation(GrpcExceptionHandler.class);
    Assert.notNull(annotation, "没有找到@GrpcExceptionHandler注解。");
    // 获取注解配置的异常集合
    this.annotatedExceptions = annotation.value();

    // 检查异常处理映射前提是否满足
    this.checkPremise4ExceptionToMap(method);
    // 从方法参数类型集合中提取异常类型集合
    Set<Class<? extends Throwable>> exceptionsToMap = this.extractExceptions(method.getParameterTypes());
    // 遍历异常类型集合，将异常类型映射对应的处理方法
    exceptionsToMap.forEach(exceptionType -> this.addExceptionMapping(exceptionType, method));
  }

  /**
   * 检查异常处理映射前提是否满足
   *
   * @param method 被异常注解的方法
   */
  private void checkPremise4ExceptionToMap(Method method) {
    // 没有任何异常需要处理的场合，异常抛出
    if (method.getParameterTypes().length == 0 && this.annotatedExceptions.length == 0) {
      throw new IllegalStateException(MessageFormat.format("被注解@GrpcExceptionHandler的方法{0}没有映射异常类型!", method.getName()));
    }
  }

  /**
   * 从方法参数类型集合中提取异常类型集合
   *
   * @param methodParamTypes 方法参数类型集合
   * @return 异常类型集合
   */
  private Set<Class<? extends Throwable>> extractExceptions(Class<?>[] methodParamTypes) {

    // 异常处理集合
    Set<Class<? extends Throwable>> exceptionsToBeMapped = new HashSet<>();
    // 遍历注解配置的异常集合
    for (Class<? extends Throwable> annotatedException : this.annotatedExceptions) {

      // 校验方法参数类型是否匹配注解声明的异常类型
      if (methodParamTypes.length > 0) {
        this.validateAppropriateParentException(annotatedException, methodParamTypes);
      }

      // 添加处理异常
      exceptionsToBeMapped.add(annotatedException);
    }

    // 针对注解@GrpcExceptionHandler没有配置异常类型的情况，将方法参数中异常类型加入异常处理集合
    this.addMappingInCaseAnnotationIsEmpty(methodParamTypes, exceptionsToBeMapped);

    return exceptionsToBeMapped;
  }

  /**
   * 校验方法参数类型是否匹配注解声明的异常类型
   *
   * @param annotatedException 注解配置的异常
   * @param methodParamTypes   方法参数类型集合
   */
  private void validateAppropriateParentException(Class<? extends Throwable> annotatedException, Class<?>[] methodParamTypes) {

    // 判定参数类型是否全都不是指定注解配置异常类型及其超类
    boolean paramTypeIsNotAppropriate = Arrays.stream(methodParamTypes).noneMatch(param -> param.isAssignableFrom(annotatedException));
    if (paramTypeIsNotAppropriate) {
      throw new IllegalStateException(
          MessageFormat.format("参数类型{0}中没有和注解@GrpcExceptionHandler中声明的异常类型{1}相同或者超类。", Arrays.toString(methodParamTypes), annotatedException));
    }
  }

  /**
   * 将方法参数中异常类型加入异常处理集合（注解@GrpcExceptionHandler没有配置异常类型）
   *
   * @param methodParamTypes     方法参数类型集合
   * @param exceptionsToBeMapped 异常处理集合
   */
  private void addMappingInCaseAnnotationIsEmpty(Class<?>[] methodParamTypes, Set<Class<? extends Throwable>> exceptionsToBeMapped) {

    // 安全类型转换函数式接口
    Function<Class<?>, Class<? extends Throwable>> convertSafely = Class.class::cast;

    // 针对注解@GrpcExceptionHandler没有配置异常类型的情况，将方法参数中异常类型加入异常处理集合
    Arrays.stream(methodParamTypes)
        .filter(param -> exceptionsToBeMapped.isEmpty())
        .filter(Throwable.class::isAssignableFrom)
        .map(convertSafely)
        .forEach(exceptionsToBeMapped::add);
  }

  /**
   * 将异常类型映射对应的处理方法
   *
   * @param exceptionType 异常类型
   * @param method        异常处理映射方法
   */
  private void addExceptionMapping(Class<? extends Throwable> exceptionType, Method method) {

    // 异常类型作为映射集合Key，映射方法对象
    Method oldMethod = this.mappedMethods.put(exceptionType, method);
    // 如果对应Key已经存在，并且和预加入方法对象不一致，异常抛出
    if (oldMethod != null && !oldMethod.equals(method)) {
      throw new IllegalStateException(MessageFormat.format("为异常类型{0}映射的处理方法[{1},{2}]是模棱两可的。", exceptionType, oldMethod, method));
    }
  }

  /**
   * 根据指定的异常类型，映射对应异常处理方法至其所在类或者其子类的实例
   *
   * @param exceptionType 异常类型
   * @param <E>           泛型
   * @return 对应异常处理方法类或者其子类的实例映射
   */
  public <E extends Throwable> Map.Entry<Object, Method> resolveMethodWithInstance(Class<E> exceptionType) {

    // 根据指定的异常类型提取对应的处理方法
    Method value = extractExtendedThrowable(exceptionType);

    if (value == null) {
      return new SimpleImmutableEntry<>(null, null);
    }

    // 根据指定方法获取对应的类类型
    Class<?> methodClass = value.getDeclaringClass();

    // 根据指定的异常类型，获取一个对应异常处理方法类或者其子类的实例
    Object key = this.grpcAdviceDiscoverer.getAnnotatedBeans()
        .values()
        .stream()
        .filter(obj -> methodClass.isAssignableFrom(obj.getClass()))
        .findFirst()
        .orElse(null);

    return new SimpleImmutableEntry<>(key, value);
  }

  /**
   * 判定指定异常类型是否存在对应的处理方法
   *
   * @param exceptionType 异常类型
   * @param <E>           泛型
   * @return 判定结果
   */
  public <E extends Throwable> boolean isMethodMappedForException(Class<E> exceptionType) {
    return extractExtendedThrowable(exceptionType) != null;
  }

  /**
   * 根据指定的异常类型提取对应的处理方法
   *
   * @param exceptionType 异常类型
   * @param <E>           泛型
   * @return 异常类型对应的处理方法
   */
  private <E extends Throwable> Method extractExtendedThrowable(Class<E> exceptionType) {

    return this.mappedMethods.keySet()
        .stream()
        // 过滤指定异常类型的超类或者相同类
        .filter(ex -> ex.isAssignableFrom(exceptionType))
        // 过滤继承深度最小的异常类型
        .min(new ExceptionDepthComparator(exceptionType))
        // 获取对应方法
        .map(mappedMethods::get)
        .orElse(null);
  }
}
