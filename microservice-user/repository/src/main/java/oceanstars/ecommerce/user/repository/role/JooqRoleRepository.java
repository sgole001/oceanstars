package oceanstars.ecommerce.user.repository.role;

import static java.util.Objects.requireNonNull;

import jakarta.annotation.Resource;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;
import oceanstars.ecommerce.common.domain.repository.BaseDomainRepository;
import oceanstars.ecommerce.common.domain.repository.condition.ICondition;
import oceanstars.ecommerce.common.exception.BusinessException;
import oceanstars.ecommerce.user.constant.enums.UserEnums.Message;
import oceanstars.ecommerce.user.domain.role.entity.Role;
import oceanstars.ecommerce.user.domain.role.repository.RoleRepository;
import oceanstars.ecommerce.user.repository.generate.tables.daos.RelRolePermissionDao;
import oceanstars.ecommerce.user.repository.generate.tables.daos.RelRoleRoleDao;
import oceanstars.ecommerce.user.repository.generate.tables.daos.UserRoleDao;
import oceanstars.ecommerce.user.repository.generate.tables.pojos.RelRolePermissionPojo;
import oceanstars.ecommerce.user.repository.generate.tables.pojos.RelRoleRolePojo;
import oceanstars.ecommerce.user.repository.generate.tables.pojos.UserRolePojo;
import oceanstars.ecommerce.user.repository.role.view.RoleViewDao;
import oceanstars.ecommerce.user.repository.role.view.bo.RoleView;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

/**
 * 角色聚合仓储实现类
 *
 * @author Clover
 * @version 1.0.0
 * @since 2024/5/23 17:39
 */
@Repository
public class JooqRoleRepository extends BaseDomainRepository<Role> implements RoleRepository {

  /**
   * 角色数据访问对象
   */
  @Resource
  private UserRoleDao roleDao;

  /**
   * 角色关联关系数据访问对象
   */
  @Resource
  private RelRoleRoleDao relRoleRoleDao;

  /**
   * 角色权限关联关系数据访问对象
   */
  @Resource
  private RelRolePermissionDao relRolePermissionDao;

  /**
   * 角色视图数据访问对象
   */
  @Resource
  private RoleViewDao roleViewDao;

  @Override
  protected void create(Role role) {

    // 校验参数
    requireNonNull(role, "role");

    // 根据角色名称（唯一标识符）查询角色信息
    final UserRolePojo rolePojo = this.roleDao.fetchOneByName(role.getIdentifier().getName());

    // 判断角色信息是否存在, 如果存在则抛出业务异常
    if (rolePojo != null) {
      throw new BusinessException(Message.MSG_BIZ_00002, role.getIdentifier().getName());
    }

    // 构建角色数据库映射
    final UserRolePojo roleRepoPojo = this.buildRoleRepoPojo(role);
    // 保存角色信息
    this.roleDao.insert(roleRepoPojo);
    // 委托角色实体
    role.delegate(roleRepoPojo);

    // 判断父角色是否存在,如果存在则保存角色隶属父角色信息
    if (!CollectionUtils.isEmpty(role.getParents())) {
      // 构建角色关联关系数据库映射
      final List<RelRoleRolePojo> relRoleRoleRepoPojoList = this.buildRelRoleRoleRepoPojo(role);
      // 保存角色关联关系信息
      this.relRoleRoleDao.insert(relRoleRoleRepoPojoList);
    }

    // 构建角色权限关联关系数据库映射
    final List<RelRolePermissionPojo> relRolePermissionRepoPojoList = this.buildRelRolePermissionRepoPojo(role);
    // 保存角色权限关联关系信息
    this.relRolePermissionDao.insert(relRolePermissionRepoPojoList);
  }

  @Override
  protected void modify(Role role) {

  }

