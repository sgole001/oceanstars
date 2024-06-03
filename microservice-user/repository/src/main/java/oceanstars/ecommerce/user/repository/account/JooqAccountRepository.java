package oceanstars.ecommerce.user.repository.account;

import static java.util.Objects.requireNonNull;

import jakarta.annotation.Resource;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import oceanstars.ecommerce.common.domain.repository.BaseDomainRepository;
import oceanstars.ecommerce.common.domain.repository.condition.ICondition;
import oceanstars.ecommerce.common.exception.BusinessException;
import oceanstars.ecommerce.common.spring.OceanstarsTransactional;
import oceanstars.ecommerce.user.constant.enums.UserEnums.AccountRegisterMeans;
import oceanstars.ecommerce.user.constant.enums.UserEnums.AccountRegisterSource;
import oceanstars.ecommerce.user.constant.enums.UserEnums.AccountStatus;
import oceanstars.ecommerce.user.constant.enums.UserEnums.Gender;
import oceanstars.ecommerce.user.constant.enums.UserEnums.Message;
import oceanstars.ecommerce.user.domain.account.entity.Account;
import oceanstars.ecommerce.user.domain.account.entity.AccountIdentifier;
import oceanstars.ecommerce.user.domain.account.entity.Profile;
import oceanstars.ecommerce.user.domain.account.repository.AccountRepository;
import oceanstars.ecommerce.user.domain.account.repository.condition.AccountFetchCondition;
import oceanstars.ecommerce.user.repository.account.view.AccountViewDao;
import oceanstars.ecommerce.user.repository.account.view.bo.AccountView;
import oceanstars.ecommerce.user.repository.generate.tables.daos.RelAccountRoleDao;
import oceanstars.ecommerce.user.repository.generate.tables.daos.UserAccountDao;
import oceanstars.ecommerce.user.repository.generate.tables.daos.UserProfileDao;
import oceanstars.ecommerce.user.repository.generate.tables.pojos.RelAccountRolePojo;
import oceanstars.ecommerce.user.repository.generate.tables.pojos.UserAccountPojo;
import oceanstars.ecommerce.user.repository.generate.tables.pojos.UserProfilePojo;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

/**
 * 账号聚合仓储实现类
 *
 * @author Clover
 * @version 1.0.0
 * @since 2024/5/30 17:10
 */
@Repository
public class JooqAccountRepository extends BaseDomainRepository<Account> implements AccountRepository {

  /**
   * 账号数据访问对象
   */
  @Resource
  private UserAccountDao accountDao;

  /**
   * 账号简况数据访问对象
   */
  @Resource
  private UserProfileDao profileDao;

  /**
   * 账号角色关系数据访问对象
   */
  @Resource
  private RelAccountRoleDao relAccountRoleDao;

  /**
   * 账号视图数据访问对象
   */
  @Resource
  private AccountViewDao accountViewDao;

  /**
   * 密码加密器
   */
  @Resource
  private BCryptPasswordEncoder passwordEncoder;

  @Override
  protected void create(Account account) {

    // 校验参数
    requireNonNull(account, "account");

    // 获取账号唯一标识符
    final AccountIdentifier accountIdentifier = account.getIdentifier();

    // 构建账号查询条件(根据账号唯一标识符)
    final AccountFetchCondition condition = AccountFetchCondition.newBuilder()
        // 账号邮箱
        .email(accountIdentifier.getEmail())
        // 账号手机
        .mobile(accountIdentifier.getMobile())
        // 第三方授权外部UID
        .externalId(accountIdentifier.getExternalId())
        // 账号注册源
        .source(Collections.singleton(accountIdentifier.getSource()))
        // 构建查询条件
        .build();

    // 根据账号查询条件查询账号视图信息
    final List<AccountView> accounts = this.accountViewDao.fetch(condition);

    // 校验账号是否已存在，如果存在则抛出业务异常
    if (!CollectionUtils.isEmpty(accounts)) {
      throw new BusinessException(Message.MSG_BIZ_20001, accountIdentifier.getSource().value(), accountIdentifier.getIdentifier());
    }

    // 构建账号数据库映射
    final UserAccountPojo accountPojo = this.buildAccountRepoPojo(account);
    // 保存账号数据
    this.accountDao.insert(accountPojo);

    // 委托账号实体
    account.delegate(accountPojo);

    // 账号简况不为空的情况下，保存账号简况数据
    if (null != account.getProfile()) {

      // 构建账号简况数据库映射
      final UserProfilePojo profilePojo = this.buildAccountProfileRepoPojo(account);
      // 保存账号简况数据
      this.profileDao.insert(profilePojo);

      // 委托账号简况实体
      account.getProfile().delegate(profilePojo);
    }

    // 账号角色不为空的情况下，保存账号角色数据
    if (!CollectionUtils.isEmpty(account.getRoles())) {

      // 构建账号角色关系数据库映射
      final List<RelAccountRolePojo> relAccountRolePojoList = this.buildRleAccountRoleRepoPojo(account);
      // 保存账号角色关系数据
      this.relAccountRoleDao.insert(relAccountRolePojoList);
    }
  }

