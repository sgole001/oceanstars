package oceanstars.ecommerce.message.domain.aggregate.event.entity.strategy.impl;

import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.time.temporal.ChronoField;
import java.util.concurrent.CompletableFuture;
import oceanstars.ecommerce.common.spring.ApplicationContextProvider;
import oceanstars.ecommerce.common.tools.SerializeUtil;
import oceanstars.ecommerce.message.domain.aggregate.event.entity.EventMetaEntity;
import oceanstars.ecommerce.message.domain.aggregate.event.entity.valueobject.EventContentValueObject;
import oceanstars.ecommerce.message.domain.aggregate.event.entity.valueobject.EventStoreValueObject;
import oceanstars.ecommerce.message.domain.aggregate.event.entity.valueobject.KafkaEventConfigurationValueObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.kafka.support.SendResult;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;

/**
 * Kafka消息总线的事件策略
 *
 * @author Clover
 * @version 1.0.0
 * @since 2021/11/30 9:35 上午
 */
@SuppressWarnings({"unchecked"})
public class KafkaEventStrategy extends AbstractEventStrategy {

  /**
   * 初始化kafka模板
   */
  private final KafkaTemplate<Integer, String> kafkaTemplate;

  /**
   * 日志管理器
   */
  private final Logger logger = LogManager.getLogger(KafkaEventStrategy.class);

  /**
   * 构造函数：初始化成员变量
   */
  public KafkaEventStrategy() {
    super();
    this.kafkaTemplate = (KafkaTemplate<Integer, String>) ApplicationContextProvider.getBean("kafkaTemplate");
  }

  @Override
  protected Message<?> buildMessage(EventMetaEntity meta, EventStoreValueObject eventStore) {

    // 获取Kafka消息总线事件元数据配置
    final KafkaEventConfigurationValueObject configuration = (KafkaEventConfigurationValueObject) meta.getConfiguration();

    // 获取事件发送内容
    final EventContentValueObject<?> eventContent = eventStore.getContent();

    // 构建Kafka消息
    final MessageBuilder<?> messageBuilder = MessageBuilder.withPayload(eventContent.getPayload());
    // 封装头部信息
    messageBuilder.copyHeaders(eventContent.getHeaders());

    // 消息头添加Topic信息
    messageBuilder.setHeader(KafkaHeaders.TOPIC, configuration.getTopic());
    // 消息头添加分区信息
    if (null != configuration.getPartition()) {
      messageBuilder.setHeader(KafkaHeaders.PARTITION, configuration.getPartition());
    }
    // 消息头添加主键信息
    if (null != configuration.getKey()) {
      messageBuilder.setHeader(KafkaHeaders.KEY, configuration.getKey());
    }
    // 消息头添加时间戳信息
    messageBuilder.setHeader(KafkaHeaders.TIMESTAMP, LocalDateTime.now().getLong(ChronoField.MICRO_OF_SECOND));

    return messageBuilder.build();
  }

  @Override
  public void sendMessage(final Message<?> message) {

    // 发送消息
    final CompletableFuture<SendResult<Integer, String>> future = this.kafkaTemplate.send(message);

    // 发送回调处理
    future.whenComplete((result, ex) -> {
      if (null == ex) {
        // 发送成功不做任何处理
        logger.info(MessageFormat.format("Message[{0}] is sent successfully.", message.getHeaders().getId()));
      } else {
        logger.error(ex.getMessage());
      }
    });
  }

  @Override
  public void config(final EventMetaEntity meta, final byte[] config) {

    // 反序列化配置信息
    final KafkaEventConfigurationValueObject kafkaEventConfig = SerializeUtil.deserialize(config, KafkaEventConfigurationValueObject.class);

    meta.setConfiguration(kafkaEventConfig);
  }
}
