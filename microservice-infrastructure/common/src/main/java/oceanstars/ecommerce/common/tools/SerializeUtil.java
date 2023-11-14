package oceanstars.ecommerce.common.tools;

import io.protostuff.LinkedBuffer;
import io.protostuff.ProtostuffIOUtil;
import io.protostuff.Schema;
import io.protostuff.runtime.RuntimeSchema;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 序列化工具类
 *
 * @author Clover
 * @version 1.0.0
 * @since 2021/11/29 11:30 上午
 */
@SuppressWarnings({"unchecked"})
public class SerializeUtil {

  /**
   * 避免每次序列化都重新申请Buffer空间
   */
  private static final LinkedBuffer BUFFER = LinkedBuffer.allocate(LinkedBuffer.DEFAULT_BUFFER_SIZE);

  /**
   * 缓存Schema
   */
  private static final Map<Class<?>, Schema<?>> SCHEMA_CACHE = new ConcurrentHashMap<>();

  /**
   * 对象序列化
   *
   * @param obj 序列化对象
   * @param <T> 对象泛型
   * @return 对象序列化信息
   */
  public static <T> byte[] serialize(T obj) {

    if (null == obj) {
      throw new NullPointerException();
    }

    // 获取序列化对象类型
    final Class<T> clazz = (Class<T>) obj.getClass();
    //
    Schema<T> schema = getSchema(clazz);

    // 基于Protobuf底层技术进行数据序列化
    byte[] data;
    try {
      data = ProtostuffIOUtil.toByteArray(obj, schema, BUFFER);
    } finally {
      BUFFER.clear();
    }

    return data;
  }

  /**
   * 对象反序列化
   *
   * @param data  对象序列化信息
   * @param clazz 对象类型
   * @param <T>   对象泛型
   * @return 对象信息
   */
  public static <T> T deserialize(byte[] data, Class<T> clazz) {

    // 根据序列化对象类型获取对应Schema
    final Schema<T> schema = getSchema(clazz);

    // 初始化反序列化对象实例
    T obj = schema.newMessage();
    // 基于Protobuf底层技术反序列化对象
    ProtostuffIOUtil.mergeFrom(data, obj, schema);

    return obj;
  }

  /**
   * 根据序列化对象类型获取对应Schema
   *
   * @param clazz 序列化对象类型
   * @param <T>   对象泛型
   * @return 序列化对象类型对应Schema
   */
  private static <T> Schema<T> getSchema(Class<T> clazz) {

    // 根据序列化对象类型获取对应Schema
    Schema<T> schema = (Schema<T>) SCHEMA_CACHE.get(clazz);

    // 第一次使用的情况，缓存Schema
    if (schema == null) {
      // 懒创建并缓存，RuntimeSchema.getSchema()线程安全
      schema = RuntimeSchema.getSchema(clazz);
      if (null == schema) {
        throw new NullPointerException();
      }
      SCHEMA_CACHE.put(clazz, schema);
    }

    return schema;
  }

  /**
   * 序列化对象基础接口
   */
  public interface Serialization {
    // ...
  }
}
