package oceanstars.ecommerce.infrastructure.jooq.provider;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.time.LocalDateTime;
import oceanstars.ecommerce.common.data.databind.jackson.LocalDateTimeDeserializer;
import oceanstars.ecommerce.common.data.databind.jackson.LocalDateTimeSerializer;
import org.jooq.Converter;
import org.jooq.ConverterProvider;
import org.jooq.JSON;
import org.jooq.exception.DataTypeException;
import org.jooq.impl.DefaultConverterProvider;

/**
 * Json数据转换接口
 *
 * @author Clover
 * @version 1.0.0
 * @since 2022/8/1 16:13
 */
@SuppressWarnings("unchecked")
public class JsonConverterProvider implements ConverterProvider {

  /**
   * 默认Convert SPI
   */
  final ConverterProvider delegate = new DefaultConverterProvider();

  /**
   * JSON映射对象
   */
  final ObjectMapper mapper = new ObjectMapper().registerModule(
      // 新增时间类型模块
      new JavaTimeModule()
          // 新增LocalDateTime类型序列化
          .addSerializer(LocalDateTime.class, new LocalDateTimeSerializer())
          // 新增LocalDateTime类型反序列化
          .addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer()));

  @Override
  public <T, U> Converter<T, U> provide(Class<T> tType, Class<U> uType) {

    // JSON类型使用自定义转换处理
    if (tType == JSON.class) {
      return Converter.ofNullable(tType, uType, t -> {
        try {
          return mapper.readValue(((JSON) t).data(), uType);
        } catch (Exception e) {
          throw new DataTypeException("JSON mapping error", e);
        }
      }, u -> {
        try {
          return (T) JSON.valueOf(mapper.writeValueAsString(u));
        } catch (Exception e) {
          throw new DataTypeException("JSON mapping error", e);
        }
      });
    }
    // 其他类型使用JOOQ默认转换处理
    else {
      return delegate.provide(tType, uType);
    }
  }
}
