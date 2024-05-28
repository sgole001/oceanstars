package oceanstars.ecommerce.user.domain.role.repository.condition;

import java.util.Set;
import oceanstars.ecommerce.common.domain.repository.condition.BaseCondition;

/**
 * 角色查询条件
 *
 * @author Clover
 * @version 1.0.0
 * @since 2024/5/24 18:14
 */
public class RoleFetchCondition extends BaseCondition {

  /**
   * 角色名称
   */
  private final String name;

  /**
   * 角色逻辑有效标志位
   */
  private final Boolean enabled;

  /**
   * 父角色(角色继承)列表
   */
  private final Set<Long> parents;

  /**
   * 角色权限列表
   */
  private final Set<Long> permissions;

  /**
   * 构造函数
   *
   * @param builder 构建器
   */
  private RoleFetchCondition(Builder builder) {
    super(builder);
    name = builder.name;
    enabled = builder.enabled;
    parents = builder.parents;
    permissions = builder.permissions;
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

  public Boolean getEnabled() {
    return enabled;
  }

  public Set<Long> getParents() {
    return parents;
  }

  public Set<Long> getPermissions() {
    return permissions;
  }

  /**
   * 构造器
   *
   * @author Clover
   * @version 1.0.0
   * @since 2024/5/24 18:18
   */
  public static final class Builder extends BaseCondition.Builder<RoleFetchCondition, RoleFetchCondition.Builder> {

    private String name;
    private Boolean enabled;
    private Set<Long> parents;
    private Set<Long> permissions;

    public Builder() {
    }

    public Builder name(String val) {
      name = val;
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

    @Override
    public RoleFetchCondition build() {
      return new RoleFetchCondition(this);
    }
  }
}
