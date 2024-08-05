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
  @Schema(description = "账号域")
  private Integer domain;

  /**
   * 账号名称（注册后无法修改）
   */
  @Schema(description = "账号名称（注册后无法修改）")
  private String userName;

  /**
   * 账号访问方式
   */
  @Schema(description = "账号访问认证方式（邮箱 | 手机 | 第三方授权UID）")
  private String access;

  /**
   * 账号访问方式类型
   */
  @Schema(description = "账号访问认证方式类型 ("
      + "0:Email, "
      + "1:Mobile, "
      + "2:External Wechat, "
      + "3:External Alipay, "
      + "4:External Douyin, "
      + "5:External QQ, "
      + "6:External Weibo, "
      + "7:External Facebook, "
      + "8:External Google, "
      + "9:External Twitter, "
      + "10:External Apple, "
      + "11:External LinkedIn)")
  private Integer accessType;

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

  public Integer getDomain() {
    return domain;
  }

  public void setDomain(Integer domain) {
    this.domain = domain;
  }

  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  public String getAccess() {
    return access;
  }

  public void setAccess(String access) {
    this.access = access;
  }

  public Integer getAccessType() {
    return accessType;
  }

  public void setAccessType(Integer accessType) {
    this.accessType = accessType;
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