  @Override
  protected void modify(Account account) {

  }

  @OceanstarsTransactional(rollbackFor = Exception.class)
  @Override
  public void saveProfile(Account account) {

    // 校验参数
    requireNonNull(account, "account");

    // 根据账号ID获取账号简况数据
    UserProfilePojo profilePojo = this.profileDao.fetchOneByAccount(account.getDelegator().getId());

    if (null == profilePojo) {
      // 构建账号简况数据库映射
      profilePojo = this.buildAccountProfileRepoPojo(account);
      // 保存账号简况数据
      this.profileDao.insert(profilePojo);
    } else {
      // 修改账号简况数据库映射
      this.modifyAccountProfileRepoPojo(account, profilePojo);
      // 更新账号简况数据
      this.profileDao.update(profilePojo);
    }
  }

  @OceanstarsTransactional(rollbackFor = Exception.class)
  @Override
  public void assignRoles(Account account) {

    // 校验参数
    requireNonNull(account, "account");

    // 根据账号ID获取账号角色关系数据
    final List<RelAccountRolePojo> relAccountRolePojoList = this.relAccountRoleDao.fetchByAccount(account.getDelegator().getId());

    // 获取分配角色ID列表
    final Set<Long> roles = account.getRoles();

    // 初始化删除账号角色关系数据列表
    final Set<RelAccountRolePojo> deleteRoles = new HashSet<>();

    // 初始化新增账号角色关系数据列表
    final Set<Long> insertRoles = new HashSet<>(roles);

    // 判断账号角色关系数据是否为空，如果不为空则进行处理
    if (!CollectionUtils.isEmpty(relAccountRolePojoList)) {
      // 遍历账号角色关系数据
      relAccountRolePojoList.forEach(relAccountRolePojo -> {
        // 如果分配角色ID列表中不包含当前角色ID，则添加到删除列表
        if (!roles.contains(relAccountRolePojo.getRole())) {
          deleteRoles.add(relAccountRolePojo);
        } else {
          // 如果分配角色ID列表中包含当前角色ID，则从新增列表中删除
          insertRoles.remove(relAccountRolePojo.getRole());
        }
      });
    }

    // 删除账号角色关系数据
    if (!CollectionUtils.isEmpty(deleteRoles)) {
      this.relAccountRoleDao.delete(deleteRoles);
    }

    // 新增账号角色关系数据
    if (!CollectionUtils.isEmpty(insertRoles)) {
      // 保存账号角色关系数据
      this.relAccountRoleDao.insert(insertRoles.stream().map(role -> {
        // 初始化账号角色关系数据库映射
        final RelAccountRolePojo relAccountRolePojo = new RelAccountRolePojo();
        // 账号ID
        relAccountRolePojo.setAccount(account.getDelegator().getId());
        // 角色ID
        relAccountRolePojo.setRole(role);

        return relAccountRolePojo;
      }).toList());
    }
  }

  @Override
  public List<Account> find(ICondition condition) {

    // 校验参数
    requireNonNull(condition, "condition");

    // 根据账号查询条件查询账号视图信息
    final List<AccountView> accounts = this.accountViewDao.fetch(condition);

    // 校验账号是否存在,如果不存在则返回空列表
    if (CollectionUtils.isEmpty(accounts)) {
      return List.of();
    }

    // 构建权限实体列表
    return accounts.stream().map(this::buildAccountEntity).toList();
  }

  @Override
  public void delete(Account account) {

  }

  /**
   * 构建账号实体
   *
   * @param accountView 账号视图
   * @return 账号实体
   */
  private Account buildAccountEntity(final AccountView accountView) {

    // 获取账号数据
    final UserAccountPojo accountPojo = accountView.getAccount();
    // 获取账号简况数据
    final UserProfilePojo profilePojo = accountView.getProfile();
    // 获取账号角色ID列表
    final List<Long> roles = accountView.getRoles();

    // 获取账号注册源
    final AccountRegisterSource source = AccountRegisterSource.of(accountPojo.getSource().intValue());
    // 获取账号注册方式
    final AccountRegisterMeans means = AccountRegisterMeans.of(accountPojo.getMeans().intValue());

    // 初始化账号实体
    final Account account = Account.newBuilder(source, means)
        // 账号邮箱
        .email(accountPojo.getEmail())
        // 账号手机
        .mobile(accountPojo.getMobile())
        // 第三方授权外部UID
        .externalId(accountPojo.getExternalId())
        // 账号密码（加密后）
        .password(accountPojo.getPassword())
        // 账号状态
        .status(AccountStatus.of(accountPojo.getStatus().intValue()))
        // 实施构建
        .build();

    // 判定账号简况是否为空, 如果不为空则进行处理
    if (null != profilePojo) {

      // 初始化账号简况实体
      final Profile profile = Profile.newBuilder(accountPojo.getId())
          // 姓
          .firstName(profilePojo.getFirstName())
          // 名
          .lastName(profilePojo.getLastName())
          // 昵称
          .nickName(profilePojo.getNickName())
          // 头像
          .avatar(profilePojo.getAvatar())
          // 性别
          .gender(Gender.of(profilePojo.getGender().intValue()))
          // 生日
          .birthday(profilePojo.getBirthday())
          // 实施构建
          .build();

      // 委托账号简况数据
      profile.delegate(profilePojo);

      // 设定账号简况
      account.setProfile(profile);
    }

    // 判定账号角色ID列表是否为空, 如果不为空则进行处理
    if (!CollectionUtils.isEmpty(roles)) {
      // 设定账号角色
      account.setRoles(new HashSet<>(roles));
    }

    // 委托账号数据
    account.delegate(accountPojo);

    return account;
  }

