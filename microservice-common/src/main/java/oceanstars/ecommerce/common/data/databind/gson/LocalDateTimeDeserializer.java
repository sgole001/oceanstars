package oceanstars.ecommerce.common.data.databind.gson;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import oceanstars.ecommerce.common.constant.DatePattern;

/**
 * Gson反序列化LocalDateTime模块
 *
 * @author Clover
 * @version 1.0.0
 * @since 2021/11/3 3:56 下午
 */
public class LocalDateTimeDeserializer implements JsonDeserializer<LocalDateTime> {

  @Override
  public LocalDateTime deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext)
      throws JsonParseException {
    String datetime = jsonElement.getAsJsonPrimitive().getAsString();
    return LocalDateTime.parse(datetime, DateTimeFormatter.ofPattern(DatePattern.NORM_DATETIME_PATTERN));
  }
}
