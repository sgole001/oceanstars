package oceanstars.ecommerce.iam.configuration;

import oceanstars.ecommerce.common.security.JwtProperties;
import oceanstars.ecommerce.common.security.TokenProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * <此类的功能说明>
 *
 * @author Clover
 * @version 1.0.0
 * @since 2024/7/24 17:21
 */
@ConfigurationProperties(prefix = "oceanstars.iam.token")
public class IamTokenProperties {

  /**
   * 访问令牌配置
   */
  private JwtProperties access;

  /**
   * 刷新令牌配置
   */
  private TokenProperties refresh;

  public JwtProperties getAccess() {
    return access;
  }

  public void setAccess(JwtProperties access) {
    this.access = access;
  }

  public TokenProperties getRefresh() {
    return refresh;
  }

  public void setRefresh(TokenProperties refresh) {
    this.refresh = refresh;
  }
}
