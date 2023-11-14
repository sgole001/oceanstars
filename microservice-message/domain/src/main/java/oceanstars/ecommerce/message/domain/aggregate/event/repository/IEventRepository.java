package oceanstars.ecommerce.message.domain.aggregate.event.repository;

import java.util.List;
import oceanstars.ecommerce.message.constant.enums.MessageEnum.MessageBus;
import oceanstars.ecommerce.message.domain.aggregate.event.entity.EventMetaEntity;
import oceanstars.ecommerce.message.domain.aggregate.event.entity.EventMetaIdentifier;
import oceanstars.ecommerce.message.domain.aggregate.event.entity.valueobject.EventStoreValueObject;

/**
 * 事件聚合仓储接口
 *
 * @author Clover
 * @version 1.0.0
 * @since 2021/12/15 5:38 下午
 */
public interface IEventRepository {

  /**
   * 根据事件唯一标识符查找事件元数据
   *
   * @param identifier 事件唯一标识符
   * @return 事件元数据
   */
  EventMetaEntity findEventMetaByIdentifier(final EventMetaIdentifier identifier);

  /**
   * 根据事件类型查找事件元数据
   *
   * @param eventType 事件类型
   * @return 事件元数据列表
   */
  List<EventMetaEntity> findEventMetasByType(final String eventType);

  /**
   * 根据事件的消息总线查找事件元数据
   *
   * @param eventBus 事件的消息总线类型
   * @return 事件元数据列表
   */
  List<EventMetaEntity> findEventMetasByBus(final MessageBus eventBus);

  /**
   * 创建事件元数据
   *
   * @param eventMetaEntity 预创建的事件元数据实体信息
   */
  void createEventMeta(final EventMetaEntity eventMetaEntity);

  /**
   * 更新事件元数据
   *
   * @param eventMetaEntity 预更新的事件元数据实体信息
   */
  void updateEventMeta(final EventMetaEntity eventMetaEntity);

  /**
   * 删除事件元数据
   *
   * @param identifiers 预删除事件的唯一标识符列表
   */
  void deleteEventMeta(final List<EventMetaIdentifier> identifiers);

  /**
   * 事件存储
   *
   * @param eventStore 事件存储对象
   */
  void eventStore(final EventStoreValueObject eventStore);

  /**
   * 查找事件存储
   *
   * @param eventType 事件类型
   * @return 事件存储列表
   */
  List<EventStoreValueObject> findEventStoresByType(final String eventType);
}
