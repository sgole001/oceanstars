package oceanstars.ecommerce.user.domain.role.entity;

import oceanstars.ecommerce.common.domain.entity.BaseEntityIdentifier;

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
   * ID前缀
   */
  private static final String ID_PREFIX = "ROLE-";

  /**
   * 构造函数：初始化成员变量
   *
   * @param name 角色名
   */
  public RoleIdentifier(final String name) {
    super(null);
    this.name = name;
  }

  @Override
  public String generateIdentifier() {

    // 获取UUID
    final String[] uuid = super.uuid(this.name);

    return ID_PREFIX + uuid[0];
  }
}
