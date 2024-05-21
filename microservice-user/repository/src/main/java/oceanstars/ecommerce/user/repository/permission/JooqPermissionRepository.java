package oceanstars.ecommerce.user.repository.permission;

import static java.util.Objects.requireNonNull;

import jakarta.annotation.Resource;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import oceanstars.ecommerce.common.domain.repository.BaseDomainRepository;
import oceanstars.ecommerce.common.domain.repository.condition.ICondition;
import oceanstars.ecommerce.common.exception.BusinessException;
import oceanstars.ecommerce.infrastructure.pool.configuration.OceanstarsTransactional;
import oceanstars.ecommerce.user.constant.enums.UserEnums.Message;
import oceanstars.ecommerce.user.constant.enums.UserEnums.PermissionType;
import oceanstars.ecommerce.user.domain.permission.entity.Permission;
import oceanstars.ecommerce.user.domain.permission.entity.PermissionBehavior;
import oceanstars.ecommerce.user.domain.permission.repository.PermissionRepository;
import oceanstars.ecommerce.user.domain.permission.repository.condition.PermissionFetchCondition;
import oceanstars.ecommerce.user.repository.generate.tables.daos.UserPermissionBehaviorDao;
import oceanstars.ecommerce.user.repository.generate.tables.daos.UserPermissionDao;
import oceanstars.ecommerce.user.repository.generate.tables.pojos.UserPermissionBehaviorPojo;
import oceanstars.ecommerce.user.repository.generate.tables.pojos.UserPermissionPojo;
import oceanstars.ecommerce.user.repository.permission.view.PermissionViewDao;
import oceanstars.ecommerce.user.repository.permission.view.bo.PermissionView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

/**
 * 权限聚合仓储实现类
 *
 * @author Clover
 * @version 1.0.0
 * @since 2022/1/11 2:48 PM
 */
@Repository
public class JooqPermissionRepository extends BaseDomainRepository<Permission> implements PermissionRepository {

  /**
   * 权限数据访问对象
   */
  @Resource
  private UserPermissionDao permissionDao;

  /**
   * 权限操作行为数据访问对象
   */
  @Resource
  private UserPermissionBehaviorDao behaviorDao;

  /**
   * 权限视图数据访问对象
   */
  @Resource
  private PermissionViewDao permissionViewDao;

  /**
   * 日志管理器
   */
  private final Logger logger = LoggerFactory.getLogger(JooqPermissionRepository.class);

  @OceanstarsTransactional(rollbackFor = Exception.class)
  @Override
  protected void create(Permission permission) {

    // 校验参数
    requireNonNull(permission, "permission");

    // 构建权限查询条件(根据权限唯一标识符)
    final PermissionFetchCondition condition = PermissionFetchCondition.newBuilder()
        // 权限名称
        .name(permission.getIdentifier().getName())
        // 权限类型
        .types(new HashSet<>(Collections.singletonList(permission.getIdentifier().getType())))
        // 构建查询条件
        .build();

    // 根据权限查询条件查询权限视图信息
    final List<PermissionView> permissions = this.permissionViewDao.fetch(condition);

    // 校验权限是否已存在，如果存在则抛出业务异常
    if (!CollectionUtils.isEmpty(permissions)) {
      throw new BusinessException(Message.MSG_BIZ_00004, permissions.getFirst().getPermission().getName());
    }

    // 构建权限数据库映射
    final UserPermissionPojo permissionPojo = this.buildPermissionRepoPojo(permission);
    // 保存权限数据
    this.permissionDao.insert(permissionPojo);

    // 委托权限实体
    permission.delegate(permissionPojo);

    // 构建权限操作行为数据库映射
    final List<UserPermissionBehaviorPojo> behaviorPojoList = permission.getBehaviors().stream().map(this::buildPermissionBehavior).toList();
    // 保存权限操作行为数据
    this.behaviorDao.insert(behaviorPojoList);

    // 构建权限视图对象
    permission.delegate(permissionPojo);
  }

  @Override
  protected void modify(Permission permission) {

  }

