package oceanstars.ecommerce.common.domain;

import java.util.Map;
import org.springframework.beans.factory.ObjectProvider;

/**
 * 事件网关：事件发布
 *
 * @author Clover
 * @version 1.0.0
 * @since 2023/12/10 14:09
 */
@SuppressWarnings("unchecked")
public class EventGateway implements EventBus {

  /**
   * 事件发布器映射表
   */
  private final Map<Class<? extends DomainEvent<?, ?>>, ? extends IEventPublisher<?, ?>> publishProvider;

  public EventGateway(ObjectProvider<Map<Class<? extends DomainEvent<?, ?>>, ? extends IEventPublisher<?, ?>>> publishProvider) {
    this.publishProvider = publishProvider.getIfAvailable();
  }

  /**
   * 发布领域事件
   *
   * @param event 领域事件
   * @param <R>   处理结果类型
   * @param <E>   领域事件类型
   * @return 处理结果
   */
  @Override
  public <R, E extends DomainEvent<?, ?>> R publish(E event) {
    IEventPublisher<R, E> eventPublisher = (IEventPublisher<R, E>) this.publishProvider.get(event.getClass());
    return eventPublisher.publish(event);
  }
}
