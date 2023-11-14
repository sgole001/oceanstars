package oceanstars.ecommerce.message.domain.aggregate.event.entity.strategy.impl;

import oceanstars.ecommerce.common.spring.ApplicationContextProvider;
import oceanstars.ecommerce.message.domain.aggregate.event.entity.EventMetaEntity;
import oceanstars.ecommerce.message.domain.aggregate.event.entity.strategy.IEventStrategy;
import oceanstars.ecommerce.message.domain.aggregate.event.entity.valueobject.EventStoreValueObject;
import oceanstars.ecommerce.message.domain.aggregate.event.repository.IEventRepository;
import org.springframework.messaging.Message;

/**
 * 事件处理策略基础类
 *
 * @author Clover
 * @version 1.0.0
 * @since 2021/12/13 3:39 下午
 */
public abstract class AbstractEventStrategy implements IEventStrategy {

  /**
   * 事件聚合领域服务接口
   */
  private final IEventRepository eventRepository;

  /**
   * 构造函数：初始化事件策略成员变量
   */
  protected AbstractEventStrategy() {
    this.eventRepository = (IEventRepository) ApplicationContextProvider.getBean("eventRepository");
  }

  @Override
  public void publish(final EventMetaEntity meta, final EventStoreValueObject eventStore) {

    // 事件存储
    this.eventRepository.eventStore(eventStore);

    // 事件消息构建
    final Message<?> message = this.buildMessage(meta, eventStore);

    // 事件消息发送
    this.sendMessage(message);
  }

  /**
   * 获取事件聚合仓储接口
   *
   * @return 事件聚合仓储接口
   */
  public IEventRepository getEventRepository() {
    return eventRepository;
  }

  /**
   * 事件消息构建处理
   *
   * @param meta       事件元数据
   * @param eventStore 事件存储信息
   * @return 构建后发送用事件消息信息
   */
  protected abstract Message<?> buildMessage(final EventMetaEntity meta, final EventStoreValueObject eventStore);

  /**
   * 事件消息发送处理
   *
   * @param message 发送用事件消息信息
   */
  protected abstract void sendMessage(final Message<?> message);
}
