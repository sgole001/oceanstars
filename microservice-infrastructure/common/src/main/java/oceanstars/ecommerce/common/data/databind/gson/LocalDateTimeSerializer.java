package oceanstars.ecommerce.common.data.databind.gson;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import oceanstars.ecommerce.common.constant.DatePattern;

/**
 * Gson序列化LocalDateTime模块
 *
 * @author Clover
 * @version 1.0.0
 * @since 2021/11/3 3:55 下午
 */
public class LocalDateTimeSerializer implements JsonSerializer<LocalDateTime> {

  @Override
  public JsonElement serialize(LocalDateTime localDateTime, Type type, JsonSerializationContext jsonSerializationContext) {
    return new JsonPrimitive(localDateTime.format(DateTimeFormatter.ofPattern(DatePattern.NORM_DATETIME_PATTERN)));
  }
}
