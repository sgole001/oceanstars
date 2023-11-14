package oceanstars.ecommerce.infrastructure.kafka.serialization;

import java.nio.charset.StandardCharsets;
import java.util.Map;
import oceanstars.ecommerce.common.tools.SerializeUtil;
import org.apache.kafka.common.header.Header;
import org.apache.kafka.common.header.Headers;
import org.apache.kafka.common.serialization.Deserializer;

/**
 * Kafka Protobuf反序列化类
 *
 * @author Clover
 * @version 1.0.0
 * @since 2022/1/19 3:37 PM
 */
@SuppressWarnings("unchecked")
public class ProtobufDeserializer<T> implements Deserializer<T> {

  @Override
  public void configure(Map<String, ?> configs, boolean isKey) {
    // ---
  }

  @Override
  public T deserialize(String topic, byte[] data) {
    return null;
  }

  @Override
  public T deserialize(String topic, Headers headers, byte[] data) {

    if (null == data) {
      return null;
    }

    // 获取Protobuf序列化对象类型消息头信息
    final Header header = headers.lastHeader(ProtobufSerializer.HEADER_4_PROTO_CLASS_TYPE);
    // 获取Protobuf序列化对象类型名
    final String clazzName = new String(header.value(), StandardCharsets.UTF_8);

    try {
      return SerializeUtil.deserialize(data, (Class<T>) Class.forName(clazzName));
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    }

    return null;
  }

  @Override
  public void close() {
    // ---
  }
}
