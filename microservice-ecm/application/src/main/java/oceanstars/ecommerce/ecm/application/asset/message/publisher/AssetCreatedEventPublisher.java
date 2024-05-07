package oceanstars.ecommerce.ecm.application.asset.message.publisher;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.HashMap;
import java.util.Map;
import oceanstars.ecommerce.ecm.domain.asset.event.AssetCreated;
import oceanstars.ecommerce.infrastructure.kafka.producer.KafkaEventPublisher;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.MessageHeaders;
import org.springframework.stereotype.Component;

/**
 * 事件发布器: 资产已创建
 *
 * @author Clover
 * @version 1.0.0
 * @since 2024/4/18 10:15
 */
@Component
public class AssetCreatedEventPublisher extends KafkaEventPublisher<AssetCreated> {

  public AssetCreatedEventPublisher(KafkaTemplate<?, ?> kafkaTemplate) {
    super(kafkaTemplate);
  }

  @Override
  protected MessageHeaders buildHeaders(AssetCreated event) {
    final Map<String, Object> headers = HashMap.newHashMap(1);
    headers.put(KafkaHeaders.TOPIC, "oceanstars.notification.ecm.asset.created");
    headers.put(KafkaHeaders.KEY, "ecm.asset");
    headers.put(KafkaHeaders.TIMESTAMP, LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli());

    return new MessageHeaders(headers);
  }
}
