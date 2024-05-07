package oceanstars.ecommerce.ecm.domain.asset.event;

import java.io.Serial;
import java.time.Clock;
import oceanstars.ecommerce.common.domain.event.DomainEvent;
import oceanstars.ecommerce.ecm.api.message.payload.asset.AssetCreatedPayload;
import oceanstars.ecommerce.ecm.domain.asset.entity.Asset;

/**
 * 领域事件: 资产已创建
 *
 * @author Clover
 * @version 1.0.0
 * @since 2024/4/6 14:17
 */
public class AssetCreated extends DomainEvent<Asset, AssetCreatedPayload> {

  @Serial
  private static final long serialVersionUID = -2205420761084012455L;

  public AssetCreated(Asset source, AssetCreatedPayload data) {
    super(source, data);
  }

  public AssetCreated(Asset source, AssetCreatedPayload data, Clock clock) {
    super(source, data, clock);
  }
}
