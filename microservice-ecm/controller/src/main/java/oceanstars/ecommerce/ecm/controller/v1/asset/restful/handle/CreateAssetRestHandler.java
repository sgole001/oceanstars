package oceanstars.ecommerce.ecm.controller.v1.asset.restful.handle;

import com.google.protobuf.Any;
import com.google.protobuf.GeneratedMessageV3;
import com.google.protobuf.Message;
import oceanstars.ecommerce.common.cqrs.Bus;
import oceanstars.ecommerce.common.restful.BaseRestHandler;
import oceanstars.ecommerce.common.restful.BaseRestResponseData;
import oceanstars.ecommerce.common.restful.RestResponseMessage;
import oceanstars.ecommerce.ecm.api.rest.v1.request.asset.CreateAssetRequestMessage;
import oceanstars.ecommerce.ecm.api.rpc.v1.dto.asset.EcmCreateAssetCommand;
import oceanstars.ecommerce.ecm.api.rpc.v1.dto.asset.EcmCreateAssetResult;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

/**
 * Restful请求处理: 创建资产
 *
 * @author Clover
 * @version 1.0.0
 * @since 2024/4/17 17:55
 */
@Component
public class CreateAssetRestHandler extends BaseRestHandler<CreateAssetRequestMessage<?>> {

  /**
   * CQRS处理总线
   */
  private final Bus cqrsGateway;

  /**
   * 构造函数
   *
   * @param cqrsGateway CQRS处理总线
   */
  public CreateAssetRestHandler(Bus cqrsGateway) {
    this.cqrsGateway = cqrsGateway;
  }

  @Override
  public GeneratedMessageV3[] parsingRequestMessage(CreateAssetRequestMessage<? extends Message> restRequestMessage) {

    // 构建创建资产命令请求参数
    final EcmCreateAssetCommand createAssetCommand = EcmCreateAssetCommand.newBuilder()
        // 资产名称
        .setName(restRequestMessage.getName())
        // 资产类型
        .setType(restRequestMessage.getType())
        // 资产描述
        .setDescription(restRequestMessage.getDescription())
        // 资产原生信息
        .setRawData(Any.pack(restRequestMessage.getRawData()))
        // 执行构建
        .build();

    return new GeneratedMessageV3[]{createAssetCommand};
  }

  @Override
  public GeneratedMessageV3 process(GeneratedMessageV3[] messages) {
    return cqrsGateway.executeCommand(messages[0]);
  }

  @Override
  public RestResponseMessage packageResponseMessage(GeneratedMessageV3 result) {

    // 获取创建资产命令处理结果
    final EcmCreateAssetResult createAssetResult = (EcmCreateAssetResult) result;

    return RestResponseMessage.newBuilder(HttpStatus.OK)
        // 设置响应数据
        .data(BaseRestResponseData.newBuilder().id(String.valueOf(createAssetResult.getId())).build())
        // 执行构建
        .build();
  }
}
