package oceanstars.ecommerce.common.data.databind.jackson;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.time.LocalDateTime;
import java.time.LocalTime;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

/**
 * Jackson配置类
 *
 * @author Clover
 * @version 1.0.0
 * @since 2021/11/3 3:56 下午
 */
@Configuration
public class JacksonConfig {

  @Bean(value = "objectMapper")
  @Primary
  public ObjectMapper objectMapper() {

    ObjectMapper objectMapper = new ObjectMapper();
    JavaTimeModule module = new JavaTimeModule();
    module.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer());
    module.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer());
    module.addSerializer(LocalTime.class, new LocalTimeSerializer());
    module.addDeserializer(LocalTime.class, new LocalTimeDeserializer());

    objectMapper.registerModule(module);

    return objectMapper;
  }
}
