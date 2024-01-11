package oceanstars.ecommerce.common.beans;

import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * AES256加解密算法信息
 *
 * @author Clover
 * @version 1.0.0
 * @since 2021/11/3 3:25 下午
 */
@Component
@PropertySource("classpath:/config/crypto.properties")
public class AES256 extends BaseCryptoBean {

  /**
   * 属性Key前缀
   */
  public static final String PREFIX = "aes.256";
}
