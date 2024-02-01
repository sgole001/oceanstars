package oceanstars.ecommerce.common.restful;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.SpecVersion;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;


/**
 * Swagger3.0配置
 *
 * @author Clover
 * @version 1.0.0
 * @since 2023/11/18 16:20
 */
@AutoConfiguration
@Profile({"dev", "sit", "uat"})
public class Swagger3Config {

  @Bean
  public OpenAPI createRestApi() {

    // 构建API文档信息并返回
    return new OpenAPI(SpecVersion.V30)
        // 将API的元信息设置为包含在json ResourceListing响应中
        .info(apiInfo())
        // API安全配置组件
        .components(this.securityComponents())
        .addSecurityItem(new SecurityRequirement().addList("bearerAuth"))
        .addSecurityItem(new SecurityRequirement().addList("apiKeyAuth"));
  }

  /**
   * API安全配置组件
   *
   * @return API安全配置组件
   */
  private Components securityComponents() {

    return new Components()
        // 用户级别安全认证
        .addSecuritySchemes("bearerAuth", new SecurityScheme()
            .type(SecurityScheme.Type.HTTP)
            .scheme("bearer")
            .bearerFormat("JWT"))
        // 应用级别安全认证
        .addSecuritySchemes("apiKeyAuth", new SecurityScheme()
            .type(SecurityScheme.Type.APIKEY)
            .in(SecurityScheme.In.HEADER)
            .name("Authorization"));
  }

  /**
   * API基础信息设定
   *
   * @return API基础信息
   */
  private Info apiInfo() {

    // 联系信息
    final Contact contact = new Contact();
    contact.setName("sgole001");
    contact.setEmail("sgole001@gmail.com");

    // 构建API信息并返回
    return new Info()
        // 文档主题
        .title("Oceanstars接口文档")
        // 文档描述
        .description("更多请咨询服务开发者sgole001@gmail.com。")
        // 联系信息
        .contact(contact)
        // 版本
        .version("1.0");
  }
}
