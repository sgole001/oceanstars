package oceanstars.ecommerce.common.data.databind.jackson;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import oceanstars.ecommerce.common.constant.DatePattern;

/**
 * jackson序列化LocalDate模块
 *
 * @author Clover
 * @version 1.0.0
 * @since 2021/11/3 3:58 下午
 */
public class LocalDateSerializer extends JsonSerializer<LocalDate> {

  @Override
  public void serialize(LocalDate localDate, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
    jsonGenerator.writeString(localDate.format(DateTimeFormatter.ofPattern(DatePattern.NORM_DATE_PATTERN)));
  }
}
