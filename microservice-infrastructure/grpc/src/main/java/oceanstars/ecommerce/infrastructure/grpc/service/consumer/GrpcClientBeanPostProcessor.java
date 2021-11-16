package oceanstars.ecommerce.infrastructure.grpc.service.consumer;

import static java.util.Objects.requireNonNull;

import com.google.common.collect.Lists;
import io.grpc.Channel;
import io.grpc.ClientInterceptor;
import io.grpc.stub.AbstractStub;
import java.lang.reflect.Field;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import oceanstars.ecommerce.infrastructure.grpc.factory.client.channel.GrpcChannelFactory;
import oceanstars.ecommerce.infrastructure.grpc.factory.client.stub.StubFactory;
import org.springframework.beans.BeanInstantiationException;
import org.springframework.beans.BeansException;
import org.springframework.beans.InvalidPropertyException;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.beans.factory.BeanDefinitionStoreException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.lang.NonNull;
import org.springframework.util.ReflectionUtils;

/**
 * 解析注解{@link GrpcClient}，提取gRPC客户端连接通道和Stub信息
 *
 * @author Clover
 * @version 1.0.0
 * @since 2021/11/16 1:33 上午
 */
public class GrpcClientBeanPostProcessor implements BeanPostProcessor {

  /**
   * Spring应用程序上下文环境
   */
  private final ApplicationContext applicationContext;

  /**
   * gRPC客户端通道工厂接口
   */
  private GrpcChannelFactory channelFactory;

  /**
   * gRPC客户端Stub工厂列表
   */
  private List<StubFactory> stubFactories;

  /**
   * gRPC客户端Stub改造器列表
   */
  private List<StubTransformer> stubTransformers;

  /**
   * 构造函数
   *
   * @param applicationContext Spring应用程序上下文环境
   */
  public GrpcClientBeanPostProcessor(final ApplicationContext applicationContext) {
    this.applicationContext = requireNonNull(applicationContext, "applicationContext");
  }

  @Override
  public Object postProcessBeforeInitialization(final Object bean, @NonNull final String beanName) throws BeansException {

    // 获取指定bean的类型
    Class<?> clazz = bean.getClass();

    do {

      // 处理指定类中被注解GrpcClient标记的字段
      this.processFields(clazz, bean);

      //
      this.processMethods(clazz, bean);

      // 获取父类
      clazz = clazz.getSuperclass();
    } while (clazz != null);

    return bean;
  }

  /**
   * 处理指定类中被注解GrpcClient标记的字段
   *
   * @param clazz 指定bean类型
   * @param bean  指定bean实例
   */
  private void processFields(final Class<?> clazz, final Object bean) {

    // 遍历指定类的成员字段
    for (final Field field : clazz.getDeclaredFields()) {

      // 处理被注解GrpcClient标记的字段
      final GrpcClient annotation = AnnotationUtils.findAnnotation(field, GrpcClient.class);
      if (annotation != null) {
        ReflectionUtils.makeAccessible(field);
        // 反射设定字段
        ReflectionUtils.setField(field, bean, this.processInjectionPoint(field, field.getType(), annotation));
      }
    }
  }

  /**
   * 处理指定类中被注解GrpcClient标记的方法
   *
   * @param clazz 指定bean类型
   * @param bean  指定bean实例
   */
  private void processMethods(final Class<?> clazz, final Object bean) {

    // 遍历指定类的方法
    for (final Method method : clazz.getDeclaredMethods()) {

      // 处理被注解GrpcClient标记的方法
      final GrpcClient annotation = AnnotationUtils.findAnnotation(method, GrpcClient.class);
      if (annotation != null) {

        // 获取方法参数
        final Class<?>[] paramTypes = method.getParameterTypes();
        // 方法参数不应该大于1个，否则异常抛出
        if (paramTypes.length != 1) {
          throw new BeanDefinitionStoreException(MessageFormat.format("方法{0}的参数不能超过1个", method));
        }

        ReflectionUtils.makeAccessible(method);
        // 反射执行方法
        ReflectionUtils.invokeMethod(method, bean, this.processInjectionPoint(method, paramTypes[0], annotation));
      }
    }
  }

  /**
   * 处理反射注入（注解GrpcClient标记的类字段或方法），字段或方法参数类型为：Channel或AbstractStub的实现类
   *
   * @param injectionTarget 反射目标
   * @param injectionType   反射目标类型
   * @param annotation      gRPC客户端注解对象
   * @return 反射目标注入值
   */
  protected <T> T processInjectionPoint(final Member injectionTarget, final Class<T> injectionType, final GrpcClient annotation) {

    // 解析注解GrpcClient实例，提取gRPC客户端自定义拦截器实例列表
    final List<ClientInterceptor> interceptors = this.interceptorsFromAnnotation(annotation);
    // 获取gRPC客户端名
    final String name = annotation.value();

    // 创建指定gRPC客户端的连接通道
    final Channel channel;
    try {
      channel = this.getChannelFactory().createChannel(name, interceptors, annotation.sortInterceptors());
      if (channel == null) {
        throw new IllegalStateException(MessageFormat.format("未能给gRPC客户端{0}创建连接通道。", name));
      }
    } catch (final RuntimeException e) {
      throw new IllegalStateException(MessageFormat.format("gRPC客户端{0}创建连接通道失败。", name), e);
    }

    // 获取反射目标的注入值
    final T value = this.valueForMember(name, injectionTarget, injectionType, channel);
    if (value == null) {
      throw new IllegalStateException(MessageFormat.format("{0}处{1}的注入值为空。", injectionTarget, name));
    }

    return value;
  }

