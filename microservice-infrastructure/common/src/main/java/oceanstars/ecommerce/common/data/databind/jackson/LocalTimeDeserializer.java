package oceanstars.ecommerce.common.data.databind.jackson;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import oceanstars.ecommerce.common.constant.DatePattern;
import oceanstars.ecommerce.common.exception.SystemException;

/**
 * jackson反序列化LocalDateTime模块
 *
 * @author Clover
 * @version 1.0.0
 * @since 2021/11/3 4:08 下午
 */
public class LocalTimeDeserializer extends JsonDeserializer<LocalTime> {

  /**
   * 时间格式化类型
   */
  private static final List<String> DATE_TIME_FORMATTER = Arrays.asList(DatePattern.NORM_TIME_PATTERN, DatePattern.NORM_TIME_MS_PATTERN);

  @Override
  public LocalTime deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) {

    for (String formatter : DATE_TIME_FORMATTER) {

      try {
        return LocalTime.parse(jsonParser.getValueAsString(), DateTimeFormatter.ofPattern(formatter));
      } catch (Exception e) {
        // 解析失败，继续迭代尝试解析
      }
    }

    // 全部格式解析失败。 TODO: LocalTime jackson反序列化失败
    throw new SystemException("");
  }
}