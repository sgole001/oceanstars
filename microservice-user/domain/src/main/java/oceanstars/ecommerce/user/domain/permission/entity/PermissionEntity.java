package oceanstars.ecommerce.user.domain.permission.entity;

import java.util.ArrayList;
import java.util.List;
import oceanstars.ecommerce.common.domain.AggregateRoot;
import oceanstars.ecommerce.common.exception.BusinessException;
import oceanstars.ecommerce.common.spring.ApplicationContextProvider;
import oceanstars.ecommerce.user.constant.enums.UserEnums.PermissionOperationType;
import oceanstars.ecommerce.user.constant.enums.UserEnums.PermissionType;
import oceanstars.ecommerce.user.repository.generate.tables.pojos.RelPermissionResourcePojo;
import oceanstars.ecommerce.user.repository.generate.tables.pojos.UserPermissionPojo;
import oceanstars.ecommerce.user.repository.permission.IPermissionRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

/**
 * 权限实体：聚合根
 *
 * @author Clover
 * @version 1.0.0
 * @since 2022/1/5 11:49 AM
 */
public class PermissionEntity extends AggregateRoot<PermissionIdentifier> {

  /**
   * 权限名
   */
  private final String name;

  /**
   * 权限类型 {@link PermissionType}
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
   * 权限行为-资源操作关系 {@link PermissionOperationType}
   */
  private List<PermissionBehaviorEntity> behaviors;

  /**
   * 权限聚合仓储接口
   */
  private final IPermissionRepository permissionRepository;

  /**
   * 构造函数：根据构建器初始化成员变量
   *
   * @param builder 构建器
   */
  private PermissionEntity(Builder builder) {
    super(new PermissionIdentifier(builder.name, builder.type));
    name = builder.name;
    type = builder.type;
    desc = builder.desc;
    enabled = builder.enabled;
    behaviors = builder.behaviors == null ? new ArrayList<>() : builder.behaviors;
    permissionRepository = ApplicationContextProvider.getApplicationContext().getBean(IPermissionRepository.class);
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

  /**
   * 申请权限
   */
  @Transactional(rollbackFor = Exception.class)
  public void apply() {

    // 构建权限数据信息
    final UserPermissionPojo permission = this.buildPermissionRepoPojo(Boolean.FALSE);
    // 添加权限行为-资源操作关系
    final List<RelPermissionResourcePojo> relPermissionResources = this.buildRelPermissionResources();

    // 创建权限基础信息
    this.permissionRepository.createPermission(permission);
    // 委托权限实体
    this.delegate(permission);
    // 赋予权限行为（资源操作）
    this.permissionRepository.assignPermissionBehavior(relPermissionResources);
  }

  public void modify() {

    // 构建权限数据信息
    final UserPermissionPojo permission = this.buildPermissionRepoPojo(Boolean.TRUE);

    this.permissionRepository.updatePermission(permission);
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

  public List<PermissionBehaviorEntity> getBehaviors() {
    return behaviors;
  }

  public void setDesc(String desc) {
    this.desc = desc;
  }

  public void setEnabled(Boolean enabled) {
    this.enabled = enabled;
  }

  public void setBehaviors(List<PermissionBehaviorEntity> behaviors) {
    this.behaviors = behaviors;
  }

  /**
   * 根据权限实体构建权限数据库映射
   *
   * @return 权限数据库映射
   */
  private UserPermissionPojo buildPermissionRepoPojo(Boolean forUpdate) {

    // 初始化权限数据库映射对象
    final UserPermissionPojo permissionPojo = new UserPermissionPojo();

    if (!forUpdate) {
      // 权限编号
      permissionPojo.setCode(this.getIdentifier().toString());
      // 权限名
      permissionPojo.setName(this.getName());
      // 权限类型
      permissionPojo.setType(this.getType().key());
    }
    // 权限描述
    permissionPojo.setDesc(this.getDesc());
    // 权限逻辑有效标志位
    if (null == this.getEnabled()) {
      permissionPojo.setEnabled(Boolean.TRUE);
    } else {
      permissionPojo.setEnabled(this.getEnabled());
    }

    return permissionPojo;
  }

  /**
   * 根据权限实体构建权限操作行为列表
   *
   * @return 权限操作行为列表
   */
  private List<RelPermissionResourcePojo> buildRelPermissionResources() {

    // 获取权限行为-资源操作关系
    final List<PermissionBehaviorEntity> behaviors = this.getBehaviors();

    if (CollectionUtils.isEmpty(behaviors)) {
      throw new BusinessException("权限资源关联数据不能为空");
    }

    final List<RelPermissionResourcePojo> relPermissionResources = new ArrayList<>();

    behaviors.forEach(behavior -> {

      // 初始化权限行为-资源操作关系数据库映射对象
      final RelPermissionResourcePojo relPermissionResourcePojo = new RelPermissionResourcePojo();

      // 权限唯一识别符
      relPermissionResourcePojo.setPid(this.getDelegator().getId());
      // 资源操作唯一识别符
      relPermissionResourcePojo.setRid(behavior.getIdentifier().getIdentifier());
      // 资源操作类型
      relPermissionResourcePojo.setOpt(behavior.doSerialization());

      relPermissionResources.add(relPermissionResourcePojo);
    });

    return relPermissionResources;
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
    private List<PermissionBehaviorEntity> behaviors;

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

    public Builder behaviors(List<PermissionBehaviorEntity> val) {
      this.behaviors = val;
      return this;
    }

    public PermissionEntity build() {
      return new PermissionEntity(this);
    }
  }
}
