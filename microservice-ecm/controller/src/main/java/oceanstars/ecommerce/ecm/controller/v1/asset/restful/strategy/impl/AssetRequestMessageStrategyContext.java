package oceanstars.ecommerce.ecm.controller.v1.asset.restful.strategy.impl;

import com.google.protobuf.Message;
import oceanstars.ecommerce.common.restful.RestRequestMessage;
import oceanstars.ecommerce.ecm.api.rest.v1.request.asset.CreateAssetRequestMessage;
import oceanstars.ecommerce.ecm.constant.enums.EcmEnums.AssetType;
import oceanstars.ecommerce.ecm.controller.v1.asset.restful.strategy.AssetRequestMessageStrategy;
import oceanstars.ecommerce.ecm.controller.v1.asset.restful.strategy.impl.ip.FunctionStrategy;

/**
 * 资产请求消息处理策略上下文
 *
 * @author Clover
 * @version 1.0.0
 * @since 2024/4/23 16:18
 */
public class AssetRequestMessageStrategyContext {

  /**
   * 资产请求消息处理策略
   */
  private AssetRequestMessageStrategy strategy;

  /**
   * 构造函数
   *
   * @param type 资产类型
   */
  public AssetRequestMessageStrategyContext(final AssetType type) {

    if (null == type) {
      throw new IllegalArgumentException("不支持的资产类型");
    }

    switch (type) {

      case AssetType.MEDIA_IMAGE:
        // TODO
        this.strategy = null;
        break;
      case AssetType.MEDIA_VIDEO:
        // TODO
        this.strategy = null;
        break;
      case AssetType.MEDIA_AUDIO:
        // TODO
        this.strategy = null;
        break;
      case AssetType.SOFTWARE_DOCUMENT:
        // TODO
        this.strategy = null;
        break;
      case AssetType.SOFTWARE_SOURCE_CODE:
        // TODO
        this.strategy = null;
        break;
      case AssetType.DATA:
        // TODO
        this.strategy = null;
        break;
      case AssetType.INTELLECTUAL_PROPERTY_FUNCTION:
        this.strategy = new FunctionStrategy();
        break;
    }
  }

  /**
   * 资产创建请求消息统一化处理
   *
   * @param message 资产创建请求消息
   * @return 资产创建请求消息统一化接口数据
   */
  public CreateAssetRequestMessage<? extends Message> unifyCreateRequestMessage(RestRequestMessage message) {
    return this.strategy.create(message);
  }
}
