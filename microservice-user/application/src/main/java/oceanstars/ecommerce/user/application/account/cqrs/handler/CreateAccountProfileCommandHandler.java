package oceanstars.ecommerce.user.application.account.cqrs.handler;

import com.google.protobuf.Timestamp;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import oceanstars.ecommerce.common.cqrs.ICommandHandler;
import oceanstars.ecommerce.common.domain.event.EventBus;
import oceanstars.ecommerce.common.exception.BusinessException;
import oceanstars.ecommerce.user.api.rpc.v1.dto.account.UserCreateAccountProfileCommand;
import oceanstars.ecommerce.user.api.rpc.v1.dto.account.UserCreateAccountProfileResult;
import oceanstars.ecommerce.user.constant.enums.UserEnums.Gender;
import oceanstars.ecommerce.user.constant.enums.UserEnums.Message;
import oceanstars.ecommerce.user.domain.account.entity.Account;
import oceanstars.ecommerce.user.domain.account.entity.Profile;
import oceanstars.ecommerce.user.domain.account.repository.AccountRepository;
import oceanstars.ecommerce.user.domain.account.repository.condition.AccountFetchCondition;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

/**
 * 创建账号简况命令处理类
 *
 * @author Clover
 * @version 1.0.0
 * @since 2024/5/30 14:28
 */
@Component(value = "createAccountProfileCommandHandler")
public class CreateAccountProfileCommandHandler implements ICommandHandler<UserCreateAccountProfileResult, UserCreateAccountProfileCommand> {

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
  public CreateAccountProfileCommandHandler(EventBus eventGateway, AccountRepository accountRepository) {
    this.eventGateway = eventGateway;
    this.accountRepository = accountRepository;
  }

  @Override
  public UserCreateAccountProfileResult handle(UserCreateAccountProfileCommand command) {

    // 根据账号ID查询获取账号实体信息，如果不存在则异常抛出
    final Account account = this.accountRepository.findOne(AccountFetchCondition.newBuilder().id(command.getAccount()).build())
        .orElseThrow(() -> new BusinessException(Message.MSG_BIZ_20000, command.getAccount()));

    // 构建账号简况实体
    final Profile profile = account.getProfile() == null ? Profile.newBuilder(command.getAccount()).build() : account.getProfile();
    // 姓
    if (StringUtils.hasText(command.getFirstName())) {
      profile.setFirstName(command.getFirstName());
    }
    // 名
    if (StringUtils.hasText(command.getLastName())) {
      profile.setLastName(command.getLastName());
    }
    // 昵称
    if (StringUtils.hasText(command.getNickName())) {
      profile.setNickName(command.getNickName());
    }
    // 头像
    if (StringUtils.hasText(command.getAvatar())) {
      profile.setAvatar(command.getAvatar());
    }
    // 性别（NULL的情况下protobuf显式赋值-1）
    if (-1 != command.getGender()) {
      profile.setGender(Gender.of(command.getGender()));
    }
    // 生日（NULL的情况下protobuf隐式赋值0）
    if (Timestamp.getDefaultInstance() != command.getBirthday()) {
      // 获取时间戳
      final Instant instant = Instant.ofEpochSecond(command.getBirthday().getSeconds(), command.getBirthday().getNanos());
      // 设置生日
      profile.setBirthday(LocalDate.ofInstant(instant, ZoneId.systemDefault()));
    }

    // 设定账号简况
    account.setProfile(profile);
    // 保存账号简况
    this.accountRepository.saveProfile(account);

    // 发布领域事件
//    this.eventGateway.publish(new AccountProfileCreated(account, new AccountProfileCreatedPayload(account.getDelegator().getId())));

    return UserCreateAccountProfileResult.newBuilder().setId(account.getDelegator().getId()).build();
  }
}
