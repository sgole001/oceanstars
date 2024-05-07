package oceanstars.ecommerce.common.domain.event;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import oceanstars.ecommerce.common.spring.ApplicationContextProvider;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.DependsOn;
import org.springframework.core.GenericTypeResolver;

/**
 * 事件配置类
 *
 * @author Clover
 * @version 1.0.0
 * @since 2023/12/10 14:34
 */
@AutoConfiguration
@DependsOn(value = {"applicationContextProvider"})
@SuppressWarnings("unchecked")
public class EventConfiguration {

  /**
   * 事件发布器映射表Bean
   *
   * @param <R> 处理结果类型
   * @param <E> 领域事件类型
   * @param <P> 事件发布器类型
   * @return 事件发布器映射表
   */
  @Bean(value = "publishProvider")
  public <R, E extends DomainEvent<?, ?>, P extends IEventPublisher<R, E>> Map<Class<E>, P> publishProvider() {

    // 获取所有实现接口IEventPublisher的Bean的名字
    final String[] eventPublisherNames = ApplicationContextProvider.getApplicationContext().getBeanNamesForType(IEventPublisher.class);

    if (eventPublisherNames.length == 0) {
      return Collections.emptyMap();
    }

    // 初始化创建事件发布处理映射
    final Map<Class<E>, P> eventPublisherMap = HashMap.newHashMap(eventPublisherNames.length);

    Arrays.stream(eventPublisherNames).forEach(name -> {

      // 获取事件发布类型
      final Class<IEventPublisher<R, E>> publisherClass = (Class<IEventPublisher<R, E>>) ApplicationContextProvider.getApplicationContext()
          .getType(name);
      if (null == publisherClass) {
        return;
      }
      // 获取事件发布类型中泛型列表
      final Class<?>[] generics = GenericTypeResolver.resolveTypeArguments(publisherClass, IEventPublisher.class);
      // 获取事件发布对应的事件类型
      if (null == generics || generics.length < 2) {
        return;
      }
      final Class<E> eventType = (Class<E>) generics[1];
      // 获取事件发布对象
      final P eventPublisher = (P) ApplicationContextProvider.getApplicationContext().getBean(publisherClass);

      eventPublisherMap.put(eventType, eventPublisher);
    });

    return eventPublisherMap;
  }

  /**
   * 事件网关Bean
   *
   * @param publishProvider 事件发布器映射表
   * @return 事件网关
   */
  @Bean(value = "eventGateway")
  public EventBus eventGateway(ObjectProvider<Map<Class<? extends DomainEvent<?, ?>>, ? extends IEventPublisher<?, ?>>> publishProvider) {
    return new EventGateway(publishProvider);
  }
}
