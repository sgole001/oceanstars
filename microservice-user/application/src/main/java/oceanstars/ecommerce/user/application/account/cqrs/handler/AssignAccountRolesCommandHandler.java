package oceanstars.ecommerce.user.application.account.cqrs.handler;

import java.util.HashSet;
import oceanstars.ecommerce.common.cqrs.ICommandHandler;
import oceanstars.ecommerce.common.domain.event.EventBus;
import oceanstars.ecommerce.common.exception.BusinessException;
import oceanstars.ecommerce.user.api.rpc.v1.dto.account.UserAssignAccountRolesCommand;
import oceanstars.ecommerce.user.api.rpc.v1.dto.account.UserAssignAccountRolesResult;
import oceanstars.ecommerce.user.constant.enums.UserEnums.Message;
import oceanstars.ecommerce.user.domain.account.entity.Account;
import oceanstars.ecommerce.user.domain.account.repository.AccountRepository;
import oceanstars.ecommerce.user.domain.account.repository.condition.AccountFetchCondition;
import org.springframework.stereotype.Component;

/**
 * <此类的功能说明>
 *
 * @author Clover
 * @version 1.0.0
 * @since 2024/5/31 16:47
 */
@Component(value = "assignAccountRolesCommandHandler")
public class AssignAccountRolesCommandHandler implements ICommandHandler<UserAssignAccountRolesResult, UserAssignAccountRolesCommand> {

  /**
   * 事件总线
   */
  private final EventBus eventGateway;

  /**
   * 账号资源仓储
   */
  private final AccountRepository accountRepository;

  /**
   * 构造函数
   *
   * @param eventGateway      事件总线
   * @param accountRepository 账号资源仓储
   */
  public AssignAccountRolesCommandHandler(EventBus eventGateway, AccountRepository accountRepository) {
    this.eventGateway = eventGateway;
    this.accountRepository = accountRepository;
  }

  @Override
  public UserAssignAccountRolesResult handle(UserAssignAccountRolesCommand command) {

    // 根据账号ID查询获取账号实体信息，如果不存在则异常抛出
    final Account account = this.accountRepository.findOne(AccountFetchCondition.newBuilder().id(command.getAccount()).build())
        .orElseThrow(() -> new BusinessException(Message.MSG_BIZ_20000, command.getAccount()));

    // 分配角色
    account.setRoles(new HashSet<>(command.getRolesList()));

    // 保存账号简况
    this.accountRepository.assignRoles(account);

    // 发布领域事件
//    this.eventGateway.publish(new AccountRoleAssigned(account, new AccountRoleAssignedPayload(account.getDelegator().getId())));

    return UserAssignAccountRolesResult.newBuilder().setId(account.getDelegator().getId()).build();
  }
}
