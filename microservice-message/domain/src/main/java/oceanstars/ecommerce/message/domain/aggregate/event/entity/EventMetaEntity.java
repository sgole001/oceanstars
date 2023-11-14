package oceanstars.ecommerce.message.domain.aggregate.event.entity;

import java.util.List;
import oceanstars.ecommerce.common.domain.AggregateRoot;
import oceanstars.ecommerce.common.spring.ApplicationContextProvider;
import oceanstars.ecommerce.message.constant.enums.MessageEnum.MessageBus;
import oceanstars.ecommerce.message.domain.aggregate.event.entity.strategy.EventContext;
import oceanstars.ecommerce.message.domain.aggregate.event.entity.valueobject.BaseEventConfigurationValueObject;
import oceanstars.ecommerce.message.domain.aggregate.event.entity.valueobject.EventStoreValueObject;
import oceanstars.ecommerce.message.domain.aggregate.event.repository.IEventRepository;
import org.springframework.lang.NonNull;

/**
 * 事件元数据实体：聚合根
 *
 * @author Clover
 * @version 1.0.0
 * @since 2021/12/14 2:19 下午
 */
public final class EventMetaEntity extends AggregateRoot<EventMetaIdentifier> {

  /**
   * 事件类型
   */
  private final String type;

  /**
   * 事件总线
   */
  private final MessageBus bus;

  /**
   * 事件配置
   */
  private BaseEventConfigurationValueObject configuration;

  /**
   * 事件存储（事件的发布日志记录）
   */
  private List<EventStoreValueObject> eventStores;

  /**
   * 事件处理策略上下文
   */
  private final EventContext eventContext;

  /**
   * 事件聚合仓储接口
   */
  private final IEventRepository eventRepository;

  /**
   * 私有构造函数:使用构造器构造实体对象
   *
   * @param builder 构造器
   */
  private EventMetaEntity(final Builder builder) {
    super(new EventMetaIdentifier(builder.type, builder.bus));
    this.type = builder.type;
    this.bus = builder.bus;
    this.configuration = builder.configuration;
    this.eventContext = new EventContext(this.bus);
    this.eventRepository = (IEventRepository) ApplicationContextProvider.getBean("eventRepository");
  }

  /**
   * 创建事件元数据实体构造器
   *
   * @param type 事件类型
   * @param bus  事件总线
   * @return 事件元数据实体构造器
   */
  public static EventMetaEntity.Builder newBuilder(@NonNull String type, @NonNull MessageBus bus) {
    return new EventMetaEntity.Builder(type, bus);
  }

  /**
   * 发布事件
   *
   * @param eventStore 事件存储内容
   */
  public void publish(final EventStoreValueObject eventStore) {
    this.eventContext.getEventStrategy().publish(this, eventStore);
  }

  /**
   * 获取事件存储
   *
   * @return 事件存储列表
   */
  public List<EventStoreValueObject> getEventStores() {
    this.eventStores = this.eventRepository.findEventStoresByType(this.getType());
    return this.eventStores;
  }

  public String getType() {
    return type;
  }

  public MessageBus getBus() {
    return bus;
  }

  public BaseEventConfigurationValueObject getConfiguration() {
    return configuration;
  }

  public void setConfiguration(BaseEventConfigurationValueObject configuration) {
    this.configuration = configuration;
  }

  /**
   * 事件元数据实体内部构造类
   *
   * @author Clover
   * @version 1.0.0
   * @since 2021/12/14 2:36 下午
   */
  public static final class Builder {

    private final String type;
    private final MessageBus bus;
    private BaseEventConfigurationValueObject configuration;

    public Builder(String type, MessageBus bus) {
      this.type = type;
      this.bus = bus;
    }

    public Builder configuration(BaseEventConfigurationValueObject conf) {
      configuration = conf;
      return this;
    }

    public EventMetaEntity build() {
      return new EventMetaEntity(this);
    }
  }

  @Override
  public boolean equals(Object obj) {
    return super.equals(obj);
  }

  @Override
  public int hashCode() {
    return super.hashCode();
  }
}
