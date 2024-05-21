package oceanstars.ecommerce.user.domain.permission.entity;

import java.io.Serial;
import oceanstars.ecommerce.common.domain.entity.BaseEntityIdentifier;
import oceanstars.ecommerce.common.exception.BusinessException;
import oceanstars.ecommerce.user.constant.enums.UserEnums.PermissionType;

/**
 * 权限实体唯一标识符生成器
 *
 * @author Clover
 * @version 1.0.0
 * @since 2022/1/5 11:49 AM
 */
public final class PermissionIdentifier extends BaseEntityIdentifier<String> {

  @Serial
  private static final long serialVersionUID = -4294197801262569230L;
  
  /**
   * 权限名
   */
  private final String name;

  /**
   * 权限类型
   */
  private final PermissionType type;

  /**
   * 构造函数；初始化成员变量
   *
   * @param name 权限名
   * @param type 权限类型
   */
  public PermissionIdentifier(final String name, final PermissionType type) {
    super(type.value() + "#" + name);
    this.name = name;
    this.type = type;
  }

  public String getName() {
    return name;
  }

  public PermissionType getType() {
    return type;
  }

  @Override
  public String generateIdentifier() {
    throw new BusinessException("权限唯一标识符非自动生成，权限名称和权限类型组合唯一");
  }
}
