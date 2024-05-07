package oceanstars.ecommerce.user.domain.permission.entity;

import oceanstars.ecommerce.common.domain.entity.Entity;
import oceanstars.ecommerce.user.constant.enums.UserEnums.PermissionOperationType;

/**
 * 权限资源操作实体
 *
 * @author Clover
 * @version 1.0.0
 * @since 2024/1/9 11:47
 */
public class PermissionBehaviorEntity extends Entity<PermissionBehaviorIdentifier> {

  /**
   * 权限操作行为：是否禁用
   */
  private Boolean isForbidden;

  /**
   * 权限操作行为：是否允许创建
   */
  private Boolean canCreate;

  /**
   * 权限操作行为：是否允许读取
   */
  private Boolean canRead;

  /**
   * 权限操作行为：是否允许更新
   */
  private Boolean canUpdate;

  /**
   * 权限操作行为：是否允许删除
   */
  private Boolean canDelete;

  /**
   * 构造函数：根据构建器初始化成员变量
   *
   * @param builder 构建器
   */
  private PermissionBehaviorEntity(Builder builder) {
    super(new PermissionBehaviorIdentifier(builder.resource));
    isForbidden = builder.isForbidden;
    canCreate = builder.canCreate;
    canRead = builder.canRead;
    canUpdate = builder.canUpdate;
    canDelete = builder.canDelete;
  }

  /**
   * 创建权限操作值对象构建器
   *
   * @return 权限操作值对象构建器
   */
  public static PermissionBehaviorEntity.Builder newBuilder(final Long resource) {
    return new PermissionBehaviorEntity.Builder(resource);
  }

  /**
   * 序列化权限操作
   *
   * @return 权限操作序列化值
   */
  public Byte doSerialization() {

    // 初始化权限操作值
    byte operation = PermissionOperationType.PROHIBIT.key();

    if (isForbidden) {
      return operation;
    }

    if (canCreate) {
      operation |= PermissionOperationType.WRITE.key();
    }

    if (canRead) {
      operation |= PermissionOperationType.READ.key();
    }

    if (canUpdate) {
      operation |= PermissionOperationType.UPDATE.key();
    }

    if (canDelete) {
      operation |= PermissionOperationType.DELETE.key();
    }

    return operation;
  }

  /**
   * 反序列化权限操作
   *
   * @param opt 权限操作序列化值
   */
  public void doDeserialization(Byte opt) {

    this.isForbidden = opt != 0x00;

    this.canCreate = (opt & PermissionOperationType.WRITE.key()) != 0x00;

    this.canRead = (opt & PermissionOperationType.READ.key()) != 0x00;

    this.canUpdate = (opt & PermissionOperationType.UPDATE.key()) != 0x00;

    this.canDelete = (opt & PermissionOperationType.DELETE.key()) != 0x00;
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

  public void setForbidden(Boolean forbidden) {
    isForbidden = forbidden;
  }

  public void setCanCreate(Boolean canCreate) {
    this.canCreate = canCreate;
  }

  public void setCanRead(Boolean canRead) {
    this.canRead = canRead;
  }

  public void setCanUpdate(Boolean canUpdate) {
    this.canUpdate = canUpdate;
  }

  public void setCanDelete(Boolean canDelete) {
    this.canDelete = canDelete;
  }

  /**
   * 创建权限操作值对象构建器
   *
   * @author Clover
   * @version 1.0.0
   * @since 2024/1/9 11:45
   */
  public static final class Builder {

    private final Long resource;
    private Boolean isForbidden;
    private Boolean canCreate;
    private Boolean canRead;
    private Boolean canUpdate;
    private Boolean canDelete;

    public Builder(Long resource) {
      this.resource = resource;
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

    public PermissionBehaviorEntity build() {
      return new PermissionBehaviorEntity(this);
    }
  }
}
