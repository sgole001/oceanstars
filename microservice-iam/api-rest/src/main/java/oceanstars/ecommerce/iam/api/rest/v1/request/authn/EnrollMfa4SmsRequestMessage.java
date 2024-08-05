package oceanstars.ecommerce.iam.api.rest.v1.request.authn;

import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serial;

/**
 * MFA：短信验证码（SMS OTP）接口请求参数
 *
 * @author Clover
 * @version 1.0.0
 * @since 2024/7/4 19:43
 */
@Schema(name = "EnrollMfa4SmsRequestMessage", description = "MFA：短信验证码（SMS OTP）接口请求参数")
public class EnrollMfa4SmsRequestMessage extends AuthnRequestMessage {

  @Serial
  private static final long serialVersionUID = -6429938984171643125L;
}
