package oceanstars.ecommerce.iam.api.rest.v1.request.authn;

import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serial;

/**
 * 验证接口请求参数
 *
 * @author Clover
 * @version 1.0.0
 * @since 2024/7/5 17:04
 */
@Schema(name = "VerifyRequestMessage", description = "验证接口请求参数")
public class VerifyRequestMessage extends AuthnRequestMessage {

  @Serial
  private static final long serialVersionUID = 4146015415901843498L;

  /**
   * MFA验证令牌
   */
  @Schema(description = "MFA验证令牌")
  private String token;

  /**
   * MFA验证一次性秘钥(One-Time Password)
   */
  @Schema(description = "MFA验证一次性秘钥(One-Time Password)")
  private String otp;

  public String getToken() {
    return token;
  }

  public void setToken(String token) {
    this.token = token;
  }

  public String getOtp() {
    return otp;
  }

  public void setOtp(String otp) {
    this.otp = otp;
  }
}
