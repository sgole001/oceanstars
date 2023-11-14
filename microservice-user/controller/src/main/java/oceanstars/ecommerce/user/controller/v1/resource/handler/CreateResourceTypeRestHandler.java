package oceanstars.ecommerce.user.controller.v1.resource.handler;

import com.google.protobuf.GeneratedMessageV3;
import jakarta.annotation.Resource;
import oceanstars.ecommerce.common.cqrs.CqrsGateway;
import oceanstars.ecommerce.common.restful.BaseRestHandler;
import oceanstars.ecommerce.common.restful.RestResponseMessage;
import oceanstars.ecommerce.user.api.rest.v1.request.resource.CreateResourceTypeRequestMessage;
import oceanstars.ecommerce.user.api.rest.v1.response.resource.CreateResourceTypeResponseMessage;
import oceanstars.ecommerce.user.api.rest.v1.response.resource.data.ResourceTypeData;
import oceanstars.ecommerce.user.api.rpc.v1.dto.CreateResourceTypeCommand;
import oceanstars.ecommerce.user.api.rpc.v1.dto.CreateResourceTypeResult;
import oceanstars.ecommerce.user.api.rpc.v1.dto.ResourceLink;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

/**
 * 创建权限资源类型的Restful请求处理
 *
 * @author Clover
 * @version 1.0.0
 * @since 2022/1/14 11:59 AM
 */
@Component(value = "createResourceTypeRestHandler")
public class CreateResourceTypeRestHandler extends BaseRestHandler<CreateResourceTypeRequestMessage> {

  /**
   * CQRS处理总线
   */
  @Resource(name = "cqrsGateway")
  private CqrsGateway cqrsGateway;

  @Override
  public GeneratedMessageV3[] parsingRequestMessage(final CreateResourceTypeRequestMessage restRequestMessage) {

    // 构建资源数据获取链接信息
    final ResourceLink resourceLink = ResourceLink.newBuilder()
        // 资源数据API路径
        .setHref(restRequestMessage.getHref())
        // 资源数据API的HTTP请求方法
        .setMethod(restRequestMessage.getMethod())
        // 资源数据PI的HTTP请求Body(JSON字符串)
        .setBody(restRequestMessage.getBody())
        // 实施构建
        .build();

    // 构建创建权限资源类型命令请求参数
    final CreateResourceTypeCommand createResourceTypeCommand = CreateResourceTypeCommand.newBuilder()
        // 资源类型名
        .setName(restRequestMessage.getName())
        // 资源类型功能说明
        .setDesc(restRequestMessage.getDesc())
        // 资源数据获取链接信息
        .setLink(resourceLink)
        // 实施构建
        .build();

    return new GeneratedMessageV3[]{createResourceTypeCommand};
  }

  @Override
  public GeneratedMessageV3[] process(final GeneratedMessageV3[] messages) {

    // 获取创建权限资源类型命令请求参数
    final CreateResourceTypeCommand createResourceTypeCommand = (CreateResourceTypeCommand) messages[0];

    // 执行命令
    final CreateResourceTypeResult createResourceTypeResult = cqrsGateway.executeCommand(createResourceTypeCommand);

    return new GeneratedMessageV3[]{createResourceTypeResult};
  }

  @Override
  public RestResponseMessage packageResponseMessage(final GeneratedMessageV3[] results) {

    // 获取创建权限资源类型命令处理结果
    final CreateResourceTypeResult createResourceTypeResult = (CreateResourceTypeResult) results[0];

    final ResourceTypeData resourceTypeData = new ResourceTypeData();
    resourceTypeData.setCode(createResourceTypeResult.getIdentifier());

    final CreateResourceTypeResponseMessage responseMessage = new CreateResourceTypeResponseMessage(HttpStatus.OK);
    responseMessage.setData(resourceTypeData);

    return responseMessage;
  }
}
