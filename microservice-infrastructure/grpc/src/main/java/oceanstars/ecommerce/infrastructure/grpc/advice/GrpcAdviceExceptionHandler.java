package oceanstars.ecommerce.infrastructure.grpc.advice;

import static java.util.Objects.requireNonNull;

import io.grpc.Metadata;
import io.grpc.ServerCall;
import io.grpc.Status;
import io.grpc.StatusException;
import io.grpc.StatusRuntimeException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.lang.reflect.Type;
import java.text.MessageFormat;
import java.util.Map.Entry;
import oceanstars.ecommerce.infrastructure.grpc.error.GrpcExceptionResponseHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * gRPC异常处理类
 *
 * @author Clover
 * @version 1.0.0
 * @since 2021/11/11 1:20 上午
 */
public class GrpcAdviceExceptionHandler implements GrpcExceptionResponseHandler {

  /**
   * gRPC异常处理方法收集整理接口
   */
  private final GrpcExceptionHandlerMethodResolver grpcExceptionHandlerMethodResolver;

  /**
   * 日志管理器
   */
  private final Logger logger = LogManager.getLogger(GrpcAdviceExceptionHandler.class.getName());

  /**
   * 构造函数
   *
   * @param grpcExceptionHandlerMethodResolver gRPC异常处理方法收集整理接口
   */
  public GrpcAdviceExceptionHandler(final GrpcExceptionHandlerMethodResolver grpcExceptionHandlerMethodResolver) {
    this.grpcExceptionHandlerMethodResolver = requireNonNull(grpcExceptionHandlerMethodResolver, "grpcExceptionHandlerMethodResolver");
  }

  @Override
  public void handleError(ServerCall<?, ?> serverCall, Throwable error) {

    try {

      // 执行异常处理，并获取处理结果
      final Object mappedReturnType = this.handleThrownException(error);
      // 解析异常处理返回状态
      final Status status = this.resolveStatus(mappedReturnType);
      // 解析Metadata信息
      final Metadata metadata = this.resolveMetadata(mappedReturnType);

      // 关闭gRPC请求
      serverCall.close(status, metadata);

    } catch (final Throwable errorWhileResolving) {

      // 避免被捕获的异常不被抑制，方便追踪调试
      if (errorWhileResolving != error) {
        errorWhileResolving.addSuppressed(error);
      }
      // 处理因调用处理异常的方法时发生的异常
      handleThrownExceptionByImplementation(serverCall, errorWhileResolving);
    }
  }

  /**
   * 异常处理
   *
   * @param exception 异常实例对象
   * @return 异常处理返回结果对象
   * @throws Throwable 处理异常
   */
  protected Object handleThrownException(final Throwable exception) throws Throwable {

    // 调试用
    logger.debug("gRPC调用执行过程中被捕获的异常", exception);

    /// 获取异常类型
    final Class<? extends Throwable> exceptionClass = exception.getClass();

    // 判定指定异常类型是否存在对应的处理方法
    if (!this.grpcExceptionHandlerMethodResolver.isMethodMappedForException(exceptionClass)) {
      throw exception;
    }

    // 根据指定的异常类型，映射对应异常处理方法至其所在类或者其子类的实例
    final Entry<Object, Method> methodWithInstance = this.grpcExceptionHandlerMethodResolver.resolveMethodWithInstance(exceptionClass);
    // 获取对应异常处理方法
    final Method mappedMethod = methodWithInstance.getValue();
    // 获取异常处理方法所在类或者其子类的实例
    final Object instanceOfMappedMethod = methodWithInstance.getKey();
    // 构建异常处理方法参数实例对象集合
    final Object[] instancedParams = this.determineInstancedParameters(mappedMethod, exception);

    // 调用异常对应的处理方法
    return this.invokeMappedMethodSafely(mappedMethod, instanceOfMappedMethod, instancedParams);
  }

