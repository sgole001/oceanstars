package oceanstars.ecommerce.user.domain.account.repository.condition;

import java.time.LocalDate;
import java.util.Set;
import oceanstars.ecommerce.common.domain.repository.condition.BaseCondition;
import oceanstars.ecommerce.user.constant.enums.UserEnums.AccountStatus;
import oceanstars.ecommerce.user.constant.enums.UserEnums.Gender;

/**
 * 账号查询条件
 *
 * @author Clover
 * @version 1.0.0
 * @since 2024/5/30 15:24
 */
public class AccountFetchCondition extends BaseCondition {

  /**
   * 账号名称
   */
  private final String name;

  /**
   * 账号域
   */
  private final Set<Integer> domain;

  /**
   * 账号访问方式类型
   */
  private final Set<Integer> accessTypes;

  /**
   * 账号访问方式
   */
  private final String access;

  /**
   * 是否主要访问方式
   */
  private final Boolean accessPrimary;

  /**
   * 创建时IP
   */
  private final String createIp;

  /**
   * 最后登陆IP
   */
  private final String lastLoginIp;

  /**
   * 登陆次数(左边界)
   */
  private final Integer loginTimesLeft;

  /**
   * 登陆次数(右边界)
   */
  private final Integer loginTimesRight;

  /**
   * 账号状态
   */
  private final Set<AccountStatus> status;

  /**
   * 姓
   */
  private final String firstName;

  /**
   * 名
   */
  private final String lastName;

  /**
   * 昵称
   */
  private final String nickName;

  /**
   * 性别 0 : male; 1 : female
   */
  private final Gender gender;

  /**
   * 生日（开始时间）
   */
  private final LocalDate birthdayStart;

  /**
   * 生日（结束时间）
   */
  private final LocalDate birthdayEnd;

  /**
   * 账号角色列表
   */
  private final Set<Long> roles;

  /**
   * 构造函数
   *
   * @param builder 构造器
   */
  private AccountFetchCondition(Builder builder) {
    super(builder);
    name = builder.name;
    domain = builder.domain;
    accessTypes = builder.accessTypes;
    access = builder.access;
    accessPrimary = builder.accessPrimary;
    createIp = builder.createIp;
    lastLoginIp = builder.lastLoginIp;
    loginTimesLeft = builder.loginTimesLeft;
    loginTimesRight = builder.loginTimesRight;
    status = builder.status;
    firstName = builder.firstName;
    lastName = builder.lastName;
    nickName = builder.nickName;
    gender = builder.gender;
    birthdayStart = builder.birthdayStart;
    birthdayEnd = builder.birthdayEnd;
    roles = builder.roles;
  }

  /**
   * 创建构建器
   *
   * @return 构建器
   */
  public static Builder newBuilder() {
    return new Builder();
  }

  public String getName() {
    return name;
  }

  public Set<Integer> getAccessTypes() {
    return accessTypes;
  }

  public String getAccess() {
    return access;
  }

  public Boolean getAccessPrimary() {
    return accessPrimary;
  }

  public Set<Integer> getDomain() {
    return domain;
  }

  public String getCreateIp() {
    return createIp;
  }

  public String getLastLoginIp() {
    return lastLoginIp;
  }

  public Integer getLoginTimesLeft() {
    return loginTimesLeft;
  }

  public Integer getLoginTimesRight() {
    return loginTimesRight;
  }

  public Set<AccountStatus> getStatus() {
    return status;
  }

  public String getFirstName() {
    return firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public String getNickName() {
    return nickName;
  }

  public Gender getGender() {
    return gender;
  }

  public LocalDate getBirthdayStart() {
    return birthdayStart;
  }

  public LocalDate getBirthdayEnd() {
    return birthdayEnd;
  }

  public Set<Long> getRoles() {
    return roles;
  }

  /**
   * 构建器
   *
   * @author Clover
   * @version 1.0.0
   * @since 2024/5/30 15:51
   */
  public static final class Builder extends BaseCondition.Builder<AccountFetchCondition, AccountFetchCondition.Builder> {

    private String name;
    private Set<Integer> domain;
    private Set<Integer> accessTypes;
    private String access;
    private Boolean accessPrimary;
    private String createIp;
    private String lastLoginIp;
    private Integer loginTimesLeft;
    private Integer loginTimesRight;
    private Set<AccountStatus> status;
    private String firstName;
    private String lastName;
    private String nickName;
    private Gender gender;
    private LocalDate birthdayStart;
    private LocalDate birthdayEnd;
    private Set<Long> roles;

    public Builder() {
    }

    public Builder name(String val) {
      name = val;
      return this;
    }

    public Builder accessTypes(Set<Integer> val) {
      accessTypes = val;
      return this;
    }

    public Builder domain(Set<Integer> val) {
      domain = val;
      return this;
    }

    public Builder access(String val) {
      access = val;
      return this;
    }

    public Builder accessPrimary(Boolean val) {
      accessPrimary = val;
      return this;
    }

    public Builder createIp(String val) {
      createIp = val;
      return this;
    }

    public Builder lastLoginIp(String val) {
      lastLoginIp = val;
      return this;
    }

    public Builder loginTimesLeft(Integer val) {
      loginTimesLeft = val;
      return this;
    }

    public Builder loginTimesRight(Integer val) {
      loginTimesRight = val;
      return this;
    }

    public Builder status(Set<AccountStatus> val) {
      status = val;
      return this;
    }

    public Builder firstName(String val) {
      firstName = val;
      return this;
    }

    public Builder lastName(String val) {
      lastName = val;
      return this;
    }

    public Builder nickName(String val) {
      nickName = val;
      return this;
    }

    public Builder gender(Gender val) {
      gender = val;
      return this;
    }

    public Builder birthdayStart(LocalDate val) {
      birthdayStart = val;
      return this;
    }

    public Builder birthdayEnd(LocalDate val) {
      birthdayEnd = val;
      return this;
    }

    public Builder roles(Set<Long> val) {
      roles = val;
      return this;
    }

    @Override
    public AccountFetchCondition build() {
      return new AccountFetchCondition(this);
    }
  }
}
