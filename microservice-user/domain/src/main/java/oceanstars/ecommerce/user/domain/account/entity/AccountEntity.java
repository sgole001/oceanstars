package oceanstars.ecommerce.user.domain.account.entity;

import java.util.List;
import oceanstars.ecommerce.common.domain.AggregateRoot;
import oceanstars.ecommerce.user.constant.enums.UserEnums.AccountRegisterMeans;
import oceanstars.ecommerce.user.constant.enums.UserEnums.AccountRegisterSource;
import oceanstars.ecommerce.user.constant.enums.UserEnums.AccountStatus;
import oceanstars.ecommerce.user.domain.account.entity.valueobject.AccountRoleValueObject;

/**
 * 账号实体：聚合根
 *
 * @author Clover
 * @version 1.0.0
 * @since 2022/1/6 10:56 AM
 */
public final class AccountEntity extends AggregateRoot<AccountIdentifier> {

  /**
   * 账号注册源
   */
  private final AccountRegisterSource source;

  /**
   * 账号注册方式
   */
  private final AccountRegisterMeans means;

  /**
   * 邮箱（非邮箱注册时可绑定或解绑）
   */
  private String email;

  /**
   * 手机（非手机注册时可绑定或解绑）
   */
  private String mobile;

  /**
   * 账号密码
   */
  private String password;

  /**
   * 第三方外部UID
   */
  private String externalId;

  /**
   * 创建时IP
   */
  private String creatIp;

  /**
   * 最后登陆IP
   */
  private String lastLoginIp;

  /**
   * 登陆次数
   */
  private Integer loginTimes;

  /**
   * 账号状态
   */
  private AccountStatus status;

  /**
   * 账号简况
   */
  private ProfileEntity profile;

  /**
   * 账号角色列表
   */
  private List<AccountRoleValueObject> roles;

  /**
   * 构造函数：根据构建器初始化成员变量
   *
   * @param builder 构建器
   */
  private AccountEntity(Builder builder) {
    super(new AccountIdentifier(builder.email, builder.mobile, builder.externalId, builder.source, builder.means));
    source = builder.source;
    means = builder.means;
    email = builder.email;
    mobile = builder.mobile;
    password = builder.password;
    externalId = builder.externalId;
    creatIp = builder.creatIp;
    lastLoginIp = builder.lastLoginIp;
    loginTimes = builder.loginTimes;
    status = builder.status;
    profile = builder.profile;
    roles = builder.roles;
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

  public AccountRegisterSource getSource() {
    return source;
  }

  public AccountRegisterMeans getMeans() {
    return means;
  }

  public String getEmail() {
    return email;
  }

  public String getMobile() {
    return mobile;
  }

  public String getPassword() {
    return password;
  }

  public String getExternalId() {
    return externalId;
  }

  public String getCreatIp() {
    return creatIp;
  }

  public String getLastLoginIp() {
    return lastLoginIp;
  }

  public Integer getLoginTimes() {
    return loginTimes;
  }

  public AccountStatus getStatus() {
    return status;
  }

  public ProfileEntity getProfile() {
    return profile;
  }

  public List<AccountRoleValueObject> getRoles() {
    return roles;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public void setMobile(String mobile) {
    this.mobile = mobile;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public void setExternalId(String externalId) {
    this.externalId = externalId;
  }

  public void setCreatIp(String creatIp) {
    this.creatIp = creatIp;
  }

  public void setLastLoginIp(String lastLoginIp) {
    this.lastLoginIp = lastLoginIp;
  }

  public void setLoginTimes(Integer loginTimes) {
    this.loginTimes = loginTimes;
  }

  public void setStatus(AccountStatus status) {
    this.status = status;
  }

  public void setProfile(ProfileEntity profile) {
    this.profile = profile;
  }

  public void setRoles(List<AccountRoleValueObject> roles) {
    this.roles = roles;
  }

  /**
   * 账号实体构建器
   *
   * @author Clover
   * @version 1.0.0
   * @since 2022/1/6 1:46 PM
   */
  protected static final class Builder {

    private final AccountRegisterSource source;
    private final AccountRegisterMeans means;
    private String email;
    private String mobile;
    private String password;
    private String externalId;
    private String creatIp;
    private String lastLoginIp;
    private Integer loginTimes;
    private AccountStatus status;
    private ProfileEntity profile;
    private List<AccountRoleValueObject> roles;

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

    public Builder creatIp(String val) {
      creatIp = val;
      return this;
    }

    public Builder lastLoginIp(String val) {
      lastLoginIp = val;
      return this;
    }

    public Builder loginTimes(Integer val) {
      loginTimes = val;
      return this;
    }

    public Builder status(AccountStatus val) {
      status = val;
      return this;
    }

    public Builder profile(ProfileEntity val) {
      profile = val;
      return this;
    }

    public Builder roles(List<AccountRoleValueObject> val) {
      roles = val;
      return this;
    }

    public AccountEntity build() {
      return new AccountEntity(this);
    }
  }
}
