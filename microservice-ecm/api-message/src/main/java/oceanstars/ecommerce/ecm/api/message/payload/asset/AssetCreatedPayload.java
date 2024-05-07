package oceanstars.ecommerce.ecm.api.message.payload.asset;

import java.io.Serial;
import java.io.Serializable;

/**
 * 领域事件业务负载: 资产已创建
 *
 * @author Clover
 * @version 1.0.0
 * @since 2024/4/6 14:13
 */
public record AssetCreatedPayload(Long assetId) implements Serializable {

  @Serial
  private static final long serialVersionUID = -734650408231060816L;

  /**
   * 构造函数: 根据资产ID构建业务负载
   *
   * @param assetId 资产ID
   */
  public AssetCreatedPayload {
  }
}
