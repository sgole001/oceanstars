package oceanstars.ecommerce.user.domain.account.entity;

import java.util.List;
import java.util.Set;
import oceanstars.ecommerce.common.domain.entity.AggregateRoot;
import oceanstars.ecommerce.user.constant.enums.UserEnums.AccountRegisterMeans;
import oceanstars.ecommerce.user.constant.enums.UserEnums.AccountRegisterSource;
import oceanstars.ecommerce.user.constant.enums.UserEnums.AccountStatus;
import oceanstars.ecommerce.user.domain.account.entity.valueobject.AccountActivityLog;
import org.apache.commons.lang3.StringUtils;

/**
 * 账号实体：聚合根
 *
 * @author Clover
 * @version 1.0.0
 * @since 2022/1/6 10:56 AM
 */
public final class Account extends AggregateRoot<AccountIdentifier> {

  /**
   * 账号注册方式
   */
  private final AccountRegisterMeans means;

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
    super(new AccountIdentifier(builder.email, builder.mobile, builder.externalId, builder.source));
    means = builder.means;
    password = builder.password;
    status = builder.status;
    profile = builder.profile;
    roles = builder.roles;
    activityLogs = builder.activityLogs;
  }

  /**
   * 创建账号实体构建器
   *
   * @param source 账号注册源
   * @return 账号实体构建器
   */
  public static Builder newBuilder(final AccountRegisterSource source, final AccountRegisterMeans means) {
    return new Builder(source, means);
  }

  public AccountRegisterMeans getMeans() {
    return means;
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

    private final AccountRegisterSource source;
    private final AccountRegisterMeans means;
    private String email;
    private String mobile;
    private String password;
    private String externalId;
    private AccountStatus status;
    private Profile profile;
    private Set<Long> roles;
    private List<AccountActivityLog> activityLogs;

    public Builder(AccountRegisterSource source, AccountRegisterMeans means) {
      this.source = source;
      this.means = means;
    }

    public Builder email(String val) {
      email = val;
      return this;
    }

    public Builder mobile(String val) {
      mobile = val;
      return this;
    }

    public Builder password(String val) {
      password = val;
      return this;
    }

    public Builder externalId(String val) {
      externalId = val;
      return this;
    }

    public Builder status(AccountStatus val) {
      status = val;
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
      switch (this.means) {
        case EMAIL:
          if (StringUtils.isBlank(this.email)) {
            throw new IllegalArgumentException("email is required");
          }
          break;
        case MOBILE:
          if (StringUtils.isBlank(this.mobile)) {
            throw new IllegalArgumentException("mobile is required");
          }
          break;
        case EXTERNAL:
          if (StringUtils.isBlank(this.externalId)) {
            throw new IllegalArgumentException("externalId is required");
          }
          break;
        default:
          throw new IllegalArgumentException("means is illegal");
      }
      return new Account(this);
    }
  }
}
