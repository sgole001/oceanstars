package oceanstars.ecommerce.ecm.controller;

import com.alicp.jetcache.anno.config.EnableMethodCache;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * ECM对外服务启动接口
 *
 * @author Clover
 * @version 1.0.0
 * @since 2022/1/14 11:18 AM
 */
@EnableMethodCache(basePackages = "oceanstars.ecommerce.ecm")
@EnableTransactionManagement
@SpringBootApplication(scanBasePackages = "oceanstars.ecommerce.ecm")
public class EcmControllerApplication {

  public static void main(String[] args) {
    SpringApplication.run(EcmControllerApplication.class, args);
  }

}
