package oceanstars.ecommerce.user.domain.role.entity.valueobject;

import oceanstars.ecommerce.common.domain.ValueObject;

/**
 * 角色权限值对象
 *
 * @author Clover
 * @version 1.0.0
 * @since 2022/1/6 12:49 AM
 */
public final class RolePermValueObject extends ValueObject {

  /**
   * 角色ID
   */
  private final Long role;

  /**
   * 权限ID
   */
  private final Long permission;

  /**
   * 构造函数：根据构建器初始化成员变量
   *
   * @param builder 构建器
   */
  private RolePermValueObject(Builder builder) {
    role = builder.role;
    permission = builder.permission;
  }

  /**
   * 创建角色权限值对象构建器
   *
   * @return 角色权限值对象构建器
   */
  public static Builder newBuilder() {
    return new Builder();
  }

  public Long getRole() {
    return role;
  }

  public Long getPermission() {
    return permission;
  }

  /**
   * 角色权限值对象构建器
   *
   * @author Clover
   * @version 1.0.0
   * @since 2022/1/6 12:52 AM
   */
  protected static final class Builder {

    private Long role;
    private Long permission;

    public Builder role(Long val) {
      role = val;
      return this;
    }

    public Builder permission(Long val) {
      permission = val;
      return this;
    }

    public RolePermValueObject build() {
      return new RolePermValueObject(this);
    }
  }
}
