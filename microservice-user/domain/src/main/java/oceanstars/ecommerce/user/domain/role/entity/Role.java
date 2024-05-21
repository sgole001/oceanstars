package oceanstars.ecommerce.user.domain.role.entity;

import java.util.Set;
import oceanstars.ecommerce.common.domain.entity.AggregateRoot;

/**
 * 角色实体：聚合根
 *
 * @author Clover
 * @version 1.0.0
 * @since 2022/1/5 9:50 AM
 */
public final class Role extends AggregateRoot<RoleIdentifier> {

  /**
   * 角色说明
   */
  private String desc;

  /**
   * 角色逻辑有效标志位
   */
  private Boolean enabled;

  /**
   * 父角色(角色继承)列表
   */
  private Set<Long> parents;

  /**
   * 角色权限列表
   */
  private Set<Long> permissions;

  /**
   * 构造函数：根据构建器初始化成员变量
   *
   * @param builder 构造器
   */
  private Role(Builder builder) {
    super(new RoleIdentifier(builder.name));
    desc = builder.desc;
    enabled = builder.enabled;
    parents = builder.parents;
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

  public String getDesc() {
    return desc;
  }

  public Boolean getEnabled() {
    return enabled;
  }

  public Set<Long> getParents() {
    return parents;
  }

  public Set<Long> getPermissions() {
    return permissions;
  }

  public void setDesc(String desc) {
    this.desc = desc;
  }

  public void setEnabled(Boolean enabled) {
    this.enabled = enabled;
  }

  public void setParents(Set<Long> parents) {
    this.parents = parents;
  }

  public void setPermissions(Set<Long> permissions) {
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
    private Set<Long> parents;
    private Set<Long> permissions;

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

    public Builder parents(Set<Long> val) {
      parents = val;
      return this;
    }

    public Builder permissions(Set<Long> val) {
      permissions = val;
      return this;
    }

    public Role build() {
      return new Role(this);
    }
  }
}
