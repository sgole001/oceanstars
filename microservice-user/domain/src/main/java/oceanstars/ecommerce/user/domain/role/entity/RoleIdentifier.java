package oceanstars.ecommerce.user.domain.role.entity;

import oceanstars.ecommerce.common.domain.entity.BaseEntityIdentifier;
import oceanstars.ecommerce.common.exception.BusinessException;

/**
 * 角色实体唯一标识符生成器
 *
 * @author Clover
 * @version 1.0.0
 * @since 2022/1/5 9:49 AM
 */
public final class RoleIdentifier extends BaseEntityIdentifier<String> {

  /**
   * 角色名
   */
  private final String name;

  /**
   * 构造函数：初始化成员变量
   *
   * @param name 角色名
   */
  public RoleIdentifier(final String name) {
    super(name);
    this.name = name;
  }

  public String getName() {
    return name;
  }

  @Override
  public String generateIdentifier() {
    throw new BusinessException("角色唯一标识符非自动生成，角色名称即为唯一标识符");
  }
}
