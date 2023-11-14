package oceanstars.ecommerce.infrastructure.shiro.token;

import java.io.Serial;
import org.apache.shiro.authc.AuthenticationToken;

/**
 * ShiroJWT认证用Token
 *
 * @author Clover
 * @version 1.0.0
 * @since 2021/11/4 11:33 上午
 */
public class JwtAuthenticationToken implements AuthenticationToken {

  @Serial
  private static final long serialVersionUID = -1951813409098969170L;
  /**
   * JWT信息
   */
  private final String token;

  /**
   * 构造函数
   *
   * @param token JWT信息
   */
  public JwtAuthenticationToken(final String token) {
    this.token = token;
  }

  @Override
  public Object getPrincipal() {
    return this.token;
  }

  @Override
  public Object getCredentials() {
    return this.token;
  }
}
