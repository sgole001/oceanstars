package oceanstars.ecommerce.message.domain.aggregate.event.entity.valueobject;

/**
 * Kafka消息总线的事件元数据配置类
 *
 * @author Clover
 * @version 1.0.0
 * @since 2021/12/14 3:15 下午
 */
public class KafkaEventConfigurationValueObject extends BaseEventConfigurationValueObject {

  /**
   * Topic
   */
  private final String topic;

  /**
   * 分区
   */
  private final Integer partition;

  /**
   * 分区Key
   */
  private final Object key;

  /**
   * 构造函数：根据构造器初始化事件元数据配置信息
   *
   * @param builder 构造器
   */
  private KafkaEventConfigurationValueObject(Builder builder) {
    topic = builder.topic;
    partition = builder.partition;
    key = builder.key;
  }

  /**
   * 创建事件元数据配置信息构造器
   *
   * @return 事件元数据配置信息构造器
   */
  public static Builder newBuilder() {
    return new Builder();
  }

  public String getTopic() {
    return topic;
  }

  public Integer getPartition() {
    return partition;
  }

  public Object getKey() {
    return key;
  }

  /**
   * Kafka消息总线的事件元数据配置构造器
   *
   * @author Clover
   * @version 1.0.0
   * @since 2021/12/14 3:22 下午
   */
  public static final class Builder {

    private String topic;
    private Integer partition;
    private Object key;

    public Builder topic(String topic) {
      this.topic = topic;
      return this;
    }

    public Builder partition(Integer partition) {
      this.partition = partition;
      return this;
    }

    public Builder key(Object key) {
      this.key = key;
      return this;
    }

    public KafkaEventConfigurationValueObject build() {
      return new KafkaEventConfigurationValueObject(this);
    }
  }
}
