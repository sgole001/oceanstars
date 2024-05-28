package oceanstars.ecommerce.user.repository.role.view;

import jakarta.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import oceanstars.ecommerce.common.domain.repository.condition.ICondition;
import oceanstars.ecommerce.user.domain.role.repository.condition.RoleFetchCondition;
import oceanstars.ecommerce.user.repository.generate.tables.RelRolePermission;
import oceanstars.ecommerce.user.repository.generate.tables.RelRoleRole;
import oceanstars.ecommerce.user.repository.generate.tables.UserRole;
import oceanstars.ecommerce.user.repository.generate.tables.pojos.UserRolePojo;
import oceanstars.ecommerce.user.repository.role.view.bo.RoleView;
import org.jooq.Condition;
import org.jooq.impl.DSL;
import org.jooq.impl.DefaultDSLContext;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

/**
 * 角色视图数据访问对象
 *
 * @author Clover
 * @version 1.0.0
 * @since 2024/5/24 18:06
 */
@Repository
public class RoleViewDao {

  /**
   * 角色表
   */
  final static UserRole T_ROLE = UserRole.USER_ROLE.as("role");

  /**
   * 父角色表
   */
  final static UserRole T_PARENT_ROLE = UserRole.USER_ROLE.as("parent_role");

  /**
   * 角色关联关系表
   */
  final static RelRoleRole REL_ROLE_ROLE = RelRoleRole.REL_ROLE_ROLE.as("rel_role");

  /**
   * 角色权限关联关系表
   */
  final static RelRolePermission REL_ROLE_PERMISSION = RelRolePermission.REL_ROLE_PERMISSION.as("rel_perm");

  /**
   * Jooq数据库操作对象
   */
  @Resource
  private DefaultDSLContext dsl;

  /**
   * 根据查询条件查询角色视图信息
   *
   * @param condition 查询条件
   * @return 角色视图信息
   */
  public List<RoleView> fetch(final ICondition condition) {

    // 初始化查询条件
    Condition searchCondition = DSL.trueCondition();

    // 转换查询条件为角色查询条件
    final RoleFetchCondition fetchCondition = (RoleFetchCondition) condition;

    // 角色及隶属查询条件：角色ID
    if (null != fetchCondition.getId()) {
      searchCondition = searchCondition.and(T_ROLE.ID.eq(fetchCondition.getId()));
    }
    // 角色及隶属查询条件：角色名称
    if (null != fetchCondition.getName()) {
      searchCondition = searchCondition.and(T_ROLE.NAME.likeIgnoreCase(fetchCondition.getName().trim()));
    }
    // 角色及隶属查询条件：角色逻辑有效标志位
    if (null != fetchCondition.getEnabled()) {
      searchCondition = searchCondition.and(T_ROLE.ENABLED.eq(fetchCondition.getEnabled()));
    }
    // 角色及隶属查询条件：父角色(角色继承)列表
    if (!CollectionUtils.isEmpty(fetchCondition.getParents())) {
      if (fetchCondition.getParents().size() == 1) {
        searchCondition = searchCondition.and(REL_ROLE_ROLE.ROLE.eq(fetchCondition.getParents().stream().findFirst().orElseThrow()));
      } else {
        searchCondition = searchCondition.and(REL_ROLE_ROLE.ROLE.in(fetchCondition.getParents()));
      }
    }
    // 角色及隶属查询条件：角色创建开始时间
    if (null != fetchCondition.getCreateStartTime()) {
      searchCondition = searchCondition.and(T_ROLE.CREATE_AT.ge(fetchCondition.getCreateStartTime()));
    }
    // 角色及隶属查询条件：角色创建结束时间
    if (null != fetchCondition.getCreateEndTime()) {
      searchCondition = searchCondition.and(T_ROLE.CREATE_AT.le(fetchCondition.getCreateEndTime()));
    }
    // 角色及隶属查询条件：角色更新开始时间
    if (null != fetchCondition.getUpdateStartTime()) {
      searchCondition = searchCondition.and(T_ROLE.UPDATE_AT.ge(fetchCondition.getUpdateStartTime()));
    }
    // 角色及隶属查询条件：角色更新结束时间
    if (null != fetchCondition.getUpdateEndTime()) {
      searchCondition = searchCondition.and(T_ROLE.UPDATE_AT.le(fetchCondition.getUpdateEndTime()));
    }

    // 角色及隶属关系查询
    final Map<UserRolePojo, List<UserRolePojo>> membershipResults = dsl.select(T_ROLE.fields())
        .select(T_PARENT_ROLE.fields())
        .from(T_ROLE)
        .leftJoin(REL_ROLE_ROLE).on(T_ROLE.ID.eq(REL_ROLE_ROLE.ROLE))
        .leftJoin(T_PARENT_ROLE).on(REL_ROLE_ROLE.PARENT.eq(T_PARENT_ROLE.ID))
        .where(searchCondition)
        .collect(Collectors.groupingBy(
            record -> record.into(T_ROLE).into(UserRolePojo.class),
            Collectors.mapping(record -> record.into(T_PARENT_ROLE).into(UserRolePojo.class), Collectors.toList())
        ));

    // 查询结果为空，返回空
    if (CollectionUtils.isEmpty(membershipResults)) {
      return null;
    }

    // 初始化角色视图映射
    final Map<Long, RoleView> roleViewMap = new HashMap<>(membershipResults.size());
    // 角色及隶属关系数据迭代设置角色视图
    membershipResults.forEach((key, value) -> {
      // 初始化角色视图对象
      final RoleView roleView = new RoleView();
      // 设置角色信息
      roleView.setRole(key);
      // 设计角色隶属关系信息（父角色）
      roleView.setRoles(value);

      roleViewMap.put(key.getId(), roleView);
    });

    // 角色权限查询条件：角色信息过滤后角色ID列表
    searchCondition = REL_ROLE_PERMISSION.ROLE.in(roleViewMap.keySet().stream().toList());
    // 角色权限查询条件：角色权限列表
    if (!CollectionUtils.isEmpty(fetchCondition.getPermissions())) {
      if (fetchCondition.getPermissions().size() == 1) {
        searchCondition = searchCondition.and(REL_ROLE_PERMISSION.PERMISSION.eq(fetchCondition.getPermissions().stream().findFirst().orElseThrow()));
      } else {
        searchCondition = searchCondition.and(REL_ROLE_PERMISSION.PERMISSION.in(fetchCondition.getPermissions()));
      }
    }

    // 角色及权限关系查询
    final Map<Long, List<Long>> permissionResults = dsl.select(REL_ROLE_PERMISSION.ROLE, REL_ROLE_PERMISSION.PERMISSION)
        .from(REL_ROLE_PERMISSION)
        .where(searchCondition)
        .fetchGroups(REL_ROLE_PERMISSION.ROLE, REL_ROLE_PERMISSION.PERMISSION);

    // 查询结果为空，返回空
    if (CollectionUtils.isEmpty(permissionResults)) {
      return null;
    }

    // 角色权限关系数据迭代设置角色视图并返回角色视图列表
    return permissionResults.entrySet().stream().map(entry -> {
      // 根据角色ID获取角色视图信息
      final RoleView roleView = roleViewMap.get(entry.getKey());
      // 设置权限信息
      roleView.setPermissions(entry.getValue());
      return roleView;
    }).toList();
  }
}
