package oceanstars.ecommerce.user.domain.permission.repository;

import java.util.List;
import oceanstars.ecommerce.user.domain.permission.entity.PermissionEntity;
import oceanstars.ecommerce.user.domain.permission.entity.PermissionIdentifier;

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
   * @param permissionEntity 预创建权限实体信息
   */
  void createPermission(final PermissionEntity permissionEntity);

  /**
   * 更新权限数据
   *
   * @param permissionEntity 预更新权限实体信息
   */
  void updatePermission(final PermissionEntity permissionEntity);

  /**
   * 禁用权限数据
   *
   * @param identifiers 预禁用权限唯一识别符列表
   */
  void disablePermission(final List<PermissionIdentifier> identifiers);
}
