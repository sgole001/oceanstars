package oceanstars.ecommerce.message.domain.aggregate.event.entity.valueobject;

import java.time.LocalDateTime;
import oceanstars.ecommerce.common.domain.ValueObject;
import org.springframework.lang.NonNull;

/**
 * 值对象：事件存储对象
 *
 * @author Clover
 * @version 1.0.0
 * @since 2021/11/25 6:29 下午
 */
public class EventStoreValueObject extends ValueObject {

  /**
   * 事件类型
   */
  private final String type;

  /**
   * 事件内容
   */
  private final EventContentValueObject<?> content;

  /**
   * 事件源类型：聚合对象类型
   */
  private final String sourceType;

  /**
   * 事件源ID：聚合对象ID
   */
  private final String sourceId;

  /**
   * 事件发生时间
   */
  private final LocalDateTime occurredOn;

  /**
   * 私有构造函数:使用构造器构造实体对象
   *
   * @param builder 消息实体构造器
   */
  private EventStoreValueObject(Builder builder) {
    this.type = builder.type;
    this.content = builder.content;
    this.sourceType = builder.sourceType;
    this.sourceId = builder.sourceId;
    this.occurredOn = builder.occurredOn;
  }

  /**
   * 创建消息实体构造器
   *
   * @param type       消息类型
   * @param content    消息内容
   * @param sourceType 消息源类型
   * @param sourceId   消息源ID
   * @param occurredOn 消息发生时间
   * @return 消息实体构造器
   */
  public static Builder newBuilder(
      @NonNull String type,
      @NonNull EventContentValueObject<?> content,
      @NonNull String sourceType,
      @NonNull String sourceId,
      @NonNull LocalDateTime occurredOn) {
    return new Builder(type, content, sourceType, sourceId, occurredOn);
  }

  public String getType() {
    return type;
  }

  public EventContentValueObject<?> getContent() {
    return content;
  }

  public String getSourceType() {
    return sourceType;
  }

  public String getSourceId() {
    return sourceId;
  }

  public LocalDateTime getOccurredOn() {
    return occurredOn;
  }

  /**
   * 事件存储值对象内部构造类
   *
   * @author Clover
   * @version 1.0.0
   * @since 2021/12/2 3:14 下午
   */
  public static final class Builder {

    private final String type;
    private final EventContentValueObject<?> content;
    private final String sourceType;
    private final String sourceId;
    private final LocalDateTime occurredOn;

    /**
     * 事件存储值对象构造器构造函数：初始化必须参数（序列化）
     *
     * @param type       事件类型
     * @param content    事件内容
     * @param sourceType 事件源类型
     * @param sourceId   事件源ID
     * @param occurredOn 事件发生时间
     */
    private Builder(
        String type,
        EventContentValueObject<?> content,
        String sourceType,
        String sourceId,
        LocalDateTime occurredOn) {
      this.type = type;
      this.content = content;
      this.sourceType = sourceType;
      this.sourceId = sourceId;
      this.occurredOn = occurredOn;
    }

    public EventStoreValueObject build() {
      return new EventStoreValueObject(this);
    }
  }
}
