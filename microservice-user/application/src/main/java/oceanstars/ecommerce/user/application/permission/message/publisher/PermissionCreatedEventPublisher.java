package oceanstars.ecommerce.user.application.permission.message.publisher;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.HashMap;
import java.util.Map;
import oceanstars.ecommerce.infrastructure.kafka.producer.KafkaEventPublisher;
import oceanstars.ecommerce.user.domain.permission.event.PermissionCreated;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.MessageHeaders;
import org.springframework.stereotype.Component;

/**
 * 事件发布器: 资源类型已创建
 *
 * @author Clover
 * @version 1.0.0
 * @since 2023/12/11 12:19
 */
@Component(value = "permissionCreatedEventPublisher")
public class PermissionCreatedEventPublisher extends KafkaEventPublisher<PermissionCreated> {

  public PermissionCreatedEventPublisher(KafkaTemplate<?, ?> kafkaTemplate) {
    super(kafkaTemplate);
  }

  @Override
  protected MessageHeaders buildHeaders(PermissionCreated event) {
    final Map<String, Object> headers = HashMap.newHashMap(1);
    headers.put(KafkaHeaders.TOPIC, "oceanstars.notification.user.permission.created");
    headers.put(KafkaHeaders.KEY, "oceanstars");
    headers.put(KafkaHeaders.TIMESTAMP, LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli());

    return new MessageHeaders(headers);
  }
}
