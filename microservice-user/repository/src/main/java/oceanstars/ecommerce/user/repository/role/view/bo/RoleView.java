package oceanstars.ecommerce.user.repository.role.view.bo;

import java.util.List;
import oceanstars.ecommerce.user.repository.generate.tables.pojos.UserRolePojo;

/**
 * 角色视图业务对象
 *
 * @author Clover
 * @version 1.0.0
 * @since 2024/5/24 18:12
 */
public class RoleView implements Comparable<RoleView> {

  /**
   * 角色数据
   */
  private UserRolePojo role;

  /**
   * 角色关联关系数据
   */
  private List<UserRolePojo> roles;

  /**
   * 角色关联权限数据
   */
  private List<Long> permissions;

  public UserRolePojo getRole() {
    return role;
  }

  public void setRole(UserRolePojo role) {
    this.role = role;
  }

  public List<UserRolePojo> getRoles() {
    return roles;
  }

  public void setRoles(List<UserRolePojo> roles) {
    this.roles = roles;
  }

  public List<Long> getPermissions() {
    return permissions;
  }

  public void setPermissions(List<Long> permissions) {
    this.permissions = permissions;
  }

  @Override
  public int compareTo(RoleView o) {
    return this.getRole().getCreateAt().compareTo(o.getRole().getCreateAt());
  }
}
