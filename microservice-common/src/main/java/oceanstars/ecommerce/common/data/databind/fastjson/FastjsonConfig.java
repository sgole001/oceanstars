package oceanstars.ecommerce.common.data.databind.fastjson;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import oceanstars.ecommerce.common.constant.DatePattern;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.format.Formatter;
import org.springframework.lang.NonNull;

/**
 * fastjson配置类
 *
 * @author Clover
 * @version 1.0.0
 * @since 2021/11/3 3:43 下午
 */
@AutoConfiguration
public class FastjsonConfig {

  @Bean
  public Formatter<LocalDateTime> localDateTimeFormatter() {

    return new Formatter<>() {

      @Override
      @NonNull
      public String print(@NonNull LocalDateTime object, @NonNull Locale locale) {
        return object.format(DateTimeFormatter.ofPattern(DatePattern.NORM_DATETIME_PATTERN));
      }

      @Override
      @NonNull
      public LocalDateTime parse(@NonNull String text, @NonNull Locale locale) {
        return LocalDateTime.parse(text, DateTimeFormatter.ofPattern(DatePattern.NORM_DATETIME_PATTERN));
      }
    };
  }
}
