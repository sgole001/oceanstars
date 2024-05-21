package oceanstars.ecommerce.user.domain.account.entity;

import oceanstars.ecommerce.common.domain.entity.Entity;
import oceanstars.ecommerce.user.constant.enums.UserEnums.Gender;

/**
 * 账号简况实体
 *
 * @author Clover
 * @version 1.0.0
 * @since 2022/1/6 11:56 AM
 */
public final class Profile extends Entity<ProfileIdentifier> {

  /**
   * 姓
   */
  private String firstName;

  /**
   * 名
   */
  private String lastName;

  /**
   * 昵称
   */
  private String nickName;

  /**
   * 用户头像(路径)
   */
  private String avatar;

  /**
   * 性别 0 : male; 1 : female
   */
  private Gender gender;

  /**
   * 构造函数：根据构建器初始化成员变量
   *
   * @param builder 构建器
   */
  private Profile(Builder builder) {
    super(new ProfileIdentifier(builder.account));
    firstName = builder.firstName;
    lastName = builder.lastName;
    nickName = builder.nickName;
    avatar = builder.avatar;
    gender = builder.gender;
  }

  /**
   * 创建账号简况实体构建器
   *
   * @param account 账号ID
   * @return 账号简况实体构建器
   */
  public static Builder newBuilder(final Long account) {
    return new Builder(account);
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public String getNickName() {
    return nickName;
  }

  public void setNickName(String nickName) {
    this.nickName = nickName;
  }

  public String getAvatar() {
    return avatar;
  }

  public void setAvatar(String avatar) {
    this.avatar = avatar;
  }

  public Gender getGender() {
    return gender;
  }

  public void setGender(Gender gender) {
    this.gender = gender;
  }

  /**
   * 账号简况实体构建器
   *
   * @author Clover
   * @version 1.0.0
   * @since 2022/1/6 12:11 PM
   */
  public static final class Builder {

    private final Long account;
    private String firstName;
    private String lastName;
    private String nickName;
    private String avatar;
    private Gender gender;

    public Builder(final Long account) {
      this.account = account;
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

    public Builder avatar(String val) {
      avatar = val;
      return this;
    }

    public Builder gender(Gender val) {
      gender = val;
      return this;
    }

    public Profile build() {
      return new Profile(this);
    }
  }
}
