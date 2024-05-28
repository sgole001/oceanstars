package oceanstars.ecommerce.user.controller.v1.role.handler;

import com.google.protobuf.GeneratedMessageV3;
import oceanstars.ecommerce.common.cqrs.Bus;
import oceanstars.ecommerce.common.restful.BaseRestHandler;
import oceanstars.ecommerce.common.restful.BaseRestResponseData;
import oceanstars.ecommerce.common.restful.RestResponseMessage;
import oceanstars.ecommerce.user.api.rest.v1.request.role.CreateRoleRequestMessage;
import oceanstars.ecommerce.user.api.rpc.v1.dto.role.UserCreateRoleCommand;
import oceanstars.ecommerce.user.api.rpc.v1.dto.role.UserCreateRoleResult;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

/**
 * 创建角色的Restful请求处理
 *
 * @author Clover
 * @version 1.0.0
 * @since 2024/5/23 15:36
 */
@Component
public class CreateRoleRestHandler extends BaseRestHandler<CreateRoleRequestMessage> {

  /**
   * CQRS处理总线
   */
  private final Bus cqrsGateway;

  /**
   * 构造函数
   *
   * @param cqrsGateway CQRS处理总线
   */
  public CreateRoleRestHandler(Bus cqrsGateway) {
    this.cqrsGateway = cqrsGateway;
  }

  @Override
  public GeneratedMessageV3[] parsingRequestMessage(CreateRoleRequestMessage restRequestMessage) {

    // 构建创建角色命令请求参数
    final UserCreateRoleCommand createRoleCommand = UserCreateRoleCommand.newBuilder()
        // 角色名
        .setName(restRequestMessage.getName())
        // 权限描述
        .setDesc(restRequestMessage.getDesc())
        // 父角色（隶属关系）列表
        .addAllParentIds(restRequestMessage.getParents())
        // 角色权限列表
        .addAllPermissionIds(restRequestMessage.getPermissions())
        // 实施构建
        .build();

    return new GeneratedMessageV3[]{createRoleCommand};
  }

  @Override
  public GeneratedMessageV3 process(GeneratedMessageV3[] messages) {

    // 获取创建角色命令请求参数
    final UserCreateRoleCommand createRoleCommand = (UserCreateRoleCommand) messages[0];

    // 执行命令
    return cqrsGateway.executeCommand(createRoleCommand);
  }

  @Override
  public RestResponseMessage packageResponseMessage(GeneratedMessageV3 result) {

    // 获取创建角色命令处理结果
    final UserCreateRoleResult createRoleResult = (UserCreateRoleResult) result;

    return RestResponseMessage.newBuilder(HttpStatus.OK)
        // 设置响应数据
        .data(BaseRestResponseData.newBuilder().id(String.valueOf(createRoleResult.getIdentifier())).build())
        // 执行构建
        .build();
  }
}
