package oceanstars.ecommerce.ecm.application.asset.cqrs.handler;

import oceanstars.ecommerce.common.cqrs.ICommandHandler;
import oceanstars.ecommerce.common.domain.entity.ValueObject;
import oceanstars.ecommerce.common.domain.event.EventBus;
import oceanstars.ecommerce.ecm.api.rpc.v1.dto.asset.EcmCreateAssetCommand;
import oceanstars.ecommerce.ecm.api.rpc.v1.dto.asset.EcmCreateAssetResult;
import oceanstars.ecommerce.ecm.application.asset.cqrs.strategy.impl.AssetRawDataStrategyContext;
import oceanstars.ecommerce.ecm.constant.enums.EcmEnums.AssetStatus;
import oceanstars.ecommerce.ecm.constant.enums.EcmEnums.AssetType;
import oceanstars.ecommerce.ecm.domain.asset.entity.Asset;
import oceanstars.ecommerce.ecm.domain.asset.repository.AssetRepository;
import org.springframework.stereotype.Component;

/**
 * 创建资产命令处理器
 *
 * @author Clover
 * @version 1.0.0
 * @since 2024/1/17 17:00
 */
@Component
public class CreateAssetCommandHandler implements ICommandHandler<EcmCreateAssetResult, EcmCreateAssetCommand> {

  /**
   * 事件总线
   */
  private final EventBus eventGateway;

  /**
   * 资产资源库
   */
  private final AssetRepository assetRepository;

  /**
   * 构造函数
   *
   * @param eventGateway    事件总线
   * @param assetRepository 资产资源库
   */
  public CreateAssetCommandHandler(EventBus eventGateway, AssetRepository assetRepository) {
    this.eventGateway = eventGateway;
    this.assetRepository = assetRepository;
  }

  @Override
  public EcmCreateAssetResult handle(EcmCreateAssetCommand command) {

    // 获取资产类型
    final AssetType assetType = AssetType.of(command.getType());
    // 解包资产原始数据
    final ValueObject raw = new AssetRawDataStrategyContext(command.getType()).unpack(command.getRawData());

    // 构建内容元数据实体
    final Asset asset = Asset.newBuilder(assetType, command.getName())
        // 资产描述
        .description(command.getDescription())
        // 资产状态
        .status(AssetStatus.PUBLISHED)
        // 资产原生信息
        .raw(raw)
        // 实施构建
        .build();

    // 创建资产
    this.assetRepository.save(asset);
    // 发布内容创建事件
//    eventGateway.publish(new AssetCreated(asset, new AssetCreatedPayload(asset.getDelegator().getId())));

    return EcmCreateAssetResult.newBuilder().setId(asset.getDelegator().getId()).build();
  }
}
