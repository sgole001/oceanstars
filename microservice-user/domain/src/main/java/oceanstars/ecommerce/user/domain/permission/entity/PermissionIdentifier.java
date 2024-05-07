package oceanstars.ecommerce.user.domain.permission.entity;

import oceanstars.ecommerce.common.constant.CommonConstant;
import oceanstars.ecommerce.common.domain.entity.BaseEntityIdentifier;
import oceanstars.ecommerce.user.constant.enums.UserEnums.PermissionType;

/**
 * 权限实体唯一标识符生成器
 *
 * @author Clover
 * @version 1.0.0
 * @since 2022/1/5 11:49 AM
 */
public final class PermissionIdentifier extends BaseEntityIdentifier<String> {

  /**
   * 权限名
   */
  private final String name;

  /**
   * 权限类型
   */
  private final PermissionType type;

  /**
   * ID前缀
   */
  private static final String ID_PREFIX = "PERM-";

  /**
   * 构造函数；初始化成员变量
   *
   * @param name 权限名
   * @param type 权限类型
   */
  public PermissionIdentifier(final String name, final PermissionType type) {
    super(null);
    this.name = name;
    this.type = type;
  }

  @Override
  public String generateIdentifier() {

    // ID生成规则相关元素拼接
    final String elements = this.name + CommonConstant.SEPARATOR_HYPHEN + this.type.value();

    // 获取UUID
    final String[] uuid = super.uuid(elements);

    return ID_PREFIX + uuid[0];
  }
}
