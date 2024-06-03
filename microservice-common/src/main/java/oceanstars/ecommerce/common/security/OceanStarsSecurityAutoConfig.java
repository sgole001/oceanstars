package oceanstars.ecommerce.common.security;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * 通用安全配置自动装配
 *
 * @author Clover
 * @version 1.0.0
 * @since 2024/5/31 18:13
 */
@AutoConfiguration
@EnableConfigurationProperties({OceanStarsCryptoProperties.class})
public class OceanStarsSecurityAutoConfig {

  /**
   * 密码加密器
   *
   * @return BCryptPasswordEncoder
   */
  @Bean(name = "passwordEncoder")
  public BCryptPasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  /**
   * AES256加解密算法信息
   *
   * @param cryptoProperties 加解密算法信息Bean
   * @return AES256加解密算法信息
   */
  @Bean(name = "aes256")
  public CryptoBean aes256(final OceanStarsCryptoProperties cryptoProperties) {
    return cryptoProperties.getCryptos().get("aes256");
  }
}
