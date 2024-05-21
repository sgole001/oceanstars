package oceanstars.ecommerce.user.repository.permission.view.bo;

import java.util.List;
import oceanstars.ecommerce.user.repository.generate.tables.pojos.UserPermissionBehaviorPojo;
import oceanstars.ecommerce.user.repository.generate.tables.pojos.UserPermissionPojo;

/**
 * 权限视图业务对象
 *
 * @author Clover
 * @version 1.0.0
 * @since 2024/5/13 18:48
 */
public class PermissionView {

  /**
   * 权限数据
   */
  private UserPermissionPojo permission;

  /**
   * 权限行为数据
   */
  private List<UserPermissionBehaviorPojo> behaviors;

  public UserPermissionPojo getPermission() {
    return permission;
  }

  public void setPermission(UserPermissionPojo permission) {
    this.permission = permission;
  }

  public List<UserPermissionBehaviorPojo> getBehaviors() {
    return behaviors;
  }

  public void setBehaviors(List<UserPermissionBehaviorPojo> behaviors) {
    this.behaviors = behaviors;
  }
}
