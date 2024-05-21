package oceanstars.ecommerce.user.domain.permission.entity;

import java.util.ArrayList;
import java.util.List;
import oceanstars.ecommerce.common.domain.entity.AggregateRoot;
import oceanstars.ecommerce.user.constant.enums.UserEnums.PermissionOperationType;
import oceanstars.ecommerce.user.constant.enums.UserEnums.PermissionType;

/**
 * 权限实体：聚合根
 *
 * @author Clover
 * @version 1.0.0
 * @since 2022/1/5 11:49 AM
 */
public class Permission extends AggregateRoot<PermissionIdentifier> {

  /**
   * 权限描述
   */
  private String desc;

  /**
   * 权限逻辑有效标志位
   */
  private Boolean enabled;

  /**
   * 权限行为-资源操作关系 {@link PermissionOperationType}
   */
  private List<PermissionBehavior> behaviors;

  /**
   * 构造函数：根据构建器初始化成员变量
   *
   * @param builder 构建器
   */
  private Permission(Builder builder) {
    super(new PermissionIdentifier(builder.name, builder.type));
    desc = builder.desc;
    enabled = builder.enabled;
    behaviors = builder.behaviors == null ? new ArrayList<>() : builder.behaviors;
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

  public String getDesc() {
    return desc;
  }

  public Boolean getEnabled() {
    return enabled;
  }

  public List<PermissionBehavior> getBehaviors() {
    return behaviors;
  }

  public void setDesc(String desc) {
    this.desc = desc;
  }

  public void setEnabled(Boolean enabled) {
    this.enabled = enabled;
  }

  public void setBehaviors(List<PermissionBehavior> behaviors) {
    this.behaviors = behaviors;
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
    private List<PermissionBehavior> behaviors;

    public Builder(String name, PermissionType type) {
      this.name = name;
      this.type = type;
    }

    public Builder desc(String val) {
      this.desc = val;
      return this;
    }

    public Builder enabled(Boolean val) {
      this.enabled = val;
      return this;
    }

    public Builder behaviors(List<PermissionBehavior> val) {
      this.behaviors = val;
      return this;
    }

    public Permission build() {
      return new Permission(this);
    }
  }
}
