package oceanstars.ecommerce.message.configuration.kafka;

import java.util.LinkedList;
import java.util.List;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Kafka所有Topic信息配置
 *
 * @author Clover
 * @version 1.0.0
 * @since 2022/1/19 10:46 PM
 */
@ConfigurationProperties("oceanstars.kafka")
public class KafkaTopicsProperties {

  /**
   * Kafka所有Topic配置列表
   */
  private final List<KafkaTopicProperties> topics = new LinkedList<>();

  public List<KafkaTopicProperties> getTopics() {
    return topics;
  }
}
