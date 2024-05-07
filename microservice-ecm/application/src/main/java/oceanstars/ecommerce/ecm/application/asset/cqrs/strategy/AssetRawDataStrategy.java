package oceanstars.ecommerce.ecm.application.asset.cqrs.strategy;

import com.google.protobuf.Any;
import oceanstars.ecommerce.common.domain.entity.ValueObject;

/**
 * 资产原生数据处理策略接口
 *
 * @author Clover
 * @version 1.0.0
 * @since 2024/4/17 18:13
 */
public interface AssetRawDataStrategy {

  /**
   * 解包请求消息
   *
   * @param message 打包后的请求消息
   * @return 解包后的请求消息
   */
  ValueObject unpack(Any message);
}
