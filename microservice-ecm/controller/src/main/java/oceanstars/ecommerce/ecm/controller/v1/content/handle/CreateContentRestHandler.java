package oceanstars.ecommerce.ecm.controller.v1.content.handle;

import com.google.protobuf.GeneratedMessageV3;
import jakarta.annotation.Resource;
import oceanstars.ecommerce.common.cqrs.CqrsGateway;
import oceanstars.ecommerce.common.restful.BaseRestHandler;
import oceanstars.ecommerce.common.restful.BaseRestResponseData;
import oceanstars.ecommerce.common.restful.RestResponseMessage;
import oceanstars.ecommerce.ecm.api.rest.v1.request.content.CreateContentRequestMessage;
import oceanstars.ecommerce.ecm.api.rpc.v1.dto.content.EcmCreateContentCommand;
import oceanstars.ecommerce.ecm.api.rpc.v1.dto.content.EcmCreateContentResult;
import oceanstars.ecommerce.ecm.application.cqrs.handler.strategy.impl.ContentRawDataStrategyContext;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

/**
 * Restful请求处理: 创建内容
 *
 * @author Clover
 * @version 1.0.0
 * @since 2024/1/17 15:34
 */
@Component
public class CreateContentRestHandler extends BaseRestHandler<CreateContentRequestMessage> {

  /**
   * CQRS处理总线
   */
  @Resource(name = "cqrsGateway")
  private CqrsGateway cqrsGateway;

  @Override
  public GeneratedMessageV3[] parsingRequestMessage(CreateContentRequestMessage restRequestMessage) {

    // 构建创建内容命令请求参数
    final EcmCreateContentCommand createContentCommand = EcmCreateContentCommand.newBuilder()
        // 内容名称
        .setName(restRequestMessage.getName())
        // 内容类型
        .setType(restRequestMessage.getType())
        // 内容展示名称
        .setDisplayName(restRequestMessage.getDisplayName())
        // 内容描述
        .setDescription(restRequestMessage.getDescription())
        // 内容标签
        .addAllTags(restRequestMessage.getTags())
        // 内容分类
        .addAllCategories(restRequestMessage.getCategories())
        // 不同内容类型特有数据
        .setRawData(new ContentRawDataStrategyContext(restRequestMessage.getType()).pack(restRequestMessage.getRawData()))
        // 执行构建
        .build();

    return new GeneratedMessageV3[]{createContentCommand};
  }

  @Override
  public GeneratedMessageV3[] process(GeneratedMessageV3[] messages) {
    return cqrsGateway.executeCommand(messages[0]);
  }

  @Override
  public RestResponseMessage packageResponseMessage(GeneratedMessageV3[] results) {

    // 获取创建内容命令处理结果
    final EcmCreateContentResult createContentResult = (EcmCreateContentResult) results[0];

    return RestResponseMessage.newBuilder(HttpStatus.OK)
        // 设置响应数据
        .data(BaseRestResponseData.newBuilder().id(String.valueOf(createContentResult.getId())).build())
        // 执行构建
        .build();
  }
}
