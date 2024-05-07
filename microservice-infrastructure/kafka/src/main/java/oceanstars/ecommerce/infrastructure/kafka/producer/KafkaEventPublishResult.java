package oceanstars.ecommerce.infrastructure.kafka.producer;

import java.io.Serial;
import oceanstars.ecommerce.common.domain.event.BaseEventPublishResult;

/**
 * Kafka事件发布同步返回结果
 *
 * @author Clover
 * @version 1.0.0
 * @since 2023/12/20 10:51
 */
public class KafkaEventPublishResult extends BaseEventPublishResult {

  @Serial
  private static final long serialVersionUID = 3175670918504004930L;
}
