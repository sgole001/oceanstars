package oceanstars.ecommerce.user.application.role.cqrs.handler;

import java.util.HashSet;
import oceanstars.ecommerce.common.cqrs.ICommandHandler;
import oceanstars.ecommerce.common.domain.event.EventBus;
import oceanstars.ecommerce.user.api.rpc.v1.dto.role.UserCreateRoleCommand;
import oceanstars.ecommerce.user.api.rpc.v1.dto.role.UserCreateRoleResult;
import oceanstars.ecommerce.user.domain.role.entity.Role;
import oceanstars.ecommerce.user.domain.role.repository.RoleRepository;
import org.springframework.stereotype.Component;

/**
 * 创建角色命令处理类
 *
 * @author Clover
 * @version 1.0.0
 * @since 2024/5/23 17:32
 */
@Component
public class CreateRoleCommandHandler implements ICommandHandler<UserCreateRoleResult, UserCreateRoleCommand> {

  /**
   * 事件总线
   */
  private final EventBus eventGateway;

  /**
   * 角色资源仓储
   */
  private final RoleRepository roleRepository;

  /**
   * 构造函数
   *
   * @param eventGateway   事件总线
   * @param roleRepository 角色资源仓储
   */
  public CreateRoleCommandHandler(EventBus eventGateway, RoleRepository roleRepository) {
    this.eventGateway = eventGateway;
    this.roleRepository = roleRepository;
  }

  @Override
  public UserCreateRoleResult handle(UserCreateRoleCommand command) {

    // 构建角色实体信息
    final Role role = Role.newBuilder(command.getName())
        // 角色描述
        .desc(command.getDesc())
        // 父角色（隶属关系）列表
        .parents(new HashSet<>(command.getParentIdsList()))
        // 角色权限列表
        .permissions(new HashSet<>(command.getPermissionIdsList()))
        // 角色逻辑有效标志位
        .enabled(Boolean.TRUE)
        // 实施构建
        .build();

    // 保存角色实体信息
    this.roleRepository.save(role);

    // 发布领域事件
//    this.eventGateway.publish(new RoleCreated(role, new RoleCreatedPayload(role.getDelegator().getId())));

    return UserCreateRoleResult.newBuilder().setIdentifier(role.getDelegator().getId()).build();
  }
}
