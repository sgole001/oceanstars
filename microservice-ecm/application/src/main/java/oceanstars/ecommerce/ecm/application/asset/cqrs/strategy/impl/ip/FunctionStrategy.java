package oceanstars.ecommerce.ecm.application.asset.cqrs.strategy.impl.ip;

import com.google.protobuf.Any;
import oceanstars.ecommerce.common.domain.entity.ValueObject;
import oceanstars.ecommerce.ecm.api.rpc.v1.dto.asset.EcmAssetFunction;
import oceanstars.ecommerce.ecm.application.asset.cqrs.strategy.AssetRawDataStrategy;
import oceanstars.ecommerce.ecm.domain.asset.entity.valueobject.FunctionValueObject;

/**
 * 知识产权资产原生数据处理策略
 *
 * @author Clover
 * @version 1.0.0
 * @since 2024/4/17 18:36
 */
public class FunctionStrategy implements AssetRawDataStrategy {

  @Override
  public ValueObject unpack(Any message) {

    try {
      final EcmAssetFunction function = message.unpack(EcmAssetFunction.class);

      // 初始化功能原生数据构建器
      final FunctionValueObject.Builder rawDataBuilder = FunctionValueObject.newBuilder();
      // 功能隶属 - 通过资产ID关联隶属关系
      if (0L != function.getParent()) {
        rawDataBuilder.parent(function.getParent());
      }

      return rawDataBuilder.build();

    } catch (Exception e) {
      throw new IllegalArgumentException("资产原生数据功能列表解包失败!");
    }
  }
}
