package oceanstars.ecommerce.ecm.controller.v1.category.restful.handle;

import com.google.protobuf.GeneratedMessageV3;
import oceanstars.ecommerce.common.cqrs.Bus;
import oceanstars.ecommerce.common.restful.BaseRestHandler;
import oceanstars.ecommerce.common.restful.BaseRestResponseData;
import oceanstars.ecommerce.common.restful.RestResponseMessage;
import oceanstars.ecommerce.ecm.api.rest.v1.request.category.CreateCategoryRequestMessage;
import oceanstars.ecommerce.ecm.api.rpc.v1.dto.category.EcmCreateCategoryCommand;
import oceanstars.ecommerce.ecm.api.rpc.v1.dto.category.EcmCreateCategoryResult;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

/**
 * Restful请求处理: 创建内容分类
 *
 * @author Clover
 * @version 1.0.0
 * @since 2024/1/15 15:54
 */
@Component
public class CreateCategoryRestHandler extends BaseRestHandler<CreateCategoryRequestMessage> {

  /**
   * CQRS处理总线
   */
  private final Bus cqrsGateway;

  /**
   * 构造函数
   *
   * @param cqrsGateway CQRS处理总线
   */
  public CreateCategoryRestHandler(Bus cqrsGateway) {
    this.cqrsGateway = cqrsGateway;
  }

  @Override
  public GeneratedMessageV3[] parsingRequestMessage(CreateCategoryRequestMessage restRequestMessage) {

    // 构建创建分类命令
    final EcmCreateCategoryCommand.Builder createCategoryCommandBuilder = EcmCreateCategoryCommand.newBuilder()
        // 分类名称
        .setName(restRequestMessage.getName())
        // 分类展示名称
        .setDisplayName(restRequestMessage.getDisplayName())
        // 分类描述
        .setDescription(restRequestMessage.getDescription())
        // 分类链接
        .setUrl(restRequestMessage.getUrl());
    // 内容分类父分类
    if (!CollectionUtils.isEmpty(restRequestMessage.getParentIds())) {
      createCategoryCommandBuilder.addAllParents(restRequestMessage.getParentIds());
    }

    return new GeneratedMessageV3[]{createCategoryCommandBuilder.build()};
  }

  @Override
  public GeneratedMessageV3 process(GeneratedMessageV3[] messages) {
    return cqrsGateway.executeCommand(messages[0]);
  }

  @Override
  public RestResponseMessage packageResponseMessage(GeneratedMessageV3 results) {

    // 获取创建内容命令处理结果
    final EcmCreateCategoryResult createCategoryResult = (EcmCreateCategoryResult) results;

    return RestResponseMessage.newBuilder(HttpStatus.OK)
        // 设置响应数据
        .data(BaseRestResponseData.newBuilder().id(String.valueOf(createCategoryResult.getId())).build())
        // 执行构建
        .build();
  }
}
