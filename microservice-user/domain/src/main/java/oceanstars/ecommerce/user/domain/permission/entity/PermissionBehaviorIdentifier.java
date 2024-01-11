package oceanstars.ecommerce.user.domain.permission.entity;

import oceanstars.ecommerce.common.domain.BaseEntityIdentifier;
import oceanstars.ecommerce.common.exception.BusinessException;

/**
 * 权限资源操作实体唯一标识符生成器
 *
 * @author Clover
 * @version 1.0.0
 * @since 2024/1/9 11:18
 */
public class PermissionBehaviorIdentifier extends BaseEntityIdentifier<Long> {

  public PermissionBehaviorIdentifier(Long resource) {
    super(resource);
  }

  @Override
  public Long generateIdentifier() {
    // 权限资源操作实体唯一标识符非自动生成，需初始化关联资源实体数据物理PK
    throw new BusinessException("权限资源操作实体唯一标识符非自动生成，需初始化关联资源实体数据物理PK");
  }
}
