package oceanstars.ecommerce.user.domain.permission.entity;

import java.io.Serial;
import oceanstars.ecommerce.common.domain.entity.BaseEntityIdentifier;
import oceanstars.ecommerce.common.exception.BusinessException;

/**
 * 权限资源操作实体唯一标识符生成器
 *
 * @author Clover
 * @version 1.0.0
 * @since 2024/1/9 11:18
 */
public class PermissionBehaviorIdentifier extends BaseEntityIdentifier<String> {

  @Serial
  private static final long serialVersionUID = 5291440877762399595L;

  /**
   * 权限实体唯一标识符
   */
  private final Permission permission;

  /**
   * 权限操作资源实体唯一标识符
   */
  private final Long resource;

  public PermissionBehaviorIdentifier(Permission permission, Long resource) {
    super(permission.getDelegator().getId() + "_" + resource);
    this.permission = permission;
    this.resource = resource;
  }

  public Permission getPermission() {
    return permission;
  }

  public Long getResource() {
    return resource;
  }

  @Override
  public String generateIdentifier() {
    // 权限资源操作实体唯一标识符非自动生成，需初始化关联资源实体数据物理PK
    throw new BusinessException("权限资源操作实体唯一标识符非自动生成，权限ID和资源ID组合唯一");
  }
}
