package oceanstars.ecommerce.common.spring;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;

/**
 * Spring配置类
 *
 * @author Clover
 * @version 1.0.0
 * @since 2024/1/31 17:00
 */
@AutoConfiguration
public class SpringConfiguration {

  @Bean
  public ApplicationContextProvider applicationContextProvider() {
    return new ApplicationContextProvider();
  }
}
