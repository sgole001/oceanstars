package oceanstars.ecommerce.user.repository.account.view.bo;

import java.util.List;
import java.util.Objects;
import oceanstars.ecommerce.user.repository.generate.tables.pojos.UserAccountPojo;
import oceanstars.ecommerce.user.repository.generate.tables.pojos.UserProfilePojo;

/**
 * 账号视图业务对象
 *
 * @author Clover
 * @version 1.0.0
 * @since 2024/5/30 17:13
 */
public class AccountView implements Comparable<AccountView> {

  /**
   * 账号数据
   */
  private UserAccountPojo account;

  /**
   * 账号简况数据
   */
  private UserProfilePojo profile;

  /**
   * 账号角色ID列表
   */
  private List<Long> roles;

  public UserAccountPojo getAccount() {
    return account;
  }

  public void setAccount(UserAccountPojo account) {
    this.account = account;
  }

  public UserProfilePojo getProfile() {
    return profile;
  }

  public void setProfile(UserProfilePojo profile) {
    this.profile = profile;
  }

  public List<Long> getRoles() {
    return roles;
  }

  public void setRoles(List<Long> roles) {
    this.roles = roles;
  }

  @Override
  public int compareTo(AccountView other) {
    return this.getAccount().getCreateAt().compareTo(other.getAccount().getCreateAt());
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    AccountView that = (AccountView) o;

    return this.account.getId().equals(that.account.getId());
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(account);
  }
}
