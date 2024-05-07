package oceanstars.ecommerce.ecm.repository.asset.strategy.impl;

import java.util.List;
import oceanstars.ecommerce.common.domain.repository.condition.ICondition;
import oceanstars.ecommerce.ecm.constant.enums.EcmEnums.AssetType;
import oceanstars.ecommerce.ecm.domain.asset.entity.Asset;
import oceanstars.ecommerce.ecm.repository.asset.strategy.AssetRepositoryStrategy;
import oceanstars.ecommerce.ecm.repository.asset.strategy.impl.ip.FunctionStrategy;

/**
 * 资产原生数据处理策略上下文
 *
 * @author Clover
 * @version 1.0.0
 * @since 2024/4/18 15:04
 */
public class AssetRepositoryStrategyContext {

  /**
   * 资产原生实体策略接口
   */
  private AssetRepositoryStrategy strategy;

  /**
   * 构造函数: 根据资产类型选择不同的策略
   *
   * @param assetType 资产类型
   */
  public AssetRepositoryStrategyContext(final AssetType assetType) {

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
   * 查询资产
   *
   * @param condition 查询条件
   * @return 资产列表
   */
  public List<Asset> fetch(final ICondition condition) {
    return this.strategy.fetch(condition);
  }

  /**
   * 保存资产
   *
   * @param asset 资产实体
   */
  public void save(final Asset asset) {
    this.strategy.save(asset);
  }
}