  /**
   * 解析异常处理返回状态
   *
   * @param mappedReturnType 异常处理返回结果
   * @return 异常处理返回状态
   */
  protected Status resolveStatus(final Object mappedReturnType) {

    // 异常处理返回结果类型为Status，转换异常处理返回结果为Status类型并返回
    if (mappedReturnType instanceof Status) {
      return (Status) mappedReturnType;
    }
    // 异常处理返回结果类型为Throwable, 从结果中提取Status信息并返回
    else if (mappedReturnType instanceof Throwable) {
      return Status.fromThrowable((Throwable) mappedReturnType);
    }

    // 返回结果类型错误，异常抛出
    throw new IllegalStateException(
        MessageFormat.format("在@GrpcAdvice内的异常处理返回结果是[{0}],其类型必须是[Status, StatusException, StatusRuntimeException, Throwable]", mappedReturnType));
  }

  /**
   * 解析Metadata信息
   *
   * @param mappedReturnType 异常处理返回结果
   * @return Metadata信息
   */
  protected Metadata resolveMetadata(final Object mappedReturnType) {

    Metadata result = null;

    // 解析状态异常结果，返回异常外挂的Metadata信息
    if (mappedReturnType instanceof final StatusException statusException) {
      result = statusException.getTrailers();
    } else if (mappedReturnType instanceof final StatusRuntimeException statusRuntimeException) {
      result = statusRuntimeException.getTrailers();
    }
    return (result == null) ? new Metadata() : result;
  }

  /**
   * 处理因调用处理异常的方法时发生的异常
   *
   * @param serverCall 服务请求
   * @param throwable  调用处理异常的方法时发生的异常
   */
  protected void handleThrownExceptionByImplementation(final ServerCall<?, ?> serverCall, final Throwable throwable) {
    logger.error("调用带注释的 @GrpcExceptionHandler 方法时抛出的异常: ", throwable);
    // 关闭gRPC服务请求
    serverCall.close(Status.INTERNAL.withCause(throwable).withDescription("处理异常时服务器发生异常。"), new Metadata());
  }

  /**
   * 构建异常处理方法参数实例对象集合
   *
   * @param mappedMethod 处理方法
   * @param exception    对应异常
   * @return 异常处理方法参数实例对象集合
   */
  private Object[] determineInstancedParameters(final Method mappedMethod, final Throwable exception) {

    // 获取方法参数
    final Parameter[] parameters = mappedMethod.getParameters();
    //
    final Object[] instancedParams = new Object[parameters.length];

    // 遍历所有参数
    for (int i = 0; i < parameters.length; i++) {

      // 参数Class类型转换
      final Class<?> parameterClass = this.convertToClass(parameters[i]);

      // 识别参数类型为异常类型的超类
      if (parameterClass.isAssignableFrom(exception.getClass())) {
        instancedParams[i] = exception;
        break;
      }
    }
    return instancedParams;
  }

  /**
   * 参数Class类型转换
   *
   * @param parameter 方法参数
   * @return Class类型转后参数类型
   */
  private Class<?> convertToClass(final Parameter parameter) {

    // 获取参数类型
    final Type paramType = parameter.getParameterizedType();
    // 参数类型为类，则返回此类型
    if (paramType instanceof Class) {
      return (Class<?>) paramType;
    }
    throw new IllegalStateException("方法参数类型必须是Class, 但此参数是: " + paramType);
  }

  /**
   * 反射调用异常所对应的相关处理方法
   *
   * @param mappedMethod           处理方法
   * @param instanceOfMappedMethod 处理方法所在类的实例对象
   * @param instancedParams        处理方法对应异常参数实例对象数组
   * @return 方法调用返回对象
   * @throws Throwable 调用异常
   */
  private Object invokeMappedMethodSafely(final Method mappedMethod, final Object instanceOfMappedMethod, final Object[] instancedParams)
      throws Throwable {
    try {
      return mappedMethod.invoke(instanceOfMappedMethod, instancedParams);
    } catch (InvocationTargetException | IllegalAccessException e) {
      throw e.getCause();
    }
  }
}
