package oceanstars.ecommerce.ecm.controller.v1.tag.handle;

import com.google.protobuf.GeneratedMessageV3;
import jakarta.annotation.Resource;
import oceanstars.ecommerce.common.cqrs.CqrsGateway;
import oceanstars.ecommerce.common.restful.BaseRestHandler;
import oceanstars.ecommerce.common.restful.BaseRestResponseData;
import oceanstars.ecommerce.common.restful.RestResponseMessage;
import oceanstars.ecommerce.ecm.api.rest.v1.request.tag.CreateTagRequestMessage;
import oceanstars.ecommerce.ecm.api.rpc.v1.dto.tag.EcmCreateTagCommand;
import oceanstars.ecommerce.ecm.api.rpc.v1.dto.tag.EcmCreateTagResult;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

/**
 * Restful请求处理: 创建标签
 *
 * @author Clover
 * @version 1.0.0
 * @since 2024/1/25 12:43
 */
@Component
public class CreateTagRestHandler extends BaseRestHandler<CreateTagRequestMessage> {

  /**
   * CQRS处理总线
   */
  @Resource(name = "cqrsGateway")
  private CqrsGateway cqrsGateway;

  @Override
  public GeneratedMessageV3[] parsingRequestMessage(CreateTagRequestMessage restRequestMessage) {

    // 构建创建标签命令请求参数
    final EcmCreateTagCommand createTagCommand = EcmCreateTagCommand.newBuilder()
        // 标签名称
        .setName(restRequestMessage.getName())
        // 标签展示名称
        .setDisplayName(restRequestMessage.getDisplayName())
        // 标签描述
        .setDescription(restRequestMessage.getDescription())
        // 标签URL
        .setUrl(restRequestMessage.getUrl())
        // 标签图标
        .setIcon(restRequestMessage.getIcon())
        // 执行构建
        .build();

    return new GeneratedMessageV3[]{createTagCommand};
  }

  @Override
  public GeneratedMessageV3[] process(GeneratedMessageV3[] messages) {
    return cqrsGateway.executeCommand(messages[0]);
  }

  @Override
  public RestResponseMessage packageResponseMessage(GeneratedMessageV3[] results) {

    // 获取创建内容命令处理结果
    final EcmCreateTagResult createTagResult = (EcmCreateTagResult) results[0];

    return RestResponseMessage.newBuilder(HttpStatus.OK)
        // 设置响应数据
        .data(BaseRestResponseData.newBuilder().id(String.valueOf(createTagResult.getId())).build())
        // 执行构建
        .build();
  }
}
