package oceanstars.ecommerce.user.repository.role;

import java.util.List;
import oceanstars.ecommerce.user.repository.generate.tables.pojos.UserRolePojo;

/**
 * 角色聚合仓储接口
 *
 * @author Clover
 * @version 1.0.0
 * @since 2024/1/10 15:39
 */
public interface IRoleRepository {

  /**
   * 创建角色
   *
   * @param rolePojo 角色数据
   */
  void createRole(UserRolePojo rolePojo);

  /**
   * 更新角色
   *
   * @param rolePojo 角色数据
   */
  void updateRole(UserRolePojo rolePojo);

  /**
   * 禁用角色
   *
   * @param ids 禁用角色ID(角色实体代理唯一识别ID)列表
   */
  void disableRole(final List<Long> ids);

  /**
   * 赋予角色权限
   *
   * @param roleId        角色ID
   * @param permissionIds 权限ID列表
   */
  void assignRolePermission(final Long roleId, final List<Long> permissionIds);
}
