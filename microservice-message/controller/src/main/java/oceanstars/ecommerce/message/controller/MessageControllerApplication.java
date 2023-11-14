package oceanstars.ecommerce.message.controller;

import com.alicp.jetcache.anno.config.EnableMethodCache;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.oas.annotations.EnableOpenApi;

/**
 * 消息对外服务启动接口
 *
 * @author Clover
 * @version 1.0.0
 * @since 2021/12/16 11:10 下午
 */
@EnableOpenApi
@SpringBootApplication(scanBasePackages = "oceanstars")
@EnableMethodCache(basePackages = "oceanstars.ecommerce")
public class MessageControllerApplication {

  public static void main(String[] args) {
    SpringApplication.run(MessageControllerApplication.class, args);
  }

}
