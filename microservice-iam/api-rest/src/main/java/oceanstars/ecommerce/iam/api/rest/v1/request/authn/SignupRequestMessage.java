package oceanstars.ecommerce.iam.api.rest.v1.request.authn;

import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serial;

/**
 * 注册接口请求参数
 *
 * @author Clover
 * @version 1.0.0
 * @since 2024/7/4 19:47
 */
@Schema(name = "RegisterRequestMessage", description = "注册接口请求参数")
public class SignupRequestMessage extends AuthnRequestMessage {

  @Serial
  private static final long serialVersionUID = 8455966159829485086L;

  /**
   * 账号访问认证方式
   */
  @Schema(description = "账号访问认证方式(邮箱 | 手机号 | 第三方授权)")
  private String access;

  /**
   * 账号访问认证方式类型
   * <p>0:Email, </p>
   * <p>1:Mobile, </p>
   * <p>2:External WeChat, </p>
   * <p>3:External Alipay, </p>
   * <p>4:External Douyin, </p>
   * <p>5:External QQ, </p>
   * <p>6:External Weibo, </p>
   * <p>7:External Facebook, </p>
   * <p>8:External Google, </p>
   * <p>9:External Twitter, </p>
   * <p>10:External Apple, </p>
   * <p>11:External LinkedIn </p>
   */
  private Integer accessType;

  /**
   * 密码
   */
  @Schema(description = "密码")
  private String password;

  /**
   * 用户名
   */
  @Schema(description = "用户名")
  private String userName;

  /**
   * 是否同意
   */
  @Schema(description = "是否同意协议和隐私政策")
  private Boolean agree;

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

  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  public Boolean getAgree() {
    return agree;
  }

  public void setAgree(Boolean agree) {
    this.agree = agree;
  }
}
