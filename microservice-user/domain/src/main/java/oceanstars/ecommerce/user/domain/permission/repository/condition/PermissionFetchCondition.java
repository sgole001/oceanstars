package oceanstars.ecommerce.user.domain.permission.repository.condition;

import java.util.Set;
import oceanstars.ecommerce.common.domain.repository.condition.BaseCondition;
import oceanstars.ecommerce.user.constant.enums.UserEnums.PermissionType;

/**
 * 权限查询条件
 *
 * @author Clover
 * @version 1.0.0
 * @since 2024/5/16 18:04
 */
public class PermissionFetchCondition extends BaseCondition {

  /**
   * 权限名称
   */
  private final String name;

  /**
   * 权限类型
   */
  private final Set<PermissionType> types;

  /**
   * 权限逻辑有效标志位
   */
  private final Boolean enabled;

  /**
   * 权限资源ID
   */
  private final Set<Long> resources;

  /**
   * 权限操作行为：是否禁用
   */
  private final Boolean isForbidden;

  /**
   * 权限操作行为：是否允许创建
   */
  private final Boolean canCreate;

  /**
   * 权限操作行为：是否允许读取
   */
  private final Boolean canRead;

  /**
   * 权限操作行为：是否允许更新
   */
  private final Boolean canUpdate;

  /**
   * 权限操作行为：是否允许删除
   */
  private final Boolean canDelete;

  /**
   * 构造函数
   *
   * @param builder 构造器
   */
  private PermissionFetchCondition(Builder builder) {
    super(builder);
    name = builder.name;
    types = builder.types;
    enabled = builder.enabled;
    resources = builder.resources;
    isForbidden = builder.isForbidden;
    canCreate = builder.canCreate;
    canRead = builder.canRead;
    canUpdate = builder.canUpdate;
    canDelete = builder.canDelete;
  }

  /**
   * 创建构建器
   *
   * @return 构建器
   */
  public static Builder newBuilder() {
    return new Builder();
  }

  public String getName() {
    return name;
  }

  public Set<PermissionType> getTypes() {
    return types;
  }

  public Boolean getEnabled() {
    return enabled;
  }

  public Set<Long> getResources() {
    return resources;
  }

  public Boolean getForbidden() {
    return isForbidden;
  }

  public Boolean getCanCreate() {
    return canCreate;
  }

  public Boolean getCanRead() {
    return canRead;
  }

  public Boolean getCanUpdate() {
    return canUpdate;
  }

  public Boolean getCanDelete() {
    return canDelete;
  }

  /**
   * 构造器
   *
   * @author Clover
   * @version 1.0.0
   * @since 2024/5/16 18:23
   */
  public static final class Builder extends BaseCondition.Builder<PermissionFetchCondition, PermissionFetchCondition.Builder> {

    private String name;
    private Set<PermissionType> types;
    private Boolean enabled;
    private Set<Long> resources;
    private Boolean isForbidden;
    private Boolean canCreate;
    private Boolean canRead;
    private Boolean canUpdate;
    private Boolean canDelete;

    public Builder() {
    }

    public Builder name(String val) {
      name = val;
      return this;
    }

    public Builder types(Set<PermissionType> val) {
      types = val;
      return this;
    }

    public Builder enabled(Boolean val) {
      enabled = val;
      return this;
    }

    public Builder resources(Set<Long> val) {
      resources = val;
      return this;
    }

    public Builder isForbidden(Boolean val) {
      isForbidden = val;
      return this;
    }

    public Builder canCreate(Boolean val) {
      canCreate = val;
      return this;
    }

    public Builder canRead(Boolean val) {
      canRead = val;
      return this;
    }

    public Builder canUpdate(Boolean val) {
      canUpdate = val;
      return this;
    }

    public Builder canDelete(Boolean val) {
      canDelete = val;
      return this;
    }

    @Override
    public PermissionFetchCondition build() {
      return new PermissionFetchCondition(this);
    }
  }
}
