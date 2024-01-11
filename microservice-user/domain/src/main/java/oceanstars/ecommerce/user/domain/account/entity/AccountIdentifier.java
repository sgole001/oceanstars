package oceanstars.ecommerce.user.domain.account.entity;

import oceanstars.ecommerce.common.constant.CommonConstant;
import oceanstars.ecommerce.common.domain.BaseEntityIdentifier;
import oceanstars.ecommerce.user.constant.enums.UserEnums.AccountRegisterMeans;
import oceanstars.ecommerce.user.constant.enums.UserEnums.AccountRegisterSource;

/**
 * 账号实体唯一识别符生成器
 *
 * @author Clover
 * @version 1.0.0
 * @since 2022/1/6 10:55 AM
 */
public final class AccountIdentifier extends BaseEntityIdentifier<String> {

  /**
   * 账号注册源
   */
  private final AccountRegisterSource source;

  /**
   * 账号注册方式
   */
  private final AccountRegisterMeans means;

  /**
   * 邮箱
   */
  private final String email;

  /**
   * 手机
   */
  private final String mobile;

  /**
   * 第三方外部UID
   */
  private final String externalId;

  /**
   * ID前缀
   */
  private static final String ID_PREFIX = "ACC-";

  /**
   * 构造函数：初始化成员变量
   *
   * @param email  账号邮箱
   * @param source 账号注册源
   */
  public AccountIdentifier(String email, String mobile, String externalId, AccountRegisterSource source, AccountRegisterMeans means) {
    super(null);
    this.email = email;
    this.mobile = mobile;
    this.externalId = externalId;
    this.source = source;
    this.means = means;
  }

  @Override
  public String generateIdentifier() {

    // ID生成规则相关元素拼接
    String elements = this.source.value() + CommonConstant.SEPARATOR_HYPHEN + this.means.value();

    // 邮件注册账号
    if (AccountRegisterMeans.EMAIL.equals(this.means)) {
      elements += CommonConstant.SEPARATOR_HYPHEN + this.email;
    }
    // 手机注册账号
    else if (AccountRegisterMeans.MOBILE.equals(this.means)) {
      elements += CommonConstant.SEPARATOR_HYPHEN + this.mobile;
    }
    // 其他第三方授权注册账号
    else {
      elements += CommonConstant.SEPARATOR_HYPHEN + this.externalId;
    }

    // 获取UUID
    final String[] uuid = super.uuid(elements);

    return ID_PREFIX + uuid[0];
  }
}
