package oceanstars.ecommerce.ecm.repository.asset.strategy.impl.ip;

import static java.util.Objects.requireNonNull;

import java.util.List;
import oceanstars.ecommerce.common.domain.repository.condition.ICondition;
import oceanstars.ecommerce.common.exception.BusinessException;
import oceanstars.ecommerce.common.spring.ApplicationContextProvider;
import oceanstars.ecommerce.ecm.constant.enums.EcmEnums.AssetStatus;
import oceanstars.ecommerce.ecm.constant.enums.EcmEnums.AssetType;
import oceanstars.ecommerce.ecm.constant.enums.EcmEnums.Message;
import oceanstars.ecommerce.ecm.domain.asset.entity.Asset;
import oceanstars.ecommerce.ecm.domain.asset.entity.AssetIdentifier;
import oceanstars.ecommerce.ecm.domain.asset.entity.valueobject.FunctionValueObject;
import oceanstars.ecommerce.ecm.domain.asset.repository.condition.ip.FunctionFetchCondition;
import oceanstars.ecommerce.ecm.repository.asset.strategy.AssetRepositoryStrategy;
import oceanstars.ecommerce.ecm.repository.generate.tables.EcmAssetIpFunction;
import oceanstars.ecommerce.ecm.repository.generate.tables.daos.EcmAssetIpFunctionDao;
import oceanstars.ecommerce.ecm.repository.generate.tables.pojos.EcmAssetIpFunctionPojo;
import org.jooq.Condition;
import org.jooq.impl.DSL;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

/**
 * 资产原生信息策略：功能列表
 *
 * @author Clover
 * @version 1.0.0
 * @since 2024/4/18 11:12
 */
public class FunctionStrategy implements AssetRepositoryStrategy {

  /**
   * 资产功能列表表对象
   */
  final static EcmAssetIpFunction T_ASSET = EcmAssetIpFunction.ECM_ASSET_IP_FUNCTION.as("asset");

  /**
   * 资产功能列表DAO
   */
  private static final EcmAssetIpFunctionDao DAO =
      ApplicationContextProvider.getApplicationContext().getBean(EcmAssetIpFunctionDao.class);

  @Override
  public List<Asset> fetch(ICondition condition) {

    // 校验参数
    requireNonNull(condition, "condition");

    // 转换查询条件为功能列表查询条件
    final FunctionFetchCondition fetchCondition = (FunctionFetchCondition) condition;

    // 初始化查询条件
    final Condition searchCondition = DSL.trueCondition();
    // 功能ID
    if (null != fetchCondition.getId()) {
      searchCondition.and(T_ASSET.ID.eq(fetchCondition.getId()));
    }
    // 功能名称
    if (StringUtils.hasText(fetchCondition.getName())) {
      searchCondition.and(T_ASSET.NAME.likeIgnoreCase(fetchCondition.getName().trim()));
    }
    // 功能状态
    if (null != fetchCondition.getStatus()) {
      searchCondition.and(T_ASSET.STATUS.eq(fetchCondition.getStatus().key().shortValue()));
    }
    // 功能隶属
    if (null != fetchCondition.getParent()) {
      searchCondition.and(T_ASSET.PARENT.eq(fetchCondition.getParent()));
    }
    // 功能创建开始时间
    if (null != fetchCondition.getCreateStartTime()) {
      searchCondition.and(T_ASSET.CREATE_AT.ge(fetchCondition.getCreateStartTime()));
    }
    // 功能创建结束时间
    if (null != fetchCondition.getCreateEndTime()) {
      searchCondition.and(T_ASSET.CREATE_AT.le(fetchCondition.getCreateEndTime()));
    }
    // 功能更新开始时间
    if (null != fetchCondition.getUpdateStartTime()) {
      searchCondition.and(T_ASSET.UPDATE_AT.ge(fetchCondition.getUpdateStartTime()));
    }
    // 功能更新结束时间
    if (null != fetchCondition.getUpdateEndTime()) {
      searchCondition.and(T_ASSET.UPDATE_AT.le(fetchCondition.getUpdateEndTime()));
    }

    // 构建查询
    final List<EcmAssetIpFunctionPojo> results =
        DSL_CONTEXT.select(T_ASSET.fields())
            // 资产主表
            .from(T_ASSET)
            // 查询条件
            .where(searchCondition)
            // 排序
            .orderBy(T_ASSET.UPDATE_AT.desc(), T_ASSET.CREATE_AT.desc()).fetchInto(EcmAssetIpFunctionPojo.class);

    // 判断查询结果是否为空
    if (CollectionUtils.isEmpty(results)) {
      return List.of();
    }

    // 构建功能列表实体并返回
    return results.stream().map(this::buildAssetEntity).toList();
  }

  @Override
  public void save(Asset asset) {

    // 校验参数
    requireNonNull(asset, "asset");

    // 根据资产唯一识别码查询资产实体
    EcmAssetIpFunctionPojo assetPojo = DAO.fetchOneByName(asset.getIdentifier().getName());

    // 判断资产实体是否存在, 存在则抛出业务异常
    if (null != assetPojo) {
      // 获取内容唯一识别对象
      final AssetIdentifier identifier = asset.getIdentifier();
      // 业务异常：资产创建失败，{0}类型的名称为{1}的资产已经存在！
      throw new BusinessException(Message.MSG_BIZ_00002, identifier.getType().value(), identifier.getName());
    }

    // 保存资产数据
    assetPojo = (EcmAssetIpFunctionPojo) this.buildAssetPojo(asset);
    DAO.insert(assetPojo);

    // 委托资产实体
    asset.delegate(assetPojo);
  }

  @Override
  public Asset buildAssetEntity(Object assetPojo) {

    // 转换功能列表POJO
    final EcmAssetIpFunctionPojo functionPojo = (EcmAssetIpFunctionPojo) assetPojo;

    // 构建功能列表原生信息值对象
    final FunctionValueObject raw = FunctionValueObject.newBuilder()
        // 功能隶属 - 通过资产ID关联隶属关系
        .parent(functionPojo.getParent())
        // 实施构建
        .build();

    // 构建功能列表实体并返回
    return Asset.newBuilder(AssetType.INTELLECTUAL_PROPERTY_FUNCTION, functionPojo.getName())
        .description(functionPojo.getDescription())
        .status(AssetStatus.of(functionPojo.getStatus().intValue()))
        .raw(raw)
        .build();
  }

  @Override
  public Object buildAssetPojo(Asset asset) {

    // 初始化资产功能列表POJO
    final EcmAssetIpFunctionPojo functionPojo = new EcmAssetIpFunctionPojo();

    // 资产功能列表名称
    functionPojo.setName(asset.getIdentifier().getName());
    // 资产功能列表描述
    functionPojo.setDescription(asset.getDescription());
    // 资产功能列表状态
    functionPojo.setStatus(asset.getStatus().key().shortValue());

    // 转换功能列表原生信息
    final FunctionValueObject raw = (FunctionValueObject) asset.getRaw();
    // 资产功能隶属
    functionPojo.setParent(raw.getParent());

    return functionPojo;
  }
}
