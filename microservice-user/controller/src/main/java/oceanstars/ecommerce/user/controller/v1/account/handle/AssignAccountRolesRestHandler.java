package oceanstars.ecommerce.user.controller.v1.account.handle;

import com.google.protobuf.GeneratedMessageV3;
import oceanstars.ecommerce.common.cqrs.Bus;
import oceanstars.ecommerce.common.restful.BaseRestHandler;
import oceanstars.ecommerce.common.restful.BaseRestResponseData;
import oceanstars.ecommerce.common.restful.RestResponseMessage;
import oceanstars.ecommerce.user.api.rest.v1.request.account.AssignAccountRolesRequestMessage;
import oceanstars.ecommerce.user.api.rpc.v1.dto.account.UserAssignAccountRolesCommand;
import oceanstars.ecommerce.user.api.rpc.v1.dto.account.UserAssignAccountRolesResult;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

/**
 * 分配账号角色的Restful请求处理
 *
 * @author Clover
 * @version 1.0.0
 * @since 2024/5/31 16:39
 */
@Component
public class AssignAccountRolesRestHandler extends BaseRestHandler<AssignAccountRolesRequestMessage> {

  /**
   * CQRS处理总线
   */
  private final Bus cqrsGateway;

  /**
   * 构造函数
   *
   * @param cqrsGateway CQRS处理总线
   */
  public AssignAccountRolesRestHandler(Bus cqrsGateway) {
    this.cqrsGateway = cqrsGateway;
  }

  @Override
  public GeneratedMessageV3[] parsingRequestMessage(AssignAccountRolesRequestMessage restRequestMessage) {

    // 构建分配账号角色命令请求参数
    final UserAssignAccountRolesCommand assignAccountRolesCommand = UserAssignAccountRolesCommand.newBuilder()
        // 账号ID
        .setAccount(restRequestMessage.getAccount())
        // 角色ID列表
        .addAllRoles(restRequestMessage.getRoles())
        // 实施构建
        .build();

    return new GeneratedMessageV3[]{assignAccountRolesCommand};
  }

  @Override
  public GeneratedMessageV3 process(GeneratedMessageV3[] messages) {

    // 获取分配账号角色命令请求参数
    final UserAssignAccountRolesCommand assignAccountRolesCommand = (UserAssignAccountRolesCommand) messages[0];

    // 执行命令
    return cqrsGateway.executeCommand(assignAccountRolesCommand);
  }

  @Override
  public RestResponseMessage packageResponseMessage(GeneratedMessageV3 result) {

    // 获取创建账号命令处理结果
    final UserAssignAccountRolesResult assignAccountRolesResult = (UserAssignAccountRolesResult) result;

    return RestResponseMessage.newBuilder(HttpStatus.OK)
        // 设置响应数据
        .data(BaseRestResponseData.newBuilder().id(String.valueOf(assignAccountRolesResult.getId())).build())
        // 执行构建
        .build();
  }
}
