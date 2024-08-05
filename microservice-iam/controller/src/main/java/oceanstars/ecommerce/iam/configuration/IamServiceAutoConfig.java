package oceanstars.ecommerce.iam.configuration;

import oceanstars.ecommerce.common.security.JwtProperties;
import oceanstars.ecommerce.common.security.TokenProperties;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

/**
 * IAM服务自动配置
 *
 * @author Clover
 * @version 1.0.0
 * @since 2024/7/22 14:55
 */
@AutoConfiguration
@EnableConfigurationProperties({IamTokenProperties.class})
public class IamServiceAutoConfig {

  /**
   * 获取访问令牌配置
   *
   * @param tokenProperties IAM令牌配置
   * @return 访问令牌配置
   */
  @Bean
  public JwtProperties accessTokenProp(IamTokenProperties tokenProperties) {
    return tokenProperties.getAccess();
  }

  /**
   * 获取刷新令牌配置
   *
   * @param tokenProperties IAM令牌配置
   * @return 刷新令牌配置
   */
  @Bean
  public TokenProperties refreshTokenProp(IamTokenProperties tokenProperties) {
    return tokenProperties.getRefresh();
  }

}
