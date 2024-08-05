package oceanstars.ecommerce.user.application.account.cqrs.handler.query;

import java.util.List;
import java.util.Set;
import oceanstars.ecommerce.common.cqrs.DelegateDto;
import oceanstars.ecommerce.common.cqrs.IQueryHandler;
import oceanstars.ecommerce.common.domain.entity.EntityDelegator;
import oceanstars.ecommerce.common.tools.DateUtil;
import oceanstars.ecommerce.user.api.rpc.v1.dto.account.UserAccountAccessDto;
import oceanstars.ecommerce.user.api.rpc.v1.dto.account.UserAccountDto;
import oceanstars.ecommerce.user.api.rpc.v1.dto.account.UserAccountExistQuery;
import oceanstars.ecommerce.user.api.rpc.v1.dto.account.UserAccountExistResult;
import oceanstars.ecommerce.user.api.rpc.v1.dto.account.UserAccountProfileDto;
import oceanstars.ecommerce.user.domain.account.entity.Account;
import oceanstars.ecommerce.user.domain.account.entity.AccountAccess;
import oceanstars.ecommerce.user.domain.account.entity.Profile;
import oceanstars.ecommerce.user.domain.account.repository.AccountRepository;
import oceanstars.ecommerce.user.domain.account.repository.condition.AccountFetchCondition;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

/**
 * 账号是否存在查询处理类
 *
 * @author Clover
 * @version 1.0.0
 * @since 2024/7/16 13:33
 */
@Component(value = "accountExistQueryHandler")
public class AccountExistQueryHandler implements IQueryHandler<UserAccountExistResult, UserAccountExistQuery> {

  /**
   * 账号资源仓储
   */
  private final AccountRepository accountRepository;

  /**
   * 构造函数
   *
   * @param accountRepository 账号资源仓储
   */
  public AccountExistQueryHandler(AccountRepository accountRepository) {
    this.accountRepository = accountRepository;
  }

  @Override
  public UserAccountExistResult handle(UserAccountExistQuery query) {

    // 构建账号查询条件
    final AccountFetchCondition.Builder fetchConditionBuilder = AccountFetchCondition.newBuilder();

    // 账号域
    fetchConditionBuilder.domain(Set.of(query.getDomain()));
    // 账号名称
    if (StringUtils.hasText(query.getUserName())) {
      fetchConditionBuilder.name(query.getUserName());
    }
    // 账号访问认证方式
    if (StringUtils.hasText(query.getAccess())) {
      fetchConditionBuilder.access(query.getAccess());
    }
    // 账号访问认证方式类型
    fetchConditionBuilder.accessTypes(Set.of(query.getAccessType()));

    // 初始化账号是否存在查询结果构建器
    final UserAccountExistResult.Builder resultBuilder = UserAccountExistResult.newBuilder().setExist(Boolean.FALSE);

    // 查询账号是否存在, 如果存在则设置结果为存在
    this.accountRepository.findOne(fetchConditionBuilder.build()).ifPresent(account -> {
      // 设置结果为存在
      resultBuilder.setExist(Boolean.TRUE);
      // 设置账号信息
      resultBuilder.setAccount(this.buildUserAccountDto(account));
    });

    return resultBuilder.build();
  }

  /**
   * 构建账号数据传输对象
   *
   * @param account 账号
   * @return 账号数据传输对象
   */
  private UserAccountDto buildUserAccountDto(Account account) {

    // 获取账号资源代理
    final EntityDelegator delegator = account.getDelegator();
    // 获取账号简要
    final Profile profile = account.getProfile();
    // 获取账号访问认证方式列表
    final Set<AccountAccess> accesses = account.getAccesses();

    // 构建资源代理数据传输对象
    final DelegateDto delegateDto = DelegateDto.newBuilder()
        // 数据信息代理唯一识别符（物理主键）
        .setId(delegator.getId())
        // 数据信息代理创建时间
        .setCreateAt(DateUtil.convertLocalDateTimeToProtoTimestamp(delegator.getCreateAt()))
        // 数据信息代理创建者
        .setCreatBy(delegator.getCreateBy())
        // 数据信息代理更新时间
        .setUpdateAt(DateUtil.convertLocalDateTimeToProtoTimestamp(delegator.getUpdateAt()))
        // 数据信息代理更新者
        .setUpdateBy(delegator.getUpdateBy())
        // 数据信息代理版本
        .setVersion(delegator.getVersion())
        // 实施构建
        .build();

    // 构建账号简要数据传输对象
    final UserAccountProfileDto profileDto = UserAccountProfileDto.newBuilder()
        // 姓
        .setFirstName(profile.getFirstName())
        // 名
        .setLastName(profile.getLastName())
        // 昵称
        .setNickName(profile.getNickName())
        // 头像
        .setAvatar(profile.getAvatar())
        // 性别
        .setGender(profile.getGender().key())
        // 生日
        .setBirthday(DateUtil.convertLocalDateToProtoTimestamp(profile.getBirthday()))
        // 实施构建
        .build();

    final List<UserAccountAccessDto> accessDtoList = accesses.stream().map(access -> UserAccountAccessDto.newBuilder()
        // 访问认证方式
        .setAccess(access.getIdentifier().getAccess())
        // 访问认证方式类型
        .setAccessType(access.getType())
        // 是否主要(对于同一类型的访问方式，只能有一个主要的访问方式)
        .setIsPrimary(access.getPrimary())
        // 实施构建
        .build()).toList();

    // 构建账号数据传输对象并返回
    return UserAccountDto.newBuilder()
        // 资源代理
        .setDelegate(delegateDto)
        // 账号域
        .setDomain(account.getIdentifier().getDomain())
        // 账号名称
        .setName(account.getIdentifier().getName())
        // 账号访问认证方式
        .addAllAccesses(accessDtoList)
        // 账号简要
        .setProfile(profileDto)
        // 账号角色
        .addAllRoles(account.getRoles())
        // 实施构建
        .build();
  }
}
