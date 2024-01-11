package oceanstars.ecommerce.user.controller;

import com.alicp.jetcache.anno.config.EnableMethodCache;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 用户对外服务启动接口
 *
 * @author Clover
 * @version 1.0.0
 * @since 2022/1/14 11:18 AM
 */
@EnableMethodCache(basePackages = "oceanstars.ecommerce")
@SpringBootApplication(scanBasePackages = "oceanstars")
public class UserControllerApplication {

  public static void main(String[] args) {
    SpringApplication.run(UserControllerApplication.class, args);
  }

}
