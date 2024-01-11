package oceanstars.ecommerce.infrastructure.kafka.serialization;

import java.util.Map;
import oceanstars.ecommerce.common.tools.SerializeUtil;
import org.apache.kafka.common.header.Headers;
import org.apache.kafka.common.serialization.Serializer;
import org.springframework.lang.Nullable;

/**
 * Kafka Protobuf序列化类
 *
 * @author Clover
 * @version 1.0.0
 * @since 2022/1/19 3:14 PM
 */
public class KafkaProtobufSerializer<T> implements Serializer<T> {

  @Override
  public synchronized void configure(Map<String, ?> configs, boolean isKey) {
    // 针对序列化器的配置，可以根据需要进行处理
  }

  @Override
  public byte[] serialize(String topic, @Nullable T data) {

    if (data == null) {
      return new byte[0];
    }

    return this.serialize(topic, null, data);
  }

  @Override
  public byte[] serialize(String topic, Headers headers, T data) {

    if (data == null) {
      return new byte[0];
    }

    return SerializeUtil.serialize(data);
  }

  @Override
  public void close() {
    // ---
  }
}
