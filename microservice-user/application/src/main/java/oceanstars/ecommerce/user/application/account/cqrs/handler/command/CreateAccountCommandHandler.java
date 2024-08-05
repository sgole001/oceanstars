package oceanstars.ecommerce.user.application.account.cqrs.handler.command;

import java.util.Set;
import oceanstars.ecommerce.common.cqrs.ICommandHandler;
import oceanstars.ecommerce.common.domain.event.EventBus;
import oceanstars.ecommerce.common.tools.ServletUtil;
import oceanstars.ecommerce.user.api.rpc.v1.dto.account.UserCreateAccountCommand;
import oceanstars.ecommerce.user.api.rpc.v1.dto.account.UserCreateAccountResult;
import oceanstars.ecommerce.user.constant.enums.UserEnums.AccountStatus;
import oceanstars.ecommerce.user.domain.account.entity.Account;
import oceanstars.ecommerce.user.domain.account.entity.AccountAccess;
import oceanstars.ecommerce.user.domain.account.repository.AccountRepository;
import org.springframework.stereotype.Component;

/**
 * 创建账号命令处理类
 *
 * @author Clover
 * @version 1.0.0
 * @since 2024/5/28 17:53
 */
@Component(value = "createAccountCommandHandler")
public class CreateAccountCommandHandler implements ICommandHandler<UserCreateAccountResult, UserCreateAccountCommand> {

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
  public CreateAccountCommandHandler(EventBus eventGateway, AccountRepository accountRepository) {
    this.eventGateway = eventGateway;
    this.accountRepository = accountRepository;
  }

  @Override
  public UserCreateAccountResult handle(UserCreateAccountCommand command) {

    // 获取远程调用IP地址
    final String ipAddress = ServletUtil.getRemoteIpAddress();
    // 获取账号域
    final Integer domain = command.getDomain();

    // 构建账号实体信息
    final Account account = Account.newBuilder(command.getUserName(), domain)
        // 账号密码
        .password(command.getPassword())
        // 账号状态
        .status(AccountStatus.NORMAL)
        // 实施构建
        .build();

    // 构建账号访问方式实体信息
    final AccountAccess accountAccess = AccountAccess.newBuilder(
            // 账号实体
            account,
            // 账号访问认证方式
            command.getAccess(),
            // 账号访问认证方式类型
            command.getAccessType())
        // 是否主要(对于同一类型的访问认证方式，只能有一个主要的访问认证方式)
        .primary(true)
        // 实施构建
        .build();

    // 设定账号访问认证方式
    account.setAccesses(Set.of(accountAccess));

    // 保存账号实体信息
    this.accountRepository.save(account);

    // 发布领域事件
//    this.eventGateway.publish(new AccountCreated(account, new AccountCreatedPayload(account.getDelegator().getId())));

    return UserCreateAccountResult.newBuilder().setId(account.getDelegator().getId()).build();
  }
}
