package oceanstars.ecommerce.user.domain.account.entity;

import oceanstars.ecommerce.common.domain.entity.Entity;

/**
 * 账号访问方式值对象
 *
 * @author Clover
 * @version 1.0.0
 * @since 2024/7/10 17:03
 */
public class AccountAccess extends Entity<AccountAccessIdentifier> {

  /**
   * 账号访问认证方式类型
   */
  private final Integer type;

  /**
   * 是否主要(对于同一类型的访问方式，只能有一个主要的访问方式)
   */
  private final Boolean primary;

  /**
   * 构造函数：根据构建器初始化成员变量
   *
   * @param builder 构建器
   */
  private AccountAccess(Builder builder) {
    super(new AccountAccessIdentifier(builder.account, builder.access));
    type = builder.type;
    primary = builder.primary;
  }

  /**
   * 创建构建器
   *
   * @param account 账号实体
   * @param access  访问方式
   * @param type    访问方式类型
   * @return 构建器
   */
  public static Builder newBuilder(Account account, String access, Integer type) {
    return new Builder(account, access, type);
  }

  public Integer getType() {
    return type;
  }

  public Boolean getPrimary() {
    return primary;
  }

  @Override
  public boolean equals(Object obj) {
    return super.equals(obj);
  }

  @Override
  public int hashCode() {
    return super.hashCode();
  }

  /**
   * <此类的功能说明>
   *
   * @author Clover
   * @version 1.0.0
   * @since 2024/7/10 17:22
   */
  public static final class Builder {

    private final Account account;
    private final String access;
    private final Integer type;
    private Boolean primary;

    public Builder(Account account, String access, Integer type) {
      this.account = account;
      this.access = access;
      this.type = type;
    }

    public Builder primary(Boolean val) {
      primary = val;
      return this;
    }

    public AccountAccess build() {
      return new AccountAccess(this);
    }
  }
}
