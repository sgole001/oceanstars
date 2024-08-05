package oceanstars.ecommerce.iam.api.rest.v1.request.authn;

import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serial;

/**
 * 登出接口请求参数
 *
 * @author Clover
 * @version 1.0.0
 * @since 2024/7/4 19:47
 */
@Schema(name = "LogoutRequestMessage", description = "登出接口请求参数")
public class LogoutRequestMessage extends AuthnRequestMessage {

  @Serial
  private static final long serialVersionUID = 1556688817294607646L;

  /**
   * 账号ID
   */
  @Schema(description = "账号ID")
  private Long accountId;

  public Long getAccountId() {
    return accountId;
  }

  public void setAccountId(Long accountId) {
    this.accountId = accountId;
  }
}
