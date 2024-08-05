package oceanstars.ecommerce.iam.domain.access.entity;

import java.util.Map;
import java.util.Set;
import oceanstars.ecommerce.common.domain.entity.AggregateRoot;
import oceanstars.ecommerce.iam.constant.enums.IamEnums.AccessType;
import oceanstars.ecommerce.iam.constant.enums.IamEnums.PrincipalType;

/**
 * Access实体：聚合根
 *
 * @author Clover
 * @version 1.0.0
 * @since 2024/7/8 14:00
 */
public class Access extends AggregateRoot<AccessIdentifier> {

  /**
   * Access操作行为-被禁止的主体ID列表
   */
  private Map<PrincipalType, Set<Long>> forbiddenPrincipals;

  /**
   * Access操作行为-可以创建的主体ID列表
   */
  private Map<PrincipalType, Set<Long>> canCreatePrincipals;

  /**
   * Access操作行为-可以读取的主体ID列表
   */
  private Map<PrincipalType, Set<Long>> canReadPrincipals;

  /**
   * Access操作行为-可以更新的主体ID列表
   */
  private Map<PrincipalType, Set<Long>> canUpdatePrincipals;

  /**
   * Access操作行为-可以删除的主体ID列表
   */
  private Map<PrincipalType, Set<Long>> canDeletePrincipals;

  /**
   * 构造函数：根据构建器初始化成员变量
   *
   * @param builder 构建器
   */
  private Access(Builder builder) {
    super(new AccessIdentifier(builder.resource, builder.type));
    forbiddenPrincipals = builder.forbiddenPrincipals;
    canCreatePrincipals = builder.canCreatePrincipals;
    canReadPrincipals = builder.canReadPrincipals;
    canUpdatePrincipals = builder.canUpdatePrincipals;
    canDeletePrincipals = builder.canDeletePrincipals;
  }

  /**
   * 创建Access实体构建器
   *
   * @param resource Access目标资源ID
   * @param type     Access类型
   * @return Access实体构建器
   */
  public static Builder newBuilder(Long resource, AccessType type) {
    return new Builder(resource, type);
  }

  public Map<PrincipalType, Set<Long>> getForbiddenPrincipals() {
    return forbiddenPrincipals;
  }

  public void setForbiddenPrincipals(
      Map<PrincipalType, Set<Long>> forbiddenPrincipals) {
    this.forbiddenPrincipals = forbiddenPrincipals;
  }

  public Map<PrincipalType, Set<Long>> getCanCreatePrincipals() {
    return canCreatePrincipals;
  }

  public void setCanCreatePrincipals(
      Map<PrincipalType, Set<Long>> canCreatePrincipals) {
    this.canCreatePrincipals = canCreatePrincipals;
  }

  public Map<PrincipalType, Set<Long>> getCanReadPrincipals() {
    return canReadPrincipals;
  }

  public void setCanReadPrincipals(
      Map<PrincipalType, Set<Long>> canReadPrincipals) {
    this.canReadPrincipals = canReadPrincipals;
  }

  public Map<PrincipalType, Set<Long>> getCanUpdatePrincipals() {
    return canUpdatePrincipals;
  }

  public void setCanUpdatePrincipals(
      Map<PrincipalType, Set<Long>> canUpdatePrincipals) {
    this.canUpdatePrincipals = canUpdatePrincipals;
  }

  public Map<PrincipalType, Set<Long>> getCanDeletePrincipals() {
    return canDeletePrincipals;
  }

  public void setCanDeletePrincipals(
      Map<PrincipalType, Set<Long>> canDeletePrincipals) {
    this.canDeletePrincipals = canDeletePrincipals;
  }

  /**
   * Access实体构建器
   *
   * @author Clover
   * @version 1.0.0
   * @since 2024/7/8 16:54
   */
  public static final class Builder {

    private final Long resource;
    private final AccessType type;
    private Map<PrincipalType, Set<Long>> forbiddenPrincipals;
    private Map<PrincipalType, Set<Long>> canCreatePrincipals;
    private Map<PrincipalType, Set<Long>> canReadPrincipals;
    private Map<PrincipalType, Set<Long>> canUpdatePrincipals;
    private Map<PrincipalType, Set<Long>> canDeletePrincipals;

    public Builder(Long resource, AccessType type) {
      this.resource = resource;
      this.type = type;
    }

    public Builder forbiddenPrincipals(Map<PrincipalType, Set<Long>> val) {
      forbiddenPrincipals = val;
      return this;
    }

    public Builder canCreatePrincipals(Map<PrincipalType, Set<Long>> val) {
      canCreatePrincipals = val;
      return this;
    }

    public Builder canReadPrincipals(Map<PrincipalType, Set<Long>> val) {
      canReadPrincipals = val;
      return this;
    }

    public Builder canUpdatePrincipals(Map<PrincipalType, Set<Long>> val) {
      canUpdatePrincipals = val;
      return this;
    }

    public Builder canDeletePrincipals(Map<PrincipalType, Set<Long>> val) {
      canDeletePrincipals = val;
      return this;
    }

    public Access build() {
      return new Access(this);
    }
  }
}
