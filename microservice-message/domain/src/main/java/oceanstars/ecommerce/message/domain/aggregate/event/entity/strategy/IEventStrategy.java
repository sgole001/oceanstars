package oceanstars.ecommerce.message.domain.aggregate.event.entity.strategy;

import oceanstars.ecommerce.message.domain.aggregate.event.entity.EventMetaEntity;
import oceanstars.ecommerce.message.domain.aggregate.event.entity.valueobject.EventStoreValueObject;

/**
 * 事件相关策略接口
 *
 * @author Clover
 * @version 1.0.0
 * @since 2021/12/14 3:00 下午
 */
public interface IEventStrategy {

  /**
   * 配置事件元数据
   *
   * @param meta   事件元数据
   * @param config 事件元数据配置序列化信息
   */
  void config(final EventMetaEntity meta, final byte[] config);

  /**
   * 发布事件
   *
   * @param meta       事件元数据
   * @param eventStore 事件存储信息
   */
  void publish(final EventMetaEntity meta, final EventStoreValueObject eventStore);
}
