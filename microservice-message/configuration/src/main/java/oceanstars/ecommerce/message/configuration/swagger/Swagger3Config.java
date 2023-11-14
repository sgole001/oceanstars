package oceanstars.ecommerce.message.configuration.swagger;

import io.swagger.annotations.ApiOperation;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.env.Profiles;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

/**
 * 消息服务Swagger3.0配置
 *
 * @author Clover
 * @version 1.0.0
 * @since 2021/11/25 5:22 下午
 */
@Configuration
public class Swagger3Config {

  @Bean
  public Docket createRestApi(Environment env) {

    // 设置要暴漏接口文档的配置环境
    Profiles profile = Profiles.of("sit", "uat");
    boolean triggerSwagger = env.acceptsProfiles(profile);

    return new Docket(DocumentationType.OAS_30)
        .apiInfo(apiInfo())
        .enable(triggerSwagger)
        .select()
        .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
        .paths(PathSelectors.any())
        .build();
  }

  private ApiInfo apiInfo() {
    return new ApiInfoBuilder()
        .title("消息服务接口文档")
        .description("更多请咨询服务开发者sgole001@gmail.com。")
        .contact(new Contact("Clover", "", "sgole001@gmail.com"))
        .version("1.0")
        .build();
  }
}
