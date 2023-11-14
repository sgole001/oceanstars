package oceanstars.ecommerce.infrastructure.kafka.interceptor;

import java.util.Map;
import oceanstars.ecommerce.common.session.Sessions;
import oceanstars.ecommerce.common.tools.SerializeUtil;
import oceanstars.ecommerce.common.tools.SessionUtil;
import org.apache.kafka.clients.producer.ProducerInterceptor;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

/**
 * Kafka生产者拦截器：Session传递
 *
 * @author Clover
 * @version 1.0.0
 * @since 2021/11/3 11:49 下午
 */
public class Producer4SessionInterceptor<K, V> implements ProducerInterceptor<K, V> {


  @Override
  public ProducerRecord<K, V> onSend(ProducerRecord<K, V> producerRecord) {

    // 添加Session信息至消息头部
    producerRecord.headers().add(Sessions.SESSION_KEY, SerializeUtil.serialize(SessionUtil.getSessions()));

    return producerRecord;
  }

  @Override
  public void onAcknowledgement(RecordMetadata recordMetadata, Exception e) {
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
