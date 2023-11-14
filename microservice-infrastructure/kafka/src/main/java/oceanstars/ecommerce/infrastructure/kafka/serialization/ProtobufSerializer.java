package oceanstars.ecommerce.infrastructure.kafka.serialization;

import java.nio.charset.StandardCharsets;
import java.util.Map;
import oceanstars.ecommerce.common.tools.SerializeUtil;
import org.apache.kafka.common.header.Headers;
import org.apache.kafka.common.header.internals.RecordHeader;
import org.apache.kafka.common.serialization.Serializer;
import org.springframework.lang.Nullable;

/**
 * Kafka Protobuf序列化类
 *
 * @author Clover
 * @version 1.0.0
 * @since 2022/1/19 3:14 PM
 */
public class ProtobufSerializer<T> implements Serializer<T> {

  /**
   * Kafka消息头KEY:Protobuf序列化时使用的类类型
   */
  public static final String HEADER_4_PROTO_CLASS_TYPE = "__ProtoClassType__";

  @Override
  public synchronized void configure(Map<String, ?> configs, boolean isKey) {
    // ---
  }

  @Override
  public byte[] serialize(String topic, @Nullable T data) {

    if (data == null) {
      return new byte[0];
    }

    return SerializeUtil.serialize(data);
  }

  @Override
  public byte[] serialize(String topic, Headers headers, T data) {

    if (data == null) {
      return new byte[0];
    }

    if (headers != null) {
      headers.add(new RecordHeader(HEADER_4_PROTO_CLASS_TYPE, data.getClass().getName().getBytes(StandardCharsets.UTF_8)));
    }

    return this.serialize(topic, data);
  }

  @Override
  public void close() {
    // ---
  }
}
