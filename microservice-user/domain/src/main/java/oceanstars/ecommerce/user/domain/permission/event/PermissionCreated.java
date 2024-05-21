package oceanstars.ecommerce.user.domain.permission.event;

import java.io.Serial;
import java.time.Clock;
import oceanstars.ecommerce.common.domain.event.DomainEvent;
import oceanstars.ecommerce.user.api.message.payload.permission.PermissionCreatedPayload;
import oceanstars.ecommerce.user.domain.permission.entity.Permission;

/**
 * 领域事件: 权限创建
 *
 * @author Clover
 * @version 1.0.0
 * @since 2024/5/21 17:39
 */
public class PermissionCreated extends DomainEvent<Permission, PermissionCreatedPayload> {

  @Serial
  private static final long serialVersionUID = -6446329235882532576L;

  public PermissionCreated(Permission source, PermissionCreatedPayload data) {
    super(source, data);
  }

  public PermissionCreated(Permission source, PermissionCreatedPayload data, Clock clock) {
    super(source, data, clock);
  }
}
