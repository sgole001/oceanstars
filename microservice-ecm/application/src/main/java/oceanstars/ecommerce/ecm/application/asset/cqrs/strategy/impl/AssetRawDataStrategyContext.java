package oceanstars.ecommerce.ecm.application.asset.cqrs.strategy.impl;

import com.google.protobuf.Any;
import oceanstars.ecommerce.common.domain.entity.ValueObject;
import oceanstars.ecommerce.ecm.application.asset.cqrs.strategy.AssetRawDataStrategy;
import oceanstars.ecommerce.ecm.application.asset.cqrs.strategy.impl.ip.FunctionStrategy;
import oceanstars.ecommerce.ecm.constant.enums.EcmEnums.AssetType;

/**
 * 资产原生数据处理策略上下文
 *
 * @author Clover
 * @version 1.0.0
 * @since 2024/4/17 18:23
 */
public class AssetRawDataStrategyContext {

  /**
   * 内容特有数据处理策略接口
   */
  private AssetRawDataStrategy strategy;

  /**
   * 构造函数: 根据资产类型选择不同的策略
   *
   * @param type 内容类型
   */
  public AssetRawDataStrategyContext(final Integer type) {

    // 映射内容类型枚举
    final AssetType assetType = AssetType.of(type);

    if (null == assetType) {
      throw new IllegalArgumentException("不支持的资产类型");
    }

    switch (assetType) {

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
   * 解包请求消息
   *
   * @param message 打包后的请求消息
   * @return 解包后的请求消息
   */
  public ValueObject unpack(Any message) {
    return this.strategy.unpack(message);
  }
}
