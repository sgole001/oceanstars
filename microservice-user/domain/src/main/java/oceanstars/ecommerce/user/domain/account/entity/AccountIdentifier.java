package oceanstars.ecommerce.user.domain.account.entity;

import java.io.Serial;
import oceanstars.ecommerce.common.domain.entity.BaseEntityIdentifier;
import oceanstars.ecommerce.user.constant.enums.UserEnums.AccountRegisterSource;
import org.springframework.util.StringUtils;

/**
 * 账号实体唯一识别符生成器
 *
 * @author Clover
 * @version 1.0.0
 * @since 2022/1/6 10:55 AM
 */
public final class AccountIdentifier extends BaseEntityIdentifier<String> {

  @Serial
  private static final long serialVersionUID = 7255231152849737890L;

  /**
   * 账号注册源
   */
  private final AccountRegisterSource source;

  /**
   * 邮箱（非邮箱注册时可绑定或解绑）
   */
  private String email;

  /**
   * 手机（非手机注册时可绑定或解绑）
   */
  private String mobile;

  /**
   * 第三方外部UID
   */
  private String externalId;

  /**
   * 构造函数：初始化成员变量
   *
   * @param email  账号邮箱
   * @param source 账号注册源
   */
  public AccountIdentifier(String email, String mobile, String externalId, AccountRegisterSource source) {
    super(null);
    this.email = email;
    this.mobile = mobile;
    this.externalId = externalId;
    this.source = source;
  }

  public AccountRegisterSource getSource() {
    return source;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getMobile() {
    return mobile;
  }

  public void setMobile(String mobile) {
    this.mobile = mobile;
  }

  public String getExternalId() {
    return externalId;
  }

  public void setExternalId(String externalId) {
    this.externalId = externalId;
  }

  @Override
  public String generateIdentifier() {

    if (StringUtils.hasText(this.mobile)) {
      return this.mobile;
    }

    if (StringUtils.hasText(this.email)) {
      return this.email;
    }

    return source.key() + "#" + this.externalId;
  }
}
