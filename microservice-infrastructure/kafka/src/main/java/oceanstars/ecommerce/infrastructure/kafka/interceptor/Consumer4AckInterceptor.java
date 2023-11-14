package oceanstars.ecommerce.infrastructure.kafka.interceptor;

import java.util.Map;
import org.apache.kafka.clients.consumer.ConsumerInterceptor;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.OffsetAndMetadata;
import org.apache.kafka.common.TopicPartition;

/**
 * Kafka消费者拦截器：业务成功消费后统一处理逻辑
 *
 * @author Clover
 * @version 1.0.0
 * @since 2022/1/19 2:45 PM
 */
public class Consumer4AckInterceptor<K, V> implements ConsumerInterceptor<K, V> {

  @Override
  public ConsumerRecords<K, V> onConsume(ConsumerRecords<K, V> consumerRecords) {
    return null;
  }

  @Override
  public void onCommit(Map<TopicPartition, OffsetAndMetadata> map) {
    // ...
  }

  @Override
  public void close() {
    // ...
  }

  @Override
  public void configure(Map<String, ?> map) {
    // ...
  }
}
