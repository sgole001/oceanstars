package oceanstars.ecommerce.user.domain.constraint.event;

import java.io.Serial;
import java.time.Clock;
import oceanstars.ecommerce.common.domain.event.DomainEvent;
import oceanstars.ecommerce.user.api.message.payload.constraint.ConstraintCreatedPayload;
import oceanstars.ecommerce.user.domain.constraint.entity.Constraint;

/**
 * 领域事件: 约束已创建
 *
 * @author Clover
 * @version 1.0.0
 * @since 2024/7/1 11:56
 */
public class ConstraintCreated extends DomainEvent<Constraint, ConstraintCreatedPayload> {

  @Serial
  private static final long serialVersionUID = -7642922533812740591L;

  public ConstraintCreated(Constraint source, ConstraintCreatedPayload data) {
    super(source, data);
  }

  public ConstraintCreated(Constraint source, ConstraintCreatedPayload data, Clock clock) {
    super(source, data, clock);
  }
}