  @Override
  public List<Role> find(ICondition condition) {

    // 校验参数
    requireNonNull(condition, "condition");

    // 根据角色查询条件查询角色视图信息
    final List<RoleView> roles = this.roleViewDao.fetch(condition);

    // 校验角色是否存在,如果不存在则返回空列表
    if (CollectionUtils.isEmpty(roles)) {
      return List.of();
    }

    // 构建权限实体列表
    return roles.stream().map(this::buildRoleEntity).toList();
  }

  @Override
  public void delete(Role role) {

  }

  /**
   * 构建角色实体信息构建角色数据库映射
   *
   * @param role 角色实体信息
   * @return 角色数据库映射
   */
  private UserRolePojo buildRoleRepoPojo(final Role role) {

    // 初始化角色数据库映射对象
    final UserRolePojo rolePojo = new UserRolePojo();

    // 设置角色名称
    rolePojo.setName(role.getIdentifier().getName());
    // 设置角色描述
    rolePojo.setDesc(role.getDesc());
    // 设置角色逻辑有效标志位
    rolePojo.setEnabled(role.getEnabled());

    return rolePojo;
  }

  /**
   * 构建角色关联关系数据库映射
   *
   * @param role 角色实体信息
   * @return 角色关联关系数据库映射
   */
  private List<RelRoleRolePojo> buildRelRoleRoleRepoPojo(final Role role) {

    // 查询指定父角色信息
    final List<UserRolePojo> parentRoles = this.roleDao.fetchById(role.getParents().toArray(new Long[0]));
    // 判断父角色信息是否存在, 如果不存在则抛出业务异常
    if (CollectionUtils.isEmpty(parentRoles)) {
      throw new BusinessException(Message.MSG_BIZ_00003, role.getIdentifier().getName());
    }

    // 构建角色关联关系数据库映射
    return parentRoles.stream().map(parent -> {
      // 初始化角色关联关系数据库映射对象
      final RelRoleRolePojo relRoleRolePojo = new RelRoleRolePojo();
      // 设置角色ID
      relRoleRolePojo.setRole(role.getDelegator().getId());
      // 设置父角色ID
      relRoleRolePojo.setParent(parent.getId());

      return relRoleRolePojo;
    }).toList();
  }

  /**
   * 构建角色权限关联关系数据库映射
   *
   * @param role 角色实体信息
   * @return 角色权限关联关系数据库映射
   */
  private List<RelRolePermissionPojo> buildRelRolePermissionRepoPojo(final Role role) {

    return role.getPermissions().stream().map(permission -> {
      // 初始化角色权限关联关系数据库映射对象
      final RelRolePermissionPojo relRolePermissionPojo = new RelRolePermissionPojo();
      // 设置角色ID
      relRolePermissionPojo.setRole(role.getDelegator().getId());
      // 设置权限ID
      relRolePermissionPojo.setPermission(permission);

      return relRolePermissionPojo;
    }).toList();
  }

  /**
   * 构建角色实体信息
   *
   * @param roleView 角色视图信息
   * @return 角色实体
   */
  private Role buildRoleEntity(final RoleView roleView) {

    // 获取角色信息
    final UserRolePojo rolePojo = roleView.getRole();

    // 获取角色隶属信息（父角色）
    final List<UserRolePojo> parents = roleView.getRoles();

    // 获取角色权限信息
    final List<Long> permissions = roleView.getPermissions();

    // 构建角色实体
    final Role role = Role.newBuilder(rolePojo.getName())
        // 角色描述
        .desc(rolePojo.getDesc())
        // 角色逻辑有效标志位
        .enabled(rolePojo.getEnabled())
        // 角色权限列表
        .permissions(new HashSet<>(permissions))
        // 实施构建
        .build();

    // 角色隶属关系
    if (!CollectionUtils.isEmpty(parents)) {
      role.setParents(parents.stream().map(UserRolePojo::getId).collect(Collectors.toSet()));
    }

    // 设定角色委托者信息
    role.delegate(rolePojo);

    return role;
  }
}