  @Override
  public List<Permission> find(ICondition condition) {

    // 校验参数
    requireNonNull(condition, "condition");

    // 根据权限查询条件查询权限视图信息
    final List<PermissionView> permissions = this.permissionViewDao.fetch(condition);

    // 校验权限是否存在,如果不存在则返回空列表
    if (CollectionUtils.isEmpty(permissions)) {
      return List.of();
    }

    // 构建权限实体列表
    return permissions.stream().map(this::buildPermissionEntity).toList();
  }

  @Override
  public void delete(Permission permission) {

  }

  /**
   * 根据权限视图构建权限实体
   *
   * @param permissionView 权限视图
   * @return 权限实体
   */
  private Permission buildPermissionEntity(final PermissionView permissionView) {

    // 获取权限数据
    final UserPermissionPojo permissionPojo = permissionView.getPermission();
    // 获取权限行为数据
    final List<UserPermissionBehaviorPojo> behaviorPojoList = permissionView.getBehaviors();

    // 初始化权限实体
    final Permission permission = Permission.newBuilder(
            // 权限名
            permissionPojo.getName(),
            // 权限类型
            PermissionType.of(permissionPojo.getType().intValue())
        )
        // 权限描述
        .desc(permissionPojo.getDesc())
        // 权限逻辑有效标志位
        .enabled(permissionPojo.getEnabled())
        // 执行构建
        .build();

    // 委托权限实体
    permission.delegate(permissionPojo);

    // 权限操作行为
    permission.setBehaviors(
        permissionView.getBehaviors().stream().map(behavior -> this.buildPermissionBehaviorEntity(permission, behavior)).toList());

    return permission;
  }

  /**
   * 根据权限实体和权限操作行为数据库映射构建权限操作行为实体
   *
   * @param permission   权限实体
   * @param behaviorPojo 权限操作行为数据库映射
   * @return 权限操作行为实体
   */
  private PermissionBehavior buildPermissionBehaviorEntity(final Permission permission, final UserPermissionBehaviorPojo behaviorPojo) {

    // 初始化权限操作行为实体
    final PermissionBehavior behavior = PermissionBehavior.newBuilder(permission, behaviorPojo.getResource()).build();

    // 反序列化权限操作行为
    behavior.doDeserialization(behaviorPojo.getOpt());

    // 委托权限操作行为实体
    behavior.delegate(behaviorPojo);

    return behavior;
  }

  /**
   * 根据权限实体构建权限数据库映射
   *
   * @param permission 权限实体
   * @return 权限数据库映射
   */
  private UserPermissionPojo buildPermissionRepoPojo(final Permission permission) {

    // 初始化权限数据库映射对象
    final UserPermissionPojo permissionPojo = new UserPermissionPojo();

    // 权限名
    permissionPojo.setName(permission.getIdentifier().getName());
    // 权限类型
    permissionPojo.setType(permission.getIdentifier().getType().key().shortValue());
    // 权限描述
    permissionPojo.setDesc(permission.getDesc());
    // 权限逻辑有效标志位
    permissionPojo.setEnabled(permission.getEnabled());

    return permissionPojo;
  }

  /**
   * 根据权限操作行为构建权限操作行为数据库映射
   *
   * @param behavior 权限操作行为
   * @return 权限操作行为列表
   */
  private UserPermissionBehaviorPojo buildPermissionBehavior(final PermissionBehavior behavior) {

    // 初始化权限行为-资源操作关系数据库映射对象
    final UserPermissionBehaviorPojo permissionBehaviorPojo = new UserPermissionBehaviorPojo();

    // 权限唯一识别符
    permissionBehaviorPojo.setPermission(behavior.getIdentifier().getPermission().getDelegator().getId());
    // 资源操作唯一识别符
    permissionBehaviorPojo.setResource(behavior.getIdentifier().getResource());
    // 资源操作类型
    permissionBehaviorPojo.setOpt(behavior.doSerialization());

    return permissionBehaviorPojo;
  }
}
