package oceanstars.ecommerce.ecm.controller.v1.asset.restful.strategy;

import com.google.protobuf.Message;
import oceanstars.ecommerce.common.restful.RestRequestMessage;
import oceanstars.ecommerce.ecm.api.rest.v1.request.asset.CreateAssetRequestMessage;

/**
 * 资产Restful请求消息处理策略
 *
 * @author Clover
 * @version 1.0.0
 * @since 2024/4/17 18:13
 */
public interface AssetRequestMessageStrategy {

  /**
   * 资产创建请求消息统一化处理
   *
   * @param message 资产创建请求消息
   * @return 资产创建请求消息统一化接口数据
   */
  CreateAssetRequestMessage<? extends Message> create(RestRequestMessage message);
}
