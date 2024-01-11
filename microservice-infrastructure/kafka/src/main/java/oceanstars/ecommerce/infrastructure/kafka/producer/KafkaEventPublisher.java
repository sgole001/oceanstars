package oceanstars.ecommerce.infrastructure.kafka.producer;

import java.util.concurrent.CompletableFuture;
import oceanstars.ecommerce.common.domain.DomainEvent;
import oceanstars.ecommerce.common.domain.IEventPublisher;
import oceanstars.ecommerce.infrastructure.kafka.spi.KafkaEventSourcingStorageSPI;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.core.io.support.SpringFactoriesLoader;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.MessageBuilder;

/**
 * Kafka事件发布器
 *
 * @author Clover
 * @version 1.0.0
 * @since 2023/12/11 14:45
 */
public abstract class KafkaEventPublisher<E extends DomainEvent<?, ?>> implements IEventPublisher<KafkaEventPublishResult, E> {

  protected final KafkaTemplate<?, ?> kafkaTemplate;

  /**
   * 日志管理器
   */
  private final Logger logger = LogManager.getLogger(KafkaEventPublisher.class);

  protected KafkaEventPublisher(KafkaTemplate<?, ?> kafkaTemplate) {
    this.kafkaTemplate = kafkaTemplate;
  }

  @Override
  public KafkaEventPublishResult publish(E event) {

    // 构建消息头
    final MessageHeaders headers = this.buildHeaders(event);

    // 构建消息体
    final Message<?> message = MessageBuilder.createMessage(event.getData(), headers);

    // 消息存储实现事件溯源（Event Sourcing）
    SpringFactoriesLoader.loadFactories(KafkaEventSourcingStorageSPI.class, null).forEach(
        storage -> storage.store(event)
    );

    // 发送消息
    CompletableFuture<? extends SendResult<?, ?>> future = this.kafkaTemplate.send(message);
    // 异步处理发送结果
    future.whenComplete(((sendResult, throwable) -> {
      if (null != throwable) {
        // 发送失败
        this.onFailure(message, throwable);
      } else {
        // 发送成功
        this.onSuccess(message, sendResult);
      }
    }));

    return new KafkaEventPublishResult();
  }

  /**
   * 构建消息头
   *
   * @param event 领域事件
   * @return 消息头
   */
  protected abstract MessageHeaders buildHeaders(E event);

  /**
   * 发送成功处理
   *
   * @param message    发送消息
   * @param sendResult 发送结果
   */
  protected void onSuccess(Message<?> message, SendResult<?, ?> sendResult) {
    // 发送成功不做任何处理
    logger.info("Event[{}] is sent successfully.", message.getHeaders().getId());
  }

  /**
   * 发送失败处理
   *
   * @param message   发送消息
   * @param throwable 异常
   */
  protected void onFailure(Message<?> message, Throwable throwable) {
    // 发送成功不做任何处理
    logger.error("Event[{}] is sent failed.", message.getHeaders().getId(), throwable);
  }
}
