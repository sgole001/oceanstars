package oceanstars.ecommerce.user.configuration.swagger;

import io.swagger.annotations.ApiOperation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.env.Profiles;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.servlet.mvc.method.RequestMappingInfoHandlerMapping;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.Contact;
import springfox.documentation.service.SecurityScheme;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.spring.web.plugins.WebFluxRequestHandlerProvider;
import springfox.documentation.spring.web.plugins.WebMvcRequestHandlerProvider;

/**
 * 用户服务Swagger3.0配置
 *
 * @author Clover
 * @version 1.0.0
 * @since 2022/1/17 2:49 PM
 */
@Configuration(proxyBeanMethods = false)
public class Swagger3Config {

  @Bean
  public Docket createRestApi(Environment env) {

    // 根据环境确定是否开启Swagger
    final Profiles profile = Profiles.of("dev", "sit", "uat");
    boolean triggerSwagger = env.acceptsProfiles(profile);

    // 构建API文档信息并返回
    return new Docket(DocumentationType.OAS_30)
        // 将API的元信息设置为包含在json ResourceListing响应中
        .apiInfo(apiInfo())
        // 是否开启Swagger
        .enable(triggerSwagger)
        // 选择哪些接口作为Swagger的DOC发布
        .select()
        // 选择条件1
        .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
        // 选择条件2
        .paths(PathSelectors.any())
        // 构建
        .build()
        // API授权认证Scheme设定
        .securitySchemes(this.securitySchemes());
  }

  /**
   * API授权认证Scheme设定
   *
   * @return API授权认证Scheme列表
   */
  private List<SecurityScheme> securitySchemes() {

    final List<SecurityScheme> securitySchemes = new ArrayList<>();

    // 应用级别安全认证
    final ApiKey apiKey4Authorization = new ApiKey("Authorization", "Authorization", "header");
    securitySchemes.add(apiKey4Authorization);
    // 用户级别安全认证
    final ApiKey apiKey4Jwt = new ApiKey("jwt", "jwt", "header");
    securitySchemes.add(apiKey4Jwt);

    return securitySchemes;
  }

  /**
   * API基础信息设定
   *
   * @return API基础信息
   */
  private ApiInfo apiInfo() {

    // 构建API信息并返回
    return new ApiInfoBuilder()
        // 文档主题
        .title("用户服务接口文档")
        // 文档描述
        .description("更多请咨询服务开发者liyi.ling@accenture.com。")
        // 联系信息
        .contact(new Contact("sgole001。", "", "sgole001@gmail.com"))
        // 版本
        .version("1.0")
        // 构建
        .build();
  }

  @Bean
  public static BeanPostProcessor springfoxHandlerProviderBeanPostProcessor() {

    // springboot升级到2.6.x及以后的版本，应对swagger3.0.0版本空指针报错
    return new BeanPostProcessor() {

      @Override
      public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (bean instanceof WebMvcRequestHandlerProvider || bean instanceof WebFluxRequestHandlerProvider) {
          this.customizeSpringfoxHandlerMappings(this.getHandlerMappings(bean));
        }
        return bean;
      }

      private <T extends RequestMappingInfoHandlerMapping> void customizeSpringfoxHandlerMappings(List<T> mappings) {
        final List<T> copy = mappings.stream().filter(mapping -> mapping.getPatternParser() == null).toList();
        mappings.clear();
        mappings.addAll(copy);
      }

      @SuppressWarnings("unchecked")
      private List<RequestMappingInfoHandlerMapping> getHandlerMappings(Object bean) {
        try {
          final Field field = ReflectionUtils.findField(bean.getClass(), "handlerMappings");
          field.setAccessible(true);
          return (List<RequestMappingInfoHandlerMapping>) field.get(bean);
        } catch (IllegalArgumentException | IllegalAccessException e) {
          throw new IllegalStateException(e);
        }
      }
    };
  }
}
