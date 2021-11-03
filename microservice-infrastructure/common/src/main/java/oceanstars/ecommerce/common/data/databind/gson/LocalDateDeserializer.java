package oceanstars.ecommerce.common.data.databind.gson;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import oceanstars.ecommerce.common.constant.DatePattern;

/**
 * Gson反序列化LocalDate模块
 *
 * @author Clover
 * @version 1.0.0
 * @since 2021/11/3 3:53 下午
 */
public class LocalDateDeserializer implements JsonDeserializer<LocalDate> {

  @Override
  public LocalDate deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext)
      throws JsonParseException {
    String date = jsonElement.getAsJsonPrimitive().getAsString();
    return LocalDate.parse(date, DateTimeFormatter.ofPattern(DatePattern.NORM_DATE_PATTERN));
  }
}
