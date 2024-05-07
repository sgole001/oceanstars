package oceanstars.ecommerce.ecm.controller.v1.asset.restful.strategy.impl.ip;

import com.google.protobuf.Message;
import oceanstars.ecommerce.common.restful.RestRequestMessage;
import oceanstars.ecommerce.ecm.api.rest.v1.request.asset.CreateAssetRequestMessage;
import oceanstars.ecommerce.ecm.api.rest.v1.request.asset.ip.CreateFunctionRequestMessage;
import oceanstars.ecommerce.ecm.api.rpc.v1.dto.asset.EcmAssetFunction;
import oceanstars.ecommerce.ecm.constant.enums.EcmEnums.AssetType;
import oceanstars.ecommerce.ecm.controller.v1.asset.restful.strategy.AssetRequestMessageStrategy;

/**
 * 知识产权资产原生数据处理策略
 *
 * @author Clover
 * @version 1.0.0
 * @since 2024/4/17 18:36
 */
public class FunctionStrategy implements AssetRequestMessageStrategy {

  @Override
  public CreateAssetRequestMessage<? extends Message> create(RestRequestMessage message) {

    // 转换请求消息
    final CreateFunctionRequestMessage requestMessage = (CreateFunctionRequestMessage) message;

    // 创建资产请求消息
    final CreateAssetRequestMessage<EcmAssetFunction> assetRequestMessage = new CreateAssetRequestMessage<>();
    // 资产名称
    assetRequestMessage.setName(requestMessage.getName());
    // 资产类型
    assetRequestMessage.setType(AssetType.INTELLECTUAL_PROPERTY_FUNCTION.key());
    // 资产描述
    assetRequestMessage.setDescription(requestMessage.getDescription());
    // 资产原生信息设定
    assetRequestMessage.setRawData(EcmAssetFunction.newBuilder()
        // 功能隶属 - 通过内容ID关联隶属关系
        .setParent(requestMessage.getParent())
        // 执行构建
        .build());

    return assetRequestMessage;
  }
}
