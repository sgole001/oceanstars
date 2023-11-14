package oceanstars.ecommerce.message.domain.aggregate.event.entity.strategy;

import oceanstars.ecommerce.message.constant.enums.MessageEnum.MessageBus;
import oceanstars.ecommerce.message.domain.aggregate.event.entity.strategy.impl.KafkaEventStrategy;

/**
 * 事件策略上下文
 *
 * @author Clover
 * @version 1.0.0
 * @since 2021/12/14 3:01 下午
 */
public class EventContext {

  /**
   * 事件相关策略接口
   */
  private final IEventStrategy eventStrategy;

  /**
   * 构造函数：初始化事件策略
   *
   * @param messageBus 消息总线
   */
  public EventContext(final MessageBus messageBus) {
    this.eventStrategy = this.createStrategy(messageBus);
  }

  /**
   * 根据消息总线创建事件策略
   *
   * @param messageBus 消息总线
   * @return 事件策略
   */
  protected IEventStrategy createStrategy(final MessageBus messageBus) {

    // kafka作为消息总线
    if (MessageBus.KAFKA.equals(messageBus)) {
      return new KafkaEventStrategy();
    }

    return null;
  }

  /**
   * 获取事件策略
   *
   * @return 事件策略
   */
  public IEventStrategy getEventStrategy() {
    return eventStrategy;
  }
}