  /**
   * 构建账号数据库映射
   *
   * @param account 账号实体
   * @return 账号数据库映射
   */
  private UserAccountPojo buildAccountRepoPojo(final Account account) {

    // 初始化账号数据库映射
    final UserAccountPojo accountPojo = new UserAccountPojo();

    // 账号注册源
    accountPojo.setSource(account.getIdentifier().getSource().key().shortValue());
    // 账号注册方式
    accountPojo.setMeans(account.getMeans().key().shortValue());
    // 账号邮箱
    if (StringUtils.hasText(account.getIdentifier().getEmail())) {
      accountPojo.setEmail(account.getIdentifier().getEmail());
    }
    // 账号手机
    if (StringUtils.hasText(account.getIdentifier().getMobile())) {
      accountPojo.setMobile(account.getIdentifier().getMobile());
    }
    // 第三方授权外部UID
    if (StringUtils.hasText(account.getIdentifier().getExternalId())) {
      accountPojo.setExternalId(account.getIdentifier().getExternalId());
    }
    // 账号密码
    if (StringUtils.hasText(account.getPassword())) {
      accountPojo.setPassword(passwordEncoder.encode(account.getPassword()));
    }
    // 账号状态
    accountPojo.setStatus(account.getStatus().key().shortValue());

    return accountPojo;
  }

  /**
   * 构建账号简况数据库映射
   *
   * @param account 账号实体
   * @return 账号简况数据库映射
   */
  private UserProfilePojo buildAccountProfileRepoPojo(final Account account) {

    // 获取账号简况实体信息
    final Profile profile = account.getProfile();

    // 初始化账号简况数据库映射
    final UserProfilePojo profilePojo = new UserProfilePojo();
    // 账号ID
    profilePojo.setAccount(account.getDelegator().getId());
    // 姓
    profilePojo.setFirstName(profile.getFirstName());
    // 名
    profilePojo.setLastName(profile.getLastName());
    // 昵称
    profilePojo.setNickName(profile.getNickName());
    // 头像
    profilePojo.setAvatar(profile.getAvatar());
    // 性别
    profilePojo.setGender(profile.getGender().key().shortValue());
    // 生日
    profilePojo.setBirthday(profile.getBirthday());

    return profilePojo;
  }

  /**
   * 修改账号简况数据库映射
   *
   * @param account     账号实体
   * @param profilePojo 账号简况数据库映射
   */
  private void modifyAccountProfileRepoPojo(final Account account, final UserProfilePojo profilePojo) {

    // 获取账号简况实体信息
    final Profile profile = account.getProfile();

    // 姓
    if (null != profile.getFirstName()) {
      profilePojo.setFirstName(profile.getFirstName());
    }
    // 名
    if (null != profile.getLastName()) {
      profilePojo.setLastName(profile.getLastName());
    }
    // 昵称
    if (null != profile.getNickName()) {
      profilePojo.setNickName(profile.getNickName());
    }
    // 头像
    if (null != profile.getAvatar()) {
      profilePojo.setAvatar(profile.getAvatar());
    }
    // 性别
    if (null != profile.getGender()) {
      profilePojo.setGender(profile.getGender().key().shortValue());
    }
    // 生日
    if (null != profile.getBirthday()) {
      profilePojo.setBirthday(profile.getBirthday());
    }
  }

  /**
   * 构建账号角色关系数据库映射列表
   *
   * @param account 账号实体
   * @return 账号角色关系数据库映射列表
   */
  private List<RelAccountRolePojo> buildRleAccountRoleRepoPojo(final Account account) {

    // 构建账号角色关系数据库映射列表
    return account.getRoles().stream().map(role -> {
      // 初始化账号角色关系数据库映射
      final RelAccountRolePojo relAccountRolePojo = new RelAccountRolePojo();
      // 账号ID
      relAccountRolePojo.setAccount(account.getDelegator().getId());
      // 角色ID
      relAccountRolePojo.setRole(role);
      return relAccountRolePojo;
    }).toList();
  }
}
