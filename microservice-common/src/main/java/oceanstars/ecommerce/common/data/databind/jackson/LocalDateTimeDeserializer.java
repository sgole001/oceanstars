package oceanstars.ecommerce.common.data.databind.jackson;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import java.time.LocalDateTime;
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
 * @since 2021/11/3 3:58 下午
 */
public class LocalDateTimeDeserializer extends JsonDeserializer<LocalDateTime> {

  /**
   * 时间格式化类型
   */
  private static final List<String> DATE_TIME_FORMATTER = Arrays.asList(DatePattern.NORM_DATETIME_PATTERN, DatePattern.NORM_DATETIME_MS_PATTERN);

  @Override
  public LocalDateTime deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) {

    for (String formatter : DATE_TIME_FORMATTER) {

      try {
        return LocalDateTime.parse(jsonParser.getValueAsString(), DateTimeFormatter.ofPattern(formatter));
      } catch (Exception e) {
        // 解析失败，继续迭代尝试解析
      }
    }
    // 全部格式解析失败。 TODO: LocalDateTime jackson反序列化失败
    throw new SystemException("");
  }
}