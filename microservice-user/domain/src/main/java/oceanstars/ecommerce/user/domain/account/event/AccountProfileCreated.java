package oceanstars.ecommerce.user.domain.account.event;

import java.io.Serial;
import java.time.Clock;
import oceanstars.ecommerce.common.domain.event.DomainEvent;
import oceanstars.ecommerce.user.api.message.payload.account.AccountProfileCreatedPayload;
import oceanstars.ecommerce.user.domain.account.entity.Account;

/**
 * 领域事件：账号简况已创建
 *
 * @author Clover
 * @version 1.0.0
 * @since 2024/5/28 17:23
 */
public class AccountProfileCreated extends DomainEvent<Account, AccountProfileCreatedPayload> {

  @Serial
  private static final long serialVersionUID = 3609923307283400321L;

  public AccountProfileCreated(Account source, AccountProfileCreatedPayload data) {
    super(source, data);
  }

  public AccountProfileCreated(Account source, AccountProfileCreatedPayload data, Clock clock) {
    super(source, data, clock);
  }
}
