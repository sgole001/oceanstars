package oceanstars.ecommerce.common.data.databind.jackson;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import oceanstars.ecommerce.common.constant.DatePattern;

/**
 * jackson反序列化LocalDate模块
 *
 * @author Clover
 * @version 1.0.0
 * @since 2021/11/3 3:57 下午
 */
public class LocalDateDeserializer extends JsonDeserializer<LocalDate> {

  @Override
  public LocalDate deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
    String date = jsonParser.getValueAsString();
    return LocalDate.parse(date, DateTimeFormatter.ofPattern(DatePattern.NORM_DATE_PATTERN));
  }
}