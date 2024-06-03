package oceanstars.ecommerce.user.api.rest.v1.request.account;

import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serial;
import java.time.LocalDate;
import oceanstars.ecommerce.common.restful.RestRequestMessage;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * 创建账号简况接口请求参数
 *
 * @author Clover
 * @version 1.0.0
 * @since 2024/5/28 15:33
 */
@Schema(name = "CreateAccountProfileRequestMessage", description = "创建账号简况接口请求参数")
public class CreateAccountProfileRequestMessage extends RestRequestMessage {

  @Serial
  private static final long serialVersionUID = 7317242281935994547L;

  /**
   * 账号ID
   */
  private Long account;

  /**
   * 姓
   */
  @Schema(description = "姓")
  private String firstName;

  /**
   * 名
   */
  @Schema(description = "名")
  private String lastName;

  /**
   * 昵称
   */
  @Schema(description = "昵称")
  private String nickName;

  /**
   * 用户头像(路径)
   */
  @Schema(description = "用户头像(路径)")
  private String avatar;

  /**
   * 性别 0 : male; 1 : female
   */
  @Schema(description = "性别 0 : male; 1 : female")
  private Integer gender;

  /**
   * 生日
   */
  @Schema(description = "生日：pattern = 'yyyy-MM-dd'")
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  private LocalDate birthday;

  public Long getAccount() {
    return account;
  }

  public void setAccount(Long account) {
    this.account = account;
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

  public Integer getGender() {
    return gender;
  }

  public void setGender(Integer gender) {
    this.gender = gender;
  }

  public LocalDate getBirthday() {
    return birthday;
  }

  public void setBirthday(LocalDate birthday) {
    this.birthday = birthday;
  }
}
