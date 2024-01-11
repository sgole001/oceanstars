package oceanstars.ecommerce.user.repository.permission;

import java.util.List;
import oceanstars.ecommerce.user.repository.generate.tables.pojos.RelPermissionResourcePojo;
import oceanstars.ecommerce.user.repository.generate.tables.pojos.UserPermissionPojo;

/**
 * 权限聚合仓储接口
 *
 * @author Clover
 * @version 1.0.0
 * @since 2022/1/11 12:07 PM
 */
public interface IPermissionRepository {

  /**
   * 创建权限数据
   *
   * @param permissionPojo 创建用权限数据信息
   */
  void createPermission(final UserPermissionPojo permissionPojo);

  /**
   * 更新权限数据
   *
   * @param permissionPojo 更新用权限数据信息
   */
  void updatePermission(final UserPermissionPojo permissionPojo);

  /**
   * 禁用权限数据
   *
   * @param ids 禁用权限ID列表
   */
  void disablePermission(final List<String> ids);

  /**
   * 赋予权限行为（资源操作）
   *
   * @param behaviors 权限行为（资源操作）列表
   */
  void assignPermissionBehavior(final List<RelPermissionResourcePojo> behaviors);
}
