package oceanstars.ecommerce.user.application.message.listener;

import oceanstars.ecommerce.user.api.message.payload.resource.ResourceTypeCreatedPayload;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.listener.adapter.ConsumerRecordMetadata;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.stereotype.Component;

/**
 * <此类的功能说明>
 *
 * @author Clover
 * @version 1.0.0
 * @since 2023/12/10 13:10
 */
@Component
public class ResourceTypeCreatedListener {

  @KafkaListener(id = "resource-type-created", topics = "oceanstars.notification.user.resource-type.created")
  public void listen(Message<?> message, ConsumerRecordMetadata meta, Acknowledgment ack) {

    final MessageHeaders headers = message.getHeaders();

    final ResourceTypeCreatedPayload payload = (ResourceTypeCreatedPayload) message.getPayload();

    System.out.println(headers.get(KafkaHeaders.TOPIC));
    System.out.println(headers.get(KafkaHeaders.TIMESTAMP));
    System.out.println(payload.getResourceId());

    ack.acknowledge();
  }
}
