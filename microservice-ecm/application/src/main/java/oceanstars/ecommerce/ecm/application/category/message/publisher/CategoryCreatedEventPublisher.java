package oceanstars.ecommerce.ecm.application.category.message.publisher;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.HashMap;
import java.util.Map;
import oceanstars.ecommerce.ecm.domain.category.event.CategoryCreated;
import oceanstars.ecommerce.infrastructure.kafka.producer.KafkaEventPublisher;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.MessageHeaders;
import org.springframework.stereotype.Component;

/**
 * 事件发布器: 内容已创建
 *
 * @author Clover
 * @version 1.0.0
 * @since 2024/1/25 14:39
 */
@Component
public class CategoryCreatedEventPublisher extends KafkaEventPublisher<CategoryCreated> {

  public CategoryCreatedEventPublisher(KafkaTemplate<?, ?> kafkaTemplate) {
    super(kafkaTemplate);
  }

  @Override
  protected MessageHeaders buildHeaders(CategoryCreated event) {
    final Map<String, Object> headers = HashMap.newHashMap(1);
    headers.put(KafkaHeaders.TOPIC, "oceanstars.notification.ecm.category.created");
    headers.put(KafkaHeaders.KEY, "ecm.category");
    headers.put(KafkaHeaders.TIMESTAMP, LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli());

    return new MessageHeaders(headers);
  }
}
