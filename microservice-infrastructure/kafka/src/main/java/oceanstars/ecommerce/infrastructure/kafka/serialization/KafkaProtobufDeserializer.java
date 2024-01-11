package oceanstars.ecommerce.infrastructure.kafka.serialization;

import java.util.Map;
import oceanstars.ecommerce.common.tools.SerializeUtil;
import org.apache.kafka.common.header.Headers;
import org.apache.kafka.common.serialization.Deserializer;

/**
 * Kafka Protobuf反序列化类
 *
 * @author Clover
 * @version 1.0.0
 * @since 2022/1/19 3:37 PM
 */
public class KafkaProtobufDeserializer<T> implements Deserializer<T> {

  @Override
  public void configure(Map<String, ?> configs, boolean isKey) {
    // 针对反序列化器的配置，可以根据需要进行处理
  }

  @Override
  public T deserialize(String topic, byte[] data) {

    if (data == null || data.length == 0) {
      return null;
    }

    return this.deserialize(topic, null, data);
  }

  @Override
  public T deserialize(String topic, Headers headers, byte[] data) {

    if (null == data || data.length == 0) {
      return null;
    }

    return SerializeUtil.deserialize(data);
  }

  @Override
  public void close() {
    // ---
  }
}
