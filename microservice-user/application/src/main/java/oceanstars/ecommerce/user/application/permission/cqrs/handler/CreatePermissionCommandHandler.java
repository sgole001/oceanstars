package oceanstars.ecommerce.user.application.permission.cqrs.handler;

import java.util.List;
import oceanstars.ecommerce.common.cqrs.ICommandHandler;
import oceanstars.ecommerce.common.domain.event.EventBus;
import oceanstars.ecommerce.user.api.rpc.v1.dto.permission.UserCreatePermissionCommand;
import oceanstars.ecommerce.user.api.rpc.v1.dto.permission.UserCreatePermissionResult;
import oceanstars.ecommerce.user.api.rpc.v1.dto.permission.UserPermissionBehavior;
import oceanstars.ecommerce.user.constant.enums.UserEnums.PermissionType;
import oceanstars.ecommerce.user.domain.permission.entity.Permission;
import oceanstars.ecommerce.user.domain.permission.entity.PermissionBehavior;
import oceanstars.ecommerce.user.domain.permission.repository.PermissionRepository;
import org.springframework.stereotype.Component;

/**
 * 创建权限资源类型命令处理类
 *
 * @author Clover
 * @version 1.0.0
 * @since 2022/1/17 1:52 PM
 */
@Component(value = "createPermissionCommandHandler")
public class CreatePermissionCommandHandler implements ICommandHandler<UserCreatePermissionResult, UserCreatePermissionCommand> {

  /**
   * 事件总线
   */
  private final EventBus eventGateway;

  /**
   * 权限资源仓储
   */
  private final PermissionRepository permissionRepository;

  /**
   * 构造函数
   *
   * @param eventGateway         事件总线
   * @param permissionRepository 权限资源仓储
   */
  public CreatePermissionCommandHandler(EventBus eventGateway, PermissionRepository permissionRepository) {
    this.eventGateway = eventGateway;
    this.permissionRepository = permissionRepository;
  }

  @Override
  public UserCreatePermissionResult handle(UserCreatePermissionCommand command) {

    // 获取权限操作行为列表
    final List<UserPermissionBehavior> behaviors = command.getBehaviorsList();

    // 构建权限实体信息
    final Permission permission = Permission.newBuilder(command.getName(), PermissionType.of(command.getType()))
        // 权限描述
        .desc(command.getDesc())
        // 权限逻辑有效标志位
        .enabled(Boolean.TRUE)
        // 实施构建
        .build();

    // 构建权限操作行为实体列表
    final List<PermissionBehavior> permissionBehaviors =
        behaviors.stream().map(behavior -> PermissionBehavior.newBuilder(permission, behavior.getResource())
            // 是否禁用
            .isForbidden(behavior.getIsForbidden())
            // 是否允许读取
            .canRead(behavior.getCanRead())
            // 是否允许创建
            .canCreate(behavior.getCanCreate())
            // 是否允许更新
            .canUpdate(behavior.getCanUpdate())
            // 是否允许删除
            .canDelete(behavior.getCanDelete())
            // 实施构建
            .build()).toList();
    // 设置权限操作行为
    permission.setBehaviors(permissionBehaviors);

    // 创建权限
    this.permissionRepository.save(permission);

    // 发布领域事件
//    eventGateway.publish(new PermissionCreated(permission, new PermissionCreatedPayload(permission.getDelegator().getId())));

    return UserCreatePermissionResult.newBuilder().setIdentifier(permission.getDelegator().getId()).build();
  }
}
