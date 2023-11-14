package oceanstars.ecommerce.message.application.cqrs.handler;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;
import jakarta.annotation.Resource;
import oceanstars.ecommerce.common.cqrs.ICommandHandler;
import oceanstars.ecommerce.common.tools.SerializeUtil;
import oceanstars.ecommerce.message.api.rpc.v1.dto.MessageContentDto;
import oceanstars.ecommerce.message.api.rpc.v1.dto.MessagePublishCommand;
import oceanstars.ecommerce.message.api.rpc.v1.dto.MessagePublishResult;
import oceanstars.ecommerce.message.domain.aggregate.event.entity.EventMetaEntity;
import oceanstars.ecommerce.message.domain.aggregate.event.entity.valueobject.EventContentValueObject;
import oceanstars.ecommerce.message.domain.aggregate.event.entity.valueobject.EventStoreValueObject;
import oceanstars.ecommerce.message.domain.aggregate.event.repository.IEventRepository;
import org.springframework.stereotype.Component;

/**
 * 消息发布命令处理类
 *
 * @author Clover
 * @version 1.0.0
 * @since 2021/12/17 2:38 下午
 */
@Component(value = "messagePublishCommandHandler")
public class MessagePublishCommandHandler implements ICommandHandler<MessagePublishResult, MessagePublishCommand> {

  /**
   * 事件聚合仓储接口
   */
  @Resource
  private IEventRepository eventRepository;

  @Override
  public MessagePublishResult handle(final MessagePublishCommand command) {

    // 根据命令信息构建事件存储信息
    final EventStoreValueObject eventStore = this.createEventStore(command);
    // 根据消息事件类型查找对应事件元数据信息
    final List<EventMetaEntity> eventMetas = eventRepository.findEventMetasByType(eventStore.getType());

    eventMetas.forEach(eventMeta -> eventMeta.publish(eventStore));

    return MessagePublishResult.newBuilder().setIdentifier("").build();
  }

  /**
   * 创建消息聚合
   *
   * @param request 消息发布命令信息
   * @return 消息聚合信息
   */
  private EventStoreValueObject createEventStore(final MessagePublishCommand request) {

    // 获取消息类型
    String type = request.getType();
    // 获取消息内容
    final MessageContentDto<?> messageContent = SerializeUtil.deserialize(request.getContent().toByteArray(), MessageContentDto.class);

    // 构建事件内容值对象
    final EventContentValueObject<?> content = EventContentValueObject.newBuilder(messageContent.getPayload())
        .headers(messageContent.getHeaders())
        .build();
    // 消息源类型：聚合对象类型
    final String sourceType = request.getSourceType();
    // 消息源ID：聚合对象ID
    final String sourceId = request.getSourceId();
    // 消息发生时间
    final LocalDateTime occurredOn = LocalDateTime.ofEpochSecond(request.getOccurredOn().getSeconds(), request.getOccurredOn().getNanos(),
        ZoneOffset.UTC);

    return EventStoreValueObject.newBuilder(type, content, sourceType, sourceId, occurredOn).build();
  }
}
