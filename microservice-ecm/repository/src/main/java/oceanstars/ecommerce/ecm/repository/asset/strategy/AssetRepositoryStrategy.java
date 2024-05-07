package oceanstars.ecommerce.ecm.repository.asset.strategy;

import java.util.List;
import oceanstars.ecommerce.common.domain.repository.condition.ICondition;
import oceanstars.ecommerce.common.spring.ApplicationContextProvider;
import oceanstars.ecommerce.ecm.domain.asset.entity.Asset;
import org.jooq.impl.DefaultDSLContext;

/**
 * 资产资源库策略
 *
 * @author Clover
 * @version 1.0.0
 * @since 2024/4/18 11:11
 */
public interface AssetRepositoryStrategy {

  /**
   * Jooq数据库操作对象
   */
  DefaultDSLContext DSL_CONTEXT = ApplicationContextProvider.getApplicationContext().getBean(DefaultDSLContext.class);

  /**
   * 根据查询条件查询资产
   *
   * @param condition 查询条件
   * @return 资产列表
   */
  List<Asset> fetch(final ICondition condition);

  /**
   * 保存资产
   *
   * @param asset 资产实体
   */
  void save(final Asset asset);

  /**
   * 构建资产实体
   *
   * @param assetPojo 资产POJO
   * @return 资产实体
   */
  Asset buildAssetEntity(final Object assetPojo);

  /**
   * 构建资产POJO
   *
   * @param asset 资产实体
   * @return 资产POJO
   */
  Object buildAssetPojo(final Asset asset);
}
