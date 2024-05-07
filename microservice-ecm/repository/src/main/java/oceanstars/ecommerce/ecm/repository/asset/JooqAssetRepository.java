package oceanstars.ecommerce.ecm.repository.asset;

import static java.util.Objects.requireNonNull;

import java.util.List;
import oceanstars.ecommerce.common.domain.repository.condition.ICondition;
import oceanstars.ecommerce.ecm.domain.asset.entity.Asset;
import oceanstars.ecommerce.ecm.domain.asset.repository.AssetRepository;
import oceanstars.ecommerce.ecm.domain.asset.repository.condition.AssetFetchCondition;
import oceanstars.ecommerce.ecm.repository.asset.strategy.impl.AssetRepositoryStrategyContext;
import oceanstars.ecommerce.infrastructure.pool.configuration.OceanstarsTransactional;
import org.springframework.stereotype.Repository;

/**
 * 资产聚合资源库接口实现(JOOQ基础设施)
 *
 * @author Clover
 * @version 1.0.0
 * @since 2024/4/18 11:07
 */
@Repository
public class JooqAssetRepository implements AssetRepository {

  @Override
  public List<Asset> find(ICondition condition) {

    // 校验参数
    requireNonNull(condition, "condition");

    // 公共查询条件类型转换获取资产类型信息
    final AssetFetchCondition assetFetchCondition = (AssetFetchCondition) condition;

    // 初始化创建资产资源库策略上下文
    final AssetRepositoryStrategyContext repositoryStrategy = new AssetRepositoryStrategyContext(assetFetchCondition.getType());

    // 查询资产
    return repositoryStrategy.fetch(assetFetchCondition);
  }

  @OceanstarsTransactional(rollbackFor = Exception.class)
  @Override
  public void save(Asset asset) {

    // 校验参数
    requireNonNull(asset, "asset");

    // 初始化创建资产资源库策略上下文
    final AssetRepositoryStrategyContext repositoryStrategy = new AssetRepositoryStrategyContext(asset.getIdentifier().getType());

    // 保存资产
    repositoryStrategy.save(asset);
  }

  @Override
  public void delete(Asset aggregator) {

  }
}
