package oceanstars.ecommerce.common.data.databind.gson;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import oceanstars.ecommerce.common.constant.DatePattern;

/**
 * Gson序列化LocalDate模块
 *
 * @author Clover
 * @version 1.0.0
 * @since 2021/11/3 3:55 下午
 */
public class LocalDateSerializer implements JsonSerializer<LocalDate> {

  @Override
  public JsonElement serialize(LocalDate localDate, Type type, JsonSerializationContext jsonSerializationContext) {
    return new JsonPrimitive(localDate.format(DateTimeFormatter.ofPattern(DatePattern.NORM_DATE_PATTERN)));
  }
}
