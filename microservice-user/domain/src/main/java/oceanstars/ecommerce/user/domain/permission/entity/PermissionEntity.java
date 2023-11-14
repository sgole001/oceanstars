package oceanstars.ecommerce.user.domain.permission.entity;

import java.util.List;
import java.util.Map;
import oceanstars.ecommerce.common.domain.AggregateRoot;
import oceanstars.ecommerce.user.constant.enums.UserEnums.PermissionType;
import oceanstars.ecommerce.user.domain.permission.entity.valueobject.PermissionResourceValueObject;

/**
 * 权限实体：聚合根
 *
 * @author Clover
 * @version 1.0.0
 * @since 2022/1/5 11:49 AM
 */
public final class PermissionEntity extends AggregateRoot<PermissionIdentifier> {

  /**
   * 权限名
   */
  private final String name;

  /**
   * 权限类型
   */
  private final PermissionType type;

  /**
   * 权限描述
   */
  private String desc;

  /**
   * 权限逻辑有效标志位
   */
  private Boolean enabled;

  /**
   * 权限资源映射
   */
  private Map<Long, List<PermissionResourceValueObject>> permissionResources;

  /**
   * 构造函数：根据构建器初始化成员变量
   *
   * @param builder 构建器
   */
  private PermissionEntity(Builder builder) {
    super(new PermissionIdentifier(builder.name, builder.type));
    name = builder.name;
    type = builder.type;
    permissionResources = builder.permissionResources;
    desc = builder.desc;
    enabled = builder.enabled;
  }

  /**
   * 创建权限实体构建器
   *
   * @param name 权限名
   * @param type 权限类型
   * @return 权限实体构建器
   */
  public static Builder newBuilder(final String name, final PermissionType type) {
    return new Builder(name, type);
  }

  public String getName() {
    return name;
  }

  public PermissionType getType() {
    return type;
  }

  public String getDesc() {
    return desc;
  }

  public Boolean getEnabled() {
    return enabled;
  }

  public Map<Long, List<PermissionResourceValueObject>> getPermissionResources() {
    return permissionResources;
  }

  public void setDesc(String desc) {
    this.desc = desc;
  }

  public void setEnabled(Boolean enabled) {
    this.enabled = enabled;
  }

  public void setPermissionResources(Map<Long, List<PermissionResourceValueObject>> permissionResources) {
    this.permissionResources = permissionResources;
  }

  /**
   * 权限实体构建器
   *
   * @author Clover
   * @version 1.0.0
   * @since 2022/1/6 12:05 AM
   */
  public static final class Builder {

    private final String name;
    private final PermissionType type;
    private String desc;
    private Boolean enabled;
    private Map<Long, List<PermissionResourceValueObject>> permissionResources;

    public Builder(String name, PermissionType type) {
      this.name = name;
      this.type = type;
    }

    public Builder desc(String val) {
      desc = val;
      return this;
    }

    public Builder enabled(Boolean val) {
      enabled = val;
      return this;
    }

    public Builder permissionResources(Map<Long, List<PermissionResourceValueObject>> val) {
      permissionResources = val;
      return this;
    }

    public PermissionEntity build() {
      return new PermissionEntity(this);
    }
  }
}
