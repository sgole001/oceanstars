package oceanstars.ecommerce.message.api.rpc.v1.dto;

import com.google.protobuf.ByteString;
import com.google.protobuf.util.Timestamps;
import java.util.Map;
import oceanstars.ecommerce.common.domain.event.DomainEvent;
import oceanstars.ecommerce.common.tools.SerializeUtil;

/**
 * 消息发布信息委托者类
 *
 * @author Clover
 * @version 1.0.0
 * @since 2021/12/20 2:18 下午
 */
public final class DomainEventDelegator {

  /**
   * 领域事件委托
   *
   * @param domainEvent 领域事件
   * @param <E>         领域事件类型
   * @return 领域事件委托者
   */
  public static <E extends DomainEvent<?, ?>> MessagePublishCommand delegate(E domainEvent) {

    // 构建消息发送命令信息
    return MessagePublishCommand.newBuilder()
        .setType(domainEvent.toString())
        .setContent(ByteString.copyFrom(SerializeUtil.serialize(MessageContentDto.newBuilder(domainEvent.getData()).build())))
        .setSourceType(domainEvent.getSourceType())
        .setSourceId(domainEvent.getSourceId())
        .setOccurredOn(Timestamps.fromMillis(domainEvent.getTimestamp()))
        .build();
  }

  /**
   * 领域事件委托(含消息头信息)
   *
   * @param domainEvent 领域事件
   * @param headers     消息头信息
   * @param <E>         领域事件类型
   * @return 领域事件委托者
   */
  public static <E extends DomainEvent<?, ?>> MessagePublishCommand delegate(final E domainEvent, final Map<String, Object> headers) {

    // 构建消息发送命令信息
    return MessagePublishCommand.newBuilder()
        .setType(domainEvent.toString())
        .setContent(ByteString.copyFrom(SerializeUtil.serialize(MessageContentDto.newBuilder(domainEvent.getData()).headers(headers).build())))
        .setSourceType(domainEvent.getSourceType())
        .setSourceId(domainEvent.getSourceId())
        .setOccurredOn(Timestamps.fromMillis(domainEvent.getTimestamp()))
        .build();
  }
}
