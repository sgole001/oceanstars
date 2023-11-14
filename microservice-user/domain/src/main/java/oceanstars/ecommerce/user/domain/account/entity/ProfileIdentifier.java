package oceanstars.ecommerce.user.domain.account.entity;

import oceanstars.ecommerce.common.domain.BaseEntityIdentifier;

/**
 * 账号简况实体唯一识别符生成器
 *
 * @author Clover
 * @version 1.0.0
 * @since 2022/1/6 11:49 AM
 */
public final class ProfileIdentifier extends BaseEntityIdentifier {

  /**
   * 账号ID
   */
  private final Long account;

  /**
   * 构造函数：初始化成员变量
   *
   * @param account 账号ID
   */
  public ProfileIdentifier(final Long account) {
    this.account = account;
  }

  @Override
  public String generateIdentifier() {
    return String.valueOf(account);
  }
}
