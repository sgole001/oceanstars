package oceanstars.ecommerce.user.domain.role.event;

import java.io.Serial;
import java.time.Clock;
import oceanstars.ecommerce.common.domain.event.DomainEvent;
import oceanstars.ecommerce.user.api.message.payload.role.RoleCreatedPayload;
import oceanstars.ecommerce.user.domain.role.entity.Role;

/**
 * 领域事件: 角色已创建
 *
 * @author Clover
 * @version 1.0.0
 * @since 2024/5/23 15:29
 */
public class RoleCreated extends DomainEvent<Role, RoleCreatedPayload> {

  @Serial
  private static final long serialVersionUID = -2386752031173465886L;

  public RoleCreated(Role source, RoleCreatedPayload data) {
    super(source, data);
  }

  public RoleCreated(Role source, RoleCreatedPayload data, Clock clock) {
    super(source, data, clock);
  }
}
