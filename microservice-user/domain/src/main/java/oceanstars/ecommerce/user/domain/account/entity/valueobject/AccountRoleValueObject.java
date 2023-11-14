package oceanstars.ecommerce.user.domain.account.entity.valueobject;

import oceanstars.ecommerce.common.domain.ValueObject;

/**
 * 账号角色值对象
 *
 * @author Clover
 * @version 1.0.0
 * @since 2022/1/6 11:34 AM
 */
public final class AccountRoleValueObject extends ValueObject {

  /**
   * 账号ID
   */
  private final Long account;

  /**
   * 角色ID
   */
  private final Long role;

  /**
   * 构造函数：根据构建器初始化成员变量
   *
   * @param builder 构建器
   */
  private AccountRoleValueObject(Builder builder) {
    account = builder.account;
    role = builder.role;
  }

  /**
   * 创建账号角色值对象构建器
   *
   * @return 账号角色值对象构建器
   */
  public static Builder newBuilder() {
    return new Builder();
  }

  public Long getAccount() {
    return account;
  }

  public Long getRole() {
    return role;
  }

  /**
   * <此类的功能说明>
   *
   * @author Clover
   * @version 1.0.0
   * @since 2022/1/6 11:35 AM
   */
  protected static final class Builder {

    private Long account;
    private Long role;

    public Builder account(Long val) {
      account = val;
      return this;
    }

    public Builder role(Long val) {
      role = val;
      return this;
    }

    public AccountRoleValueObject build() {
      return new AccountRoleValueObject(this);
    }
  }
}
