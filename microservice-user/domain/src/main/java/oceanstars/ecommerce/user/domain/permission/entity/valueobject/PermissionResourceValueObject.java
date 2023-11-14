package oceanstars.ecommerce.user.domain.permission.entity.valueobject;

import oceanstars.ecommerce.common.domain.ValueObject;

/**
 * 权限资源值对象
 *
 * @author Clover
 * @version 1.0.0
 * @since 2022/1/5 2:51 PM
 */
public final class PermissionResourceValueObject extends ValueObject {

  /**
   * 权限ID
   */
  private final Long permission;

  /**
   * 资源ID
   */
  private final Long resource;

  /**
   * 资源类型ID
   */
  private final Long resourceType;

  /**
   * 操作ID
   */
  private final Long operation;

  /**
   * 构造函数：根据构建器初始化成员变量
   *
   * @param builder 构建器
   */
  private PermissionResourceValueObject(Builder builder) {
    permission = builder.permission;
    resource = builder.resource;
    resourceType = builder.resourceType;
    operation = builder.operation;
  }

  /**
   * 创建权限资源值对象构建器
   *
   * @return 权限资源值对象构建器
   */
  public static Builder newBuilder() {
    return new Builder();
  }

  public Long getPermission() {
    return permission;
  }

  public Long getResource() {
    return resource;
  }

  public Long getResourceType() {
    return resourceType;
  }

  public Long getOperation() {
    return operation;
  }

  /**
   * 权限资源值对象构建器
   *
   * @author Clover
   * @version 1.0.0
   * @since 2022/1/5 2:57 PM
   */
  protected static final class Builder {

    private Long permission;
    private Long resource;
    private Long resourceType;
    private Long operation;

    public Builder permission(Long val) {
      permission = val;
      return this;
    }

    public Builder resource(Long val) {
      resource = val;
      return this;
    }

    public Builder resourceType(Long val) {
      resourceType = val;
      return this;
    }

    public Builder operation(Long val) {
      operation = val;
      return this;
    }

    public PermissionResourceValueObject build() {
      return new PermissionResourceValueObject(this);
    }
  }
}
