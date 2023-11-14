package oceanstars.ecommerce.message.domain.aggregate.event.repository.impl;

import java.util.List;
import java.util.Objects;
import jakarta.annotation.Resource;
import oceanstars.ecommerce.common.exception.BusinessException;
import oceanstars.ecommerce.common.tools.MessageUtil;
import oceanstars.ecommerce.common.tools.SerializeUtil;
import oceanstars.ecommerce.message.constant.MessageMessageConstant;
import oceanstars.ecommerce.message.constant.enums.MessageEnum.MessageBus;
import oceanstars.ecommerce.message.domain.aggregate.event.entity.EventMetaEntity;
import oceanstars.ecommerce.message.domain.aggregate.event.entity.EventMetaIdentifier;
import oceanstars.ecommerce.message.domain.aggregate.event.entity.strategy.EventContext;
import oceanstars.ecommerce.message.domain.aggregate.event.entity.strategy.IEventStrategy;
import oceanstars.ecommerce.message.domain.aggregate.event.entity.valueobject.EventStoreValueObject;
import oceanstars.ecommerce.message.domain.aggregate.event.repository.IEventRepository;
import oceanstars.ecommerce.message.repository.generate.tables.daos.MessageEventMetaDao;
import oceanstars.ecommerce.message.repository.generate.tables.pojos.MessageEventMetaPojo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

/**
 * 事件聚合仓储实现类
 *
 * @author Clover
 * @version 1.0.0
 * @since 2021/12/15 5:56 下午
 */
@Service(value = "eventRepository")
public final class EventRepositoryImpl implements IEventRepository {

  /**
   * 事件元数据的数据访问对象
   */
  @Resource
  private MessageEventMetaDao messageEventMetaDao;

  /**
   * 日志管理器
   */
  private final Logger logger = LoggerFactory.getLogger(EventRepositoryImpl.class);

  @Override
  public EventMetaEntity findEventMetaByIdentifier(final EventMetaIdentifier identifier) {

    // 根据事件元数据唯一识别符获取事件元数据
    final MessageEventMetaPojo eventMetaPojo = this.messageEventMetaDao.fetchOneByCode(identifier.toString());

    if (null == eventMetaPojo) {
      throw new BusinessException(MessageMessageConstant.MSG_BIZ_00000, identifier.toString());
    }

    return this.toEventMetaEntity(eventMetaPojo);
  }

  @Override
  public List<EventMetaEntity> findEventMetasByType(final String eventType) {

    // 根据事件类型获取事件元数据
    final List<MessageEventMetaPojo> eventMetas = this.messageEventMetaDao.fetchByType(eventType);

    if (CollectionUtils.isEmpty(eventMetas)) {
      throw new BusinessException(MessageMessageConstant.MSG_BIZ_00001, eventType);
    }

    return eventMetas.stream().map(this::toEventMetaEntity).toList();
  }

  @Override
  public List<EventMetaEntity> findEventMetasByBus(final MessageBus eventBus) {

    // 根据事件总线类型获取事件元数据
    final List<MessageEventMetaPojo> eventMetas = this.messageEventMetaDao.fetchByBus(eventBus.getCode().shortValue());

    if (CollectionUtils.isEmpty(eventMetas)) {
      throw new BusinessException(MessageMessageConstant.MSG_BIZ_00002, eventBus.getName());
    }

    return eventMetas.stream().map(this::toEventMetaEntity).toList();
  }

  @Override
  public void createEventMeta(final EventMetaEntity eventMetaEntity) {

    // 获取事件唯一识别符
    final String identifier = eventMetaEntity.getIdentifier().toString();
    // 根据事件元数据唯一识别符获取事件元数据
    MessageEventMetaPojo eventMetaPojo = this.messageEventMetaDao.fetchOneByCode(identifier);

    if (null != eventMetaPojo) {
      throw new BusinessException(MessageMessageConstant.MSG_BIZ_00000, identifier);
    }

    // 根据事件元数据实体构建事件元数据数据库映射
    eventMetaPojo = this.fromEventMetaEntity(eventMetaEntity);

    // 插入数据库
    this.messageEventMetaDao.insert(eventMetaPojo);
  }

  @Override
  public void updateEventMeta(final EventMetaEntity eventMetaEntity) {

    // 获取事件唯一识别符
    final String identifier = eventMetaEntity.getIdentifier().toString();
    // 根据事件元数据唯一识别符获取事件元数据
    final MessageEventMetaPojo eventMetaPojo = this.messageEventMetaDao.fetchOneByCode(identifier);

    if (null == eventMetaPojo) {
      throw new BusinessException(MessageMessageConstant.MSG_BIZ_00000, identifier);
    }

    // 更新配置
    eventMetaPojo.setConfig(SerializeUtil.serialize(eventMetaEntity.getConfiguration()));

    this.messageEventMetaDao.update(eventMetaPojo);
  }

  @Override
  public void deleteEventMeta(final List<EventMetaIdentifier> identifiers) {

    identifiers.forEach(identifier -> {

      // 根据事件元数据唯一识别符获取事件元数据
      final MessageEventMetaPojo eventMetaPojo = this.messageEventMetaDao.fetchOneByCode(identifier.toString());

      if (null == eventMetaPojo) {
        logger.warn(MessageUtil.ACCESSOR.getMessage(MessageMessageConstant.MSG_BIZ_00000, identifier.toString()));
        return;
      }

      this.messageEventMetaDao.delete(eventMetaPojo);
    });
  }

  @Override
  public void eventStore(final EventStoreValueObject eventStore) {

  }

  @Override
  public List<EventStoreValueObject> findEventStoresByType(String eventType) {
    return null;
  }

  /**
   * 根据事件元数据数据库映射构建事件元数据实体
   *
   * @param eventMetaPojo 事件元数据数据库映射
   * @return 事件元数据实体
   */
  private EventMetaEntity toEventMetaEntity(final MessageEventMetaPojo eventMetaPojo) {

    // 构建事件元数据实体
    final EventMetaEntity eventMetaEntity = EventMetaEntity.newBuilder(
            eventMetaPojo.getType(),
            Objects.requireNonNull(MessageBus.of(eventMetaPojo.getBus().intValue())))
        .configuration(null)
        .build();

    // 获取事件策略
    final IEventStrategy eventStrategy = new EventContext(eventMetaEntity.getBus()).getEventStrategy();
    // 配置事件元数据
    eventStrategy.config(eventMetaEntity, eventMetaPojo.getConfig());
    // 配置委托者
    eventMetaEntity.delegate(eventMetaPojo);

    return eventMetaEntity;
  }

  /**
   * 根据事件元数据实体构建事件元数据数据库映射
   *
   * @param eventMetaEntity 事件元数据实体
   * @return 事件元数据数据库映射
   */
  private MessageEventMetaPojo fromEventMetaEntity(final EventMetaEntity eventMetaEntity) {

    // 初始化事件元数据数据库映射对象
    final MessageEventMetaPojo eventMetaPojo = new MessageEventMetaPojo();
    // 事件编码
    eventMetaPojo.setCode(eventMetaEntity.getIdentifier().toString());
    // 事件类型
    eventMetaPojo.setType(eventMetaEntity.getType());
    // 事件总线类型
    eventMetaPojo.setBus(eventMetaEntity.getBus().getCode().shortValue());
    // 事件配置
    eventMetaPojo.setConfig(SerializeUtil.serialize(eventMetaEntity.getConfiguration()));

    return eventMetaPojo;
  }
}