  /**
   * 获取gRPC客户端连接通道工厂
   *
   * @return gRPC客户端连接通道工厂
   */
  private GrpcChannelFactory getChannelFactory() {

    if (this.channelFactory == null) {
//      this.applicationContext.getBean(NameResolverRegistration.class);
      // 获取实现GrpcChannelFactory接口的gRPC客户端连接通道工厂对象
      this.channelFactory = this.applicationContext.getBean(GrpcChannelFactory.class);
    }

    return this.channelFactory;
  }

  /**
   * 获取gRPC客户端Stub改造器列表
   *
   * @return gRPC客户端Stub改造器列表
   */
  private List<StubTransformer> getStubTransformers() {

    if (this.stubTransformers == null) {
      // 获取类型为StubTransformer的对象列表
      this.stubTransformers = new ArrayList<>(this.applicationContext.getBeansOfType(StubTransformer.class).values());
    }
    return this.stubTransformers;
  }

  /**
   * 解析指定的GrpcClient注解，提取gRPC客户端拦截器列表
   *
   * @param annotation GrpcClient注解实例
   * @return gRPC客户端拦截器列表
   * @throws BeansException 创建拦截器实例Bean异常
   */
  protected List<ClientInterceptor> interceptorsFromAnnotation(final GrpcClient annotation) throws BeansException {

    // 初始化创建gRPC客户端拦截器列表
    final List<ClientInterceptor> list = Lists.newArrayList();

    // 遍历注解GrpcClient配置的自定义拦截器类型
    for (final Class<? extends ClientInterceptor> interceptorClass : annotation.interceptors()) {

      // 如果指定的拦截器类型已经注入Spring
      if (this.applicationContext.getBeanNamesForType(interceptorClass).length > 0) {
        list.add(this.applicationContext.getBean(interceptorClass));
      }
      // 其他情况，创建指定的拦截器类型实例
      else {
        try {
          list.add(interceptorClass.getConstructor().newInstance());
        } catch (final Exception e) {
          throw new BeanCreationException("创建客户端拦截器实例失败。", e);
        }
      }
    }

    // 遍历注解GrpcClient配置的自定义拦截器名
    for (final String interceptorName : annotation.interceptorNames()) {
      list.add(this.applicationContext.getBean(interceptorName, ClientInterceptor.class));
    }

    return list;
  }

  /**
   * 获取反射目标的注入值
   *
   * @param name            gRPC客户端名
   * @param injectionTarget 反射目标
   * @param injectionType   反射目标类型
   * @param channel         gRPC客户端连接通道
   * @return 反射注入值
   * @throws BeansException 不支持的类型异常
   */
  protected <T> T valueForMember(final String name, final Member injectionTarget, final Class<T> injectionType, final Channel channel)
      throws BeansException {

    // 注入目标的类型为Channel，直接强转后返回
    if (Channel.class.equals(injectionType)) {
      return injectionType.cast(channel);
    }
    // 注入目标的类型为AbstractStub的子类
    else if (AbstractStub.class.isAssignableFrom(injectionType)) {

      // 创建gRPC客户端Stub
      AbstractStub<?> stub = this.createStub(injectionType.asSubclass(AbstractStub.class), channel);

      // 获取并遍历gRPC客户端Stub改造器列表，改造指定gRPC客户端Stub
      for (final StubTransformer stubTransformer : this.getStubTransformers()) {
        stub = stubTransformer.transform(name, stub);
      }

      // 强转为指定的Stub类型返回
      return injectionType.cast(stub);
    }
    // 其他情况，异常抛出
    else {
      if (injectionTarget != null) {
        throw new InvalidPropertyException(injectionTarget.getDeclaringClass(), injectionTarget.getName(),
            MessageFormat.format("类型{0}不被支持。", injectionType.getName()));
      } else {
        throw new BeanInstantiationException(injectionType, "不支持的gRPC客户端Stub或通道类型");
      }
    }
  }

  /**
   * 创建gRPC客户端Stub
   *
   * @param stubClass Stub类型
   * @param channel   gRPC客户端连接通道
   * @return gRPC客户端Stub
   */
  private <T extends AbstractStub<T>> AbstractStub<T> createStub(final Class<T> stubClass, final Channel channel) {

    // 获取指定Stub类型对应的Stub工厂对象
    final StubFactory factory = this.getStubFactories().stream()
        // 根据指定的Stub类型匹配合适的工厂
        .filter(stubFactory -> stubFactory.isApplicable(stubClass))
        // 一个Stub类型只对应最多一个工厂
        .findFirst()
        // 否则异常抛出
        .orElseThrow(() -> new BeanInstantiationException(stubClass, MessageFormat.format("{0}是不被支持的Stub类型。", stubClass.getName())));

    try {
      // 创建gRPC客户端Stub
      return factory.createStub(stubClass, channel);
    } catch (final Exception exception) {
      throw new BeanInstantiationException(stubClass, MessageFormat.format("创建类型{0}的gRPC客户端Stub失败。", stubClass.getName()), exception);
    }
  }

  /**
   * 获取gRPC客户端Stub工厂列表
   *
   * @return gRPC客户端Stub工厂列表
   */
  private List<StubFactory> getStubFactories() {

    if (null == this.stubFactories) {
      // 获取类型为StubFactory的Stub工厂列表
      this.stubFactories = new ArrayList<>(this.applicationContext.getBeansOfType(StubFactory.class).values());
    }

    return this.stubFactories;
  }
}
