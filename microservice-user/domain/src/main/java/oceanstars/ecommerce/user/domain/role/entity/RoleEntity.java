package oceanstars.ecommerce.user.domain.role.entity;

import java.util.List;
import oceanstars.ecommerce.common.domain.AggregateRoot;
import oceanstars.ecommerce.user.domain.role.entity.valueobject.RolePermValueObject;

/**
 * 角色实体：聚合根
 *
 * @author Clover
 * @version 1.0.0
 * @since 2022/1/5 9:50 AM
 */
public final class RoleEntity extends AggregateRoot<RoleIdentifier> {

  /**
   * 角色名
   */
  private final String name;

  /**
   * 角色说明
   */
  private String desc;

  /**
   * 角色逻辑有效标志位
   */
  private Boolean enabled;

  /**
   * 父角色列表
   */
  private List<RoleEntity> parents;

  /**
   * 子角色列表
   */
  private List<RoleEntity> children;

  /**
   * 角色权限列表
   */
  private List<RolePermValueObject> permissions;

  /**
   * 构造函数：根据构建器初始化成员变量
   *
   * @param builder 构造器
   */
  private RoleEntity(Builder builder) {
    super(new RoleIdentifier(builder.name));
    name = builder.name;
    desc = builder.desc;
    enabled = builder.enabled;
    parents = builder.parents;
    children = builder.children;
    permissions = builder.permissions;
  }

  /**
   * 创建角色实体构建器
   *
   * @param name 角色名
   * @return 角色实体构建器
   */
  public static Builder newBuilder(final String name) {
    return new Builder(name);
  }

  public String getName() {
    return name;
  }

  public String getDesc() {
    return desc;
  }

  public Boolean getEnabled() {
    return enabled;
  }

  public List<RoleEntity> getParents() {
    return parents;
  }

  public List<RoleEntity> getChildren() {
    return children;
  }

  public List<RolePermValueObject> getPermissions() {
    return permissions;
  }

  public void setDesc(String desc) {
    this.desc = desc;
  }

  public void setEnabled(Boolean enabled) {
    this.enabled = enabled;
  }

  public void setParents(List<RoleEntity> parents) {
    this.parents = parents;
  }

  public void setChildren(List<RoleEntity> children) {
    this.children = children;
  }

  public void setPermissions(List<RolePermValueObject> permissions) {
    this.permissions = permissions;
  }

  /**
   * 角色实体构建器
   *
   * @author Clover
   * @version 1.0.0
   * @since 2022/1/5 11:37 AM
   */
  public static final class Builder {

    private final String name;
    private String desc;
    private Boolean enabled;
    private List<RoleEntity> parents;
    private List<RoleEntity> children;
    private List<RolePermValueObject> permissions;

    public Builder(String name) {
      this.name = name;
    }

    public Builder desc(String val) {
      desc = val;
      return this;
    }

    public Builder enabled(Boolean val) {
      enabled = val;
      return this;
    }

    public Builder parents(List<RoleEntity> val) {
      parents = val;
      return this;
    }

    public Builder children(List<RoleEntity> val) {
      children = val;
      return this;
    }

    public Builder permissions(List<RolePermValueObject> val) {
      permissions = val;
      return this;
    }

    public RoleEntity build() {
      return new RoleEntity(this);
    }
  }
}
