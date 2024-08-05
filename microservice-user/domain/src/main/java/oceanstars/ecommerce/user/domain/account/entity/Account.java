package oceanstars.ecommerce.user.domain.account.entity;

import java.util.List;
import java.util.Set;
import oceanstars.ecommerce.common.domain.entity.AggregateRoot;
import oceanstars.ecommerce.user.constant.enums.UserEnums.AccountStatus;
import oceanstars.ecommerce.user.domain.account.entity.valueobject.AccountActivityLog;

/**
 * 账号实体：聚合根
 *
 * @author Clover
 * @version 1.0.0
 * @since 2022/1/6 10:56 AM
 */
public final class Account extends AggregateRoot<AccountIdentifier> {

  /**
   * 账号密码
   */
  private String password;

  /**
   * 账号状态
   */
  private AccountStatus status;

  /**
   * 账号简况
   */
  private Profile profile;

  /**
   * 账号访问方式列表
   */
  private Set<AccountAccess> accesses;

  /**
   * 账号角色列表
   */
  private Set<Long> roles;

  /**
   * 账号活动日志列表
   */
  private List<AccountActivityLog> activityLogs;

  /**
   * 构造函数：根据构建器初始化成员变量
   *
   * @param builder 构建器
   */
  private Account(Builder builder) {
    super(new AccountIdentifier(builder.name, builder.domain));
    password = builder.password;
    status = builder.status;
    accesses = builder.accesses;
    profile = builder.profile;
    roles = builder.roles;
    activityLogs = builder.activityLogs;
  }

  /**
   * 创建账号实体构建器
   *
   * @param name   账号名称
   * @param domain 账号域
   * @return 账号实体构建器
   */
  public static Builder newBuilder(final String name, final Integer domain) {
    return new Builder(name, domain);
  }

  public String getPassword() {
    return password;
  }

  public AccountStatus getStatus() {
    return status;
  }

  public Profile getProfile() {
    return profile;
  }

  public Set<Long> getRoles() {
    return roles;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public void setStatus(AccountStatus status) {
    this.status = status;
  }

  public Set<AccountAccess> getAccesses() {
    return accesses;
  }

  public void setAccesses(Set<AccountAccess> accesses) {
    this.accesses = accesses;
  }

  public void setProfile(Profile profile) {
    this.profile = profile;
  }

  public void setRoles(Set<Long> roles) {
    this.roles = roles;
  }

  public List<AccountActivityLog> getActivityLogs() {
    return activityLogs;
  }

  public void setActivityLogs(List<AccountActivityLog> activityLogs) {
    this.activityLogs = activityLogs;
  }

  /**
   * 账号实体构建器
   *
   * @author Clover
   * @version 1.0.0
   * @since 2022/1/6 1:46 PM
   */
  public static final class Builder {

    private final String name;
    private final Integer domain;
    private String password;
    private AccountStatus status;
    private Set<AccountAccess> accesses;
    private Profile profile;
    private Set<Long> roles;
    private List<AccountActivityLog> activityLogs;

    public Builder(String name, Integer domain) {
      this.name = name;
      this.domain = domain;
    }

    public Builder password(String val) {
      password = val;
      return this;
    }

    public Builder status(AccountStatus val) {
      status = val;
      return this;
    }

    public Builder accesses(Set<AccountAccess> val) {
      accesses = val;
      return this;
    }

    public Builder profile(Profile val) {
      profile = val;
      return this;
    }

    public Builder roles(Set<Long> val) {
      roles = val;
      return this;
    }

    public Builder activityLogs(List<AccountActivityLog> val) {
      activityLogs = val;
      return this;
    }

    public Account build() {
      return new Account(this);
    }
  }
}
