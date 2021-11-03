package oceanstars.ecommerce.common.data.databind.jackson;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import java.io.IOException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import oceanstars.ecommerce.common.constant.DatePattern;

/**
 * jackson序列化LocalTime模块
 *
 * @author Clover
 * @version 1.0.0
 * @since 2021/11/3 4:11 下午
 */
public class LocalTimeSerializer extends JsonSerializer<LocalTime> {

  @Override
  public void serialize(LocalTime localTime, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
    jsonGenerator.writeString(localTime.format(DateTimeFormatter.ofPattern(DatePattern.NORM_TIME_PATTERN)));
  }
}
