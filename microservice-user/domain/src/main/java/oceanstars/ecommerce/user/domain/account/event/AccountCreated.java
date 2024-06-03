package oceanstars.ecommerce.user.domain.account.event;

import java.io.Serial;
import java.time.Clock;
import oceanstars.ecommerce.common.domain.event.DomainEvent;
import oceanstars.ecommerce.user.api.message.payload.account.AccountCreatedPayload;
import oceanstars.ecommerce.user.domain.account.entity.Account;

/**
 * 领域事件: 账号已创建
 *
 * @author Clover
 * @version 1.0.0
 * @since 2024/5/28 17:19
 */
public class AccountCreated extends DomainEvent<Account, AccountCreatedPayload> {

  @Serial
  private static final long serialVersionUID = 6829034532682023527L;

  public AccountCreated(Account source, AccountCreatedPayload data) {
    super(source, data);
  }

  public AccountCreated(Account source, AccountCreatedPayload data, Clock clock) {
    super(source, data, clock);
  }
}
