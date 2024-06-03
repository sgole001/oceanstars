package oceanstars.ecommerce.user.api.rest.v1.request.account;

import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serial;
import oceanstars.ecommerce.common.restful.RestRequestMessage;

/**
 * 创建账号接口请求参数
 *
 * @author Clover
 * @version 1.0.0
 * @since 2024/5/28 14:39
 */
@Schema(name = "CreateAccountRequestMessage", description = "创建账号接口请求参数")
public class CreateAccountRequestMessage extends RestRequestMessage {

  @Serial
  private static final long serialVersionUID = -6981463472879380888L;

  /**
   * 账号注册源
   */
  @Schema(description = "账号注册源")
  private Integer source;

  /**
   * 账号注册方式（手机 | 邮箱 | 第三方授权）
   */
  @Schema(description = "账号注册方式（手机 | 邮箱 | 第三方授权）")
  private Integer mean;

  /**
   * 账号名
   */
  @Schema(description = "账号名")
  private String account;

  /**
   * 账号密码
   */
  @Schema(description = "账号密码")
  private String password;

  /**
   * 验证码（图灵测试：完全自动区分计算机和人类）
   */
  @Schema(description = "验证码（图灵测试：完全自动区分计算机和人类）")
  private String captcha;

  /**
   * 验证码（一次性：One-Time Password）
   */
  @Schema(description = "验证码（一次性：One-Time Password）")
  private String otp;

  public Integer getSource() {
    return source;
  }

  public void setSource(Integer source) {
    this.source = source;
  }

  public Integer getMean() {
    return mean;
  }

  public void setMean(Integer mean) {
    this.mean = mean;
  }

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

  public String getCaptcha() {
    return captcha;
  }

  public void setCaptcha(String captcha) {
    this.captcha = captcha;
  }

  public String getOtp() {
    return otp;
  }

  public void setOtp(String otp) {
    this.otp = otp;
  }
}
