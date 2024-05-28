package oceanstars.ecommerce.user.repository.permission.view;

import jakarta.annotation.Resource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import oceanstars.ecommerce.common.domain.repository.condition.ICondition;
import oceanstars.ecommerce.user.constant.enums.UserEnums.PermissionOperationType;
import oceanstars.ecommerce.user.domain.permission.repository.condition.PermissionFetchCondition;
import oceanstars.ecommerce.user.repository.generate.tables.UserPermission;
import oceanstars.ecommerce.user.repository.generate.tables.UserPermissionBehavior;
import oceanstars.ecommerce.user.repository.generate.tables.pojos.UserPermissionBehaviorPojo;
import oceanstars.ecommerce.user.repository.generate.tables.pojos.UserPermissionPojo;
import oceanstars.ecommerce.user.repository.permission.view.bo.PermissionView;
import org.jooq.Condition;
import org.jooq.impl.DSL;
import org.jooq.impl.DefaultDSLContext;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

/**
 * 权限视图数据访问对象
 *
 * @author Clover
 * @version 1.0.0
 * @since 2024/5/13 18:41
 */
@Repository
public class PermissionViewDao {

  /**
   * 权限表
   */
  final static UserPermission T_PERMISSION = UserPermission.USER_PERMISSION.as("perm");

  /**
   * 权限行为表
   */
  final static UserPermissionBehavior T_BEHAVIOR = UserPermissionBehavior.USER_PERMISSION_BEHAVIOR.as("bev");

  /**
   * Jooq数据库操作对象
   */
  @Resource
  private DefaultDSLContext dsl;

  /**
   * 根据查询条件查询权限视图信息
   *
   * @param condition 查询条件
   * @return 权限视图信息
   */
  public List<PermissionView> fetch(final ICondition condition) {

    // 初始化查询条件
    Condition searchCondition = DSL.trueCondition();

    // 转换查询条件为权限查询条件
    final PermissionFetchCondition fetchCondition = (PermissionFetchCondition) condition;

    // 权限ID
    if (null != fetchCondition.getId()) {
      searchCondition = searchCondition.and(T_PERMISSION.ID.eq(fetchCondition.getId()));
    }
    // 权限名称
    if (null != fetchCondition.getName()) {
      searchCondition = searchCondition.and(T_PERMISSION.NAME.likeIgnoreCase(fetchCondition.getName().trim()));
    }
    // 权限类型
    if (!CollectionUtils.isEmpty(fetchCondition.getTypes())) {
      if (fetchCondition.getTypes().size() == 1) {
        searchCondition = searchCondition.and(T_PERMISSION.TYPE.eq(fetchCondition.getTypes().stream().findFirst().orElseThrow().key().shortValue()));
      } else {
        searchCondition = searchCondition.and(T_PERMISSION.TYPE.in(fetchCondition.getTypes()));
      }
    }
    // 权限逻辑有效标志位
    if (null != fetchCondition.getEnabled()) {
      searchCondition = searchCondition.and(T_PERMISSION.ENABLED.eq(fetchCondition.getEnabled()));
    }
    // 权限资源ID
    if (!CollectionUtils.isEmpty(fetchCondition.getResources())) {
      if (fetchCondition.getResources().size() == 1) {
        searchCondition = searchCondition.and(T_BEHAVIOR.RESOURCE.eq(fetchCondition.getResources().stream().findFirst().orElseThrow()));
      } else {
        searchCondition = searchCondition.and(T_BEHAVIOR.RESOURCE.in(fetchCondition.getResources()));
      }
    }
    // 权限操作行为掩码，确认需要查询的权限操作行为
    byte operationMask = 0x00;
    // 权限操作行为期望，确认需要查询的权限操作行为状态
    byte operationExpert = 0x00;
    // 权限操作行为：是否禁用
    if (null != fetchCondition.getForbidden()) {
      operationMask = (byte) (operationMask | PermissionOperationType.PROHIBIT.key());
      if (fetchCondition.getForbidden()) {
        operationExpert = (byte) (operationExpert | PermissionOperationType.PROHIBIT.key());
      }
    }
    // 权限操作行为：是否允许读取
    if (null != fetchCondition.getCanRead()) {
      operationMask = (byte) (operationMask | PermissionOperationType.READ.key());
      if (fetchCondition.getCanRead()) {
        operationExpert = (byte) (operationExpert | PermissionOperationType.READ.key());
      }
    }
    // 权限操作行为：是否允许创建
    if (null != fetchCondition.getCanCreate()) {
      operationMask = (byte) (operationMask | PermissionOperationType.WRITE.key());
      if (fetchCondition.getCanCreate()) {
        operationExpert = (byte) (operationExpert | PermissionOperationType.WRITE.key());
      }
    }
    // 权限操作行为：是否允许更新
    if (null != fetchCondition.getCanUpdate()) {
      operationMask = (byte) (operationMask | PermissionOperationType.UPDATE.key());
      if (fetchCondition.getCanUpdate()) {
        operationExpert = (byte) (operationExpert | PermissionOperationType.UPDATE.key());
      }
    }
    // 权限操作行为：是否允许删除
    if (null != fetchCondition.getCanDelete()) {
      operationMask = (byte) (operationMask | PermissionOperationType.DELETE.key());
      if (fetchCondition.getCanDelete()) {
        operationExpert = (byte) (operationExpert | PermissionOperationType.DELETE.key());
      }
    }
    searchCondition = searchCondition.and(T_BEHAVIOR.OPT.bitAnd(operationMask).eq(operationExpert));
    // 权限创建开始时间
    if (null != fetchCondition.getCreateStartTime()) {
      searchCondition = searchCondition.and(T_PERMISSION.CREATE_AT.ge(fetchCondition.getCreateStartTime()));
    }
    // 权限创建结束时间
    if (null != fetchCondition.getCreateEndTime()) {
      searchCondition = searchCondition.and(T_PERMISSION.CREATE_AT.le(fetchCondition.getCreateEndTime()));
    }
    // 权限更新开始时间
    if (null != fetchCondition.getUpdateStartTime()) {
      searchCondition = searchCondition.and(T_PERMISSION.UPDATE_AT.ge(fetchCondition.getUpdateStartTime()));
    }
    // 权限更新结束时间
    if (null != fetchCondition.getUpdateEndTime()) {
      searchCondition = searchCondition.and(T_PERMISSION.UPDATE_AT.le(fetchCondition.getUpdateEndTime()));
    }

    // 查询
    final Map<UserPermissionPojo, List<UserPermissionBehaviorPojo>> results = dsl
        .select(T_PERMISSION.fields())
        .select(T_BEHAVIOR.fields())
        .from(T_PERMISSION)
        .join(T_BEHAVIOR).on(T_PERMISSION.ID.eq(T_BEHAVIOR.PERMISSION))
        .where(searchCondition)
        .collect(Collectors.groupingBy(
            record -> record.into(T_PERMISSION).into(UserPermissionPojo.class),
            Collectors.mapping(record -> record.into(T_BEHAVIOR).into(UserPermissionBehaviorPojo.class), Collectors.toList())
        ));

    // 查询结果为空，返回空
    if (CollectionUtils.isEmpty(results)) {
      return null;
    }

    // 遍历查询结果, 构建权限视图信息
    final List<PermissionView> permissionViews = new ArrayList<>();
    results.forEach((permission, behaviors) -> {
      final PermissionView permissionView = new PermissionView();
      permissionView.setPermission(permission);
      permissionView.setBehaviors(behaviors);
      permissionViews.add(permissionView);
    });

    // 排序
    Collections.sort(permissionViews);

    return permissionViews;
  }
}
