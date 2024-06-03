package oceanstars.ecommerce.user.application.account.cqrs.handler;

import oceanstars.ecommerce.common.cqrs.ICommandHandler;
import oceanstars.ecommerce.common.domain.event.EventBus;
import oceanstars.ecommerce.common.tools.ServletUtil;
import oceanstars.ecommerce.user.api.rpc.v1.dto.account.UserCreateAccountCommand;
import oceanstars.ecommerce.user.api.rpc.v1.dto.account.UserCreateAccountResult;
import oceanstars.ecommerce.user.constant.enums.UserEnums.AccountRegisterMeans;
import oceanstars.ecommerce.user.constant.enums.UserEnums.AccountRegisterSource;
import oceanstars.ecommerce.user.constant.enums.UserEnums.AccountStatus;
import oceanstars.ecommerce.user.domain.account.entity.Account;
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
    // 获取账号注册源
    final AccountRegisterSource source = AccountRegisterSource.of(command.getSource());
    // 获取账号注册方式
    final AccountRegisterMeans mean = AccountRegisterMeans.of(command.getMean());

    // 构建账号实体信息
    final Account.Builder accountBuilder = Account.newBuilder(source, mean)
        // 账号密码
        .password(command.getPassword())
        // 账号状态
        .status(AccountStatus.NORMAL);

    switch (mean) {
      case EMAIL:
        // 邮箱
        accountBuilder.email(command.getAccount());
        break;
      case MOBILE:
        // 手机
        accountBuilder.mobile(command.getAccount());
        break;
      case EXTERNAL:
        // 第三方外部UID
        accountBuilder.externalId(command.getAccount());
        break;
    }

    // 构建账号实体
    final Account account = accountBuilder.build();

    // 保存角色实体信息
    this.accountRepository.save(account);

    // 发布领域事件
//    this.eventGateway.publish(new AccountCreated(account, new AccountCreatedPayload(account.getDelegator().getId())));

    return UserCreateAccountResult.newBuilder().setId(account.getDelegator().getId()).build();
  }
}
