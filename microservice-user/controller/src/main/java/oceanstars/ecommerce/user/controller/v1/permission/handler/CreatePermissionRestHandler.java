package oceanstars.ecommerce.user.controller.v1.permission.handler;

import com.google.protobuf.GeneratedMessageV3;
import java.util.List;
import oceanstars.ecommerce.common.cqrs.Bus;
import oceanstars.ecommerce.common.restful.BaseRestHandler;
import oceanstars.ecommerce.common.restful.BaseRestResponseData;
import oceanstars.ecommerce.common.restful.RestResponseMessage;
import oceanstars.ecommerce.user.api.rest.v1.request.permission.CreatePermissionRequestMessage;
import oceanstars.ecommerce.user.api.rpc.v1.dto.permission.UserCreatePermissionCommand;
import oceanstars.ecommerce.user.api.rpc.v1.dto.permission.UserCreatePermissionResult;
import oceanstars.ecommerce.user.api.rpc.v1.dto.permission.UserPermissionBehavior;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

/**
 * 创建权限的Restful请求处理
 *
 * @author Clover
 * @version 1.0.0
 * @since 2022/1/14 11:59 AM
 */
@Component(value = "createPermissionRestHandler")
public class CreatePermissionRestHandler extends BaseRestHandler<CreatePermissionRequestMessage> {

  /**
   * CQRS处理总线
   */
  private final Bus cqrsGateway;

  /**
   * 构造函数
   *
   * @param cqrsGateway CQRS处理总线
   */
  public CreatePermissionRestHandler(Bus cqrsGateway) {
    this.cqrsGateway = cqrsGateway;
  }

  @Override
  public GeneratedMessageV3[] parsingRequestMessage(final CreatePermissionRequestMessage restRequestMessage) {

    // 构建权限操作行为列表
    final List<UserPermissionBehavior> behaviors =
        restRequestMessage.getBehaviors().stream().map(behavior -> UserPermissionBehavior.newBuilder()
            // 权限资源ID
            .setResource(behavior.getResource())
            // 是否禁用
            .setIsForbidden(behavior.getForbidden())
            // 是否可读
            .setCanRead(behavior.getCanRead())
            // 是否可创建
            .setCanCreate(behavior.getCanCreate())
            // 是否可更新
            .setCanUpdate(behavior.getCanUpdate())
            // 是否可删除
            .setCanDelete(behavior.getCanDelete())
            // 执行构建
            .build()).toList();

    // 构建创建权限命令请求参数
    final UserCreatePermissionCommand createPermissionCommand = UserCreatePermissionCommand.newBuilder()
        // 权限名称
        .setName(restRequestMessage.getName())
        // 权限类型
        .setType(restRequestMessage.getType())
        // 权限描述
        .setDesc(restRequestMessage.getDesc())
        // 权限操作行为列表
        .addAllBehaviors(behaviors)
        // 实施构建
        .build();

    return new GeneratedMessageV3[]{createPermissionCommand};
  }

  @Override
  public GeneratedMessageV3 process(final GeneratedMessageV3[] messages) {

    // 获取创建权限命令请求参数
    final UserCreatePermissionCommand createPermissionCommand = (UserCreatePermissionCommand) messages[0];

    // 执行命令
    return cqrsGateway.executeCommand(createPermissionCommand);
  }

  @Override
  public RestResponseMessage packageResponseMessage(GeneratedMessageV3 result) {

    // 获取创建权限命令处理结果
    final UserCreatePermissionResult createPermissionResult = (UserCreatePermissionResult) result;

    return RestResponseMessage.newBuilder(HttpStatus.OK)
        // 设置响应数据
        .data(BaseRestResponseData.newBuilder().id(String.valueOf(createPermissionResult.getIdentifier())).build())
        // 执行构建
        .build();
  }
}
