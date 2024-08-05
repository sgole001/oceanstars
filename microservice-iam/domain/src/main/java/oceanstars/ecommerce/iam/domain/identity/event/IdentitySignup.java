package oceanstars.ecommerce.iam.domain.identity.event;

import java.io.Serial;
import java.time.Clock;
import oceanstars.ecommerce.common.domain.event.DomainEvent;
import oceanstars.ecommerce.iam.api.message.payload.identity.IdentitySignupPayload;
import oceanstars.ecommerce.iam.domain.identity.entity.Identity;

/**
 * 领域事件: 身份注册
 *
 * @author Clover
 * @version 1.0.0
 * @since 2024/7/24 19:39
 */
public class IdentitySignup extends DomainEvent<Identity, IdentitySignupPayload> {

  @Serial
  private static final long serialVersionUID = 6384375912415963364L;

  public IdentitySignup(Identity source, IdentitySignupPayload data) {
    super(source, data);
  }

  public IdentitySignup(Identity source, IdentitySignupPayload data, Clock clock) {
    super(source, data, clock);
  }
}
