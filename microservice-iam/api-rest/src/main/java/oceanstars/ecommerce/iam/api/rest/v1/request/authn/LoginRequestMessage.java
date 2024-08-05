package oceanstars.ecommerce.iam.api.rest.v1.request.authn;

import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serial;

/**
 * 登录接口请求参数
 *
 * @author Clover
 * @version 1.0.0
 * @since 2024/7/4 19:47
 */
@Schema(name = "LoginRequestMessage", description = "登录接口请求参数")
public class LoginRequestMessage extends AuthnRequestMessage {

  @Serial
  private static final long serialVersionUID = 9100577676032514820L;

  /**
   * 账号(邮箱 | 手机号)
   */
  @Schema(description = "账号(邮箱 | 手机号)")
  private String account;

  /**
   * 密码
   */
  @Schema(description = "密码")
  private String password;

  public String getAccount() {
    return account;
  }

  public void setAccount(String account) {
    this.account = account;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }
}
