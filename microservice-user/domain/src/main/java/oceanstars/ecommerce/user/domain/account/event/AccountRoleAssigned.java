package oceanstars.ecommerce.user.domain.account.event;

import java.io.Serial;
import java.time.Clock;
import oceanstars.ecommerce.common.domain.event.DomainEvent;
import oceanstars.ecommerce.user.api.message.payload.account.AccountRoleAssignedPayload;
import oceanstars.ecommerce.user.domain.account.entity.Account;

/**
 * 领域事件: 账号角色已分配
 *
 * @author Clover
 * @version 1.0.0
 * @since 2024/5/31 16:37
 */
public class AccountRoleAssigned extends DomainEvent<Account, AccountRoleAssignedPayload> {

  @Serial
  private static final long serialVersionUID = 1370209015386995042L;

  public AccountRoleAssigned(Account source, AccountRoleAssignedPayload data) {
    super(source, data);
  }

  public AccountRoleAssigned(Account source, AccountRoleAssignedPayload data, Clock clock) {
    super(source, data, clock);
  }
}
