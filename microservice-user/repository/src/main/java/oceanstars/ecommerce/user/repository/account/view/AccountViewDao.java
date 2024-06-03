package oceanstars.ecommerce.user.repository.account.view;

import jakarta.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import oceanstars.ecommerce.common.domain.repository.condition.ICondition;
import oceanstars.ecommerce.user.constant.enums.UserEnums.AccountRegisterMeans;
import oceanstars.ecommerce.user.constant.enums.UserEnums.AccountRegisterSource;
import oceanstars.ecommerce.user.domain.account.repository.condition.AccountFetchCondition;
import oceanstars.ecommerce.user.repository.account.view.bo.AccountView;
import oceanstars.ecommerce.user.repository.generate.tables.RelAccountRole;
import oceanstars.ecommerce.user.repository.generate.tables.UserAccount;
import oceanstars.ecommerce.user.repository.generate.tables.UserProfile;
import oceanstars.ecommerce.user.repository.generate.tables.pojos.UserAccountPojo;
import oceanstars.ecommerce.user.repository.generate.tables.pojos.UserProfilePojo;
import org.jooq.Condition;
import org.jooq.SelectJoinStep;
import org.jooq.impl.DSL;
import org.jooq.impl.DefaultDSLContext;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

/**
 * 账号视图数据访问对象
 *
 * @author Clover
 * @version 1.0.0
 * @since 2024/5/30 17:17
 */
@Repository
public class AccountViewDao {

  /**
   * 账号表
   */
  final static UserAccount T_ACCOUNT = UserAccount.USER_ACCOUNT.as("account");

  /**
   * 账号简况表
   */
  final static UserProfile T_PROFILE = UserProfile.USER_PROFILE.as("profile");

  /**
   * 账号角色关系表
   */
  final static RelAccountRole REL_ACCOUNT_ROLE = RelAccountRole.REL_ACCOUNT_ROLE.as("role");

  /**
   * Jooq数据库操作对象
   */
  @Resource
  private DefaultDSLContext dsl;

  /**
   * 根据查询条件查询账号视图信息
   *
   * @param condition 查询条件
   * @return 账号视图信息
   */
  public List<AccountView> fetch(final ICondition condition) {

    // 初始化查询条件
    Condition searchCondition = DSL.trueCondition();

    // 转换查询条件为账号查询条件
    final AccountFetchCondition fetchCondition = (AccountFetchCondition) condition;

    // 账号ID
    if (null != fetchCondition.getId()) {
      searchCondition = searchCondition.and(T_ACCOUNT.ID.eq(fetchCondition.getId()));
    }

    // 账号注册方式
    if (!CollectionUtils.isEmpty(fetchCondition.getMeans())) {
      searchCondition = searchCondition.and(T_ACCOUNT.MEANS.in(fetchCondition.getMeans().stream().map(AccountRegisterMeans::key).toList()));
    }

    // 账号注册源
    if (!CollectionUtils.isEmpty(fetchCondition.getSource())) {
      searchCondition = searchCondition.and(T_ACCOUNT.SOURCE.in(fetchCondition.getSource().stream().map(AccountRegisterSource::key).toList()));
    }

    // 邮箱（非邮箱注册时可绑定或解绑）
    if (StringUtils.hasText(fetchCondition.getEmail())) {
      searchCondition = searchCondition.and(T_ACCOUNT.EMAIL.eq(fetchCondition.getEmail()));
    }

    // 手机号（非手机注册时可绑定或解绑）
    if (StringUtils.hasText(fetchCondition.getMobile())) {
      searchCondition = searchCondition.and(T_ACCOUNT.MOBILE.eq(fetchCondition.getMobile()));
    }

    // 第三方授权外部UID
    if (StringUtils.hasText(fetchCondition.getExternalId())) {
      searchCondition = searchCondition.and(T_ACCOUNT.EXTERNAL_ID.eq(fetchCondition.getExternalId()));
    }

    // 账号状态
    if (!CollectionUtils.isEmpty(fetchCondition.getStatus())) {
      searchCondition = searchCondition.and(
          T_ACCOUNT.STATUS.in(fetchCondition.getStatus().stream().map(status -> status.key().shortValue()).toList()));
    }

    // 账号创建开始时间
    if (null != fetchCondition.getCreateStartTime()) {
      searchCondition = searchCondition.and(T_ACCOUNT.CREATE_AT.ge(fetchCondition.getCreateStartTime()));
    }
    // 账号创建结束时间
    if (null != fetchCondition.getCreateEndTime()) {
      searchCondition = searchCondition.and(T_ACCOUNT.CREATE_AT.le(fetchCondition.getCreateEndTime()));
    }
    // 账号更新开始时间
    if (null != fetchCondition.getUpdateStartTime()) {
      searchCondition = searchCondition.and(T_ACCOUNT.UPDATE_AT.ge(fetchCondition.getUpdateStartTime()));
    }
    // 账号更新结束时间
    if (null != fetchCondition.getUpdateEndTime()) {
      searchCondition = searchCondition.and(T_ACCOUNT.UPDATE_AT.le(fetchCondition.getUpdateEndTime()));
    }

    // 是否有账号简况查询条件
    Boolean hasProfileCondition = Boolean.FALSE;
    // 姓
    if (StringUtils.hasText(fetchCondition.getFirstName())) {
      searchCondition = searchCondition.and(T_PROFILE.FIRST_NAME.eq(fetchCondition.getFirstName()));
      hasProfileCondition = Boolean.TRUE;
    }

    // 名
    if (StringUtils.hasText(fetchCondition.getLastName())) {
      searchCondition = searchCondition.and(T_PROFILE.LAST_NAME.eq(fetchCondition.getLastName()));
      hasProfileCondition = Boolean.TRUE;
    }

    // 昵称
    if (StringUtils.hasText(fetchCondition.getNickName())) {
      searchCondition = searchCondition.and(T_PROFILE.NICK_NAME.eq(fetchCondition.getNickName()));
      hasProfileCondition = Boolean.TRUE;
    }

    // 性别 0 : male; 1 : female
    if (null != fetchCondition.getGender()) {
      searchCondition = searchCondition.and(T_PROFILE.GENDER.eq(fetchCondition.getGender().key().shortValue()));
      hasProfileCondition = Boolean.TRUE;
    }

    // 生日（开始时间）
    if (null != fetchCondition.getBirthdayStart()) {
      searchCondition = searchCondition.and(T_PROFILE.BIRTHDAY.ge(fetchCondition.getBirthdayStart()));
      hasProfileCondition = Boolean.TRUE;
    }

    // 生日（结束时间）
    if (null != fetchCondition.getBirthdayEnd()) {
      searchCondition = searchCondition.and(T_PROFILE.BIRTHDAY.le(fetchCondition.getBirthdayEnd()));
      hasProfileCondition = Boolean.TRUE;
    }

    // 是否有账号角色查询条件
    Boolean hasRoleCondition = Boolean.FALSE;
    // 账号角色列表
    if (!CollectionUtils.isEmpty(fetchCondition.getRoles())) {
      searchCondition = searchCondition.and(REL_ACCOUNT_ROLE.ROLE.in(fetchCondition.getRoles()));
      hasRoleCondition = Boolean.TRUE;
    }

    // 构建查询
    SelectJoinStep<?> query = dsl
        .select(T_ACCOUNT.fields())
        .select(T_PROFILE.fields())
        .select(REL_ACCOUNT_ROLE.ROLE)
        .from(T_ACCOUNT);
    // 是否有账号简况查询条件
    if (hasProfileCondition) {
      query = query.join(T_PROFILE).on(T_ACCOUNT.ID.eq(T_PROFILE.ACCOUNT));
    } else {
      query = query.leftJoin(T_PROFILE).on(T_ACCOUNT.ID.eq(T_PROFILE.ACCOUNT));
    }
    // 是否有账号角色查询条件
    if (hasRoleCondition) {
      query = query.join(REL_ACCOUNT_ROLE).on(T_ACCOUNT.ID.eq(REL_ACCOUNT_ROLE.ACCOUNT));
    } else {
      query = query.leftJoin(REL_ACCOUNT_ROLE).on(T_ACCOUNT.ID.eq(REL_ACCOUNT_ROLE.ACCOUNT));
    }

    // 查询
    final Map<AccountView, List<Long>> results = query
        // 查询条件
        .where(searchCondition)
        // 排序条件
        .orderBy(T_ACCOUNT.ID, REL_ACCOUNT_ROLE.ROLE)
        .collect(
            Collectors.groupingBy(
                record -> {
                  // 初始化账号视图对象
                  final AccountView accountView = new AccountView();
                  // 设定账号数据
                  accountView.setAccount(record.into(T_ACCOUNT).into(UserAccountPojo.class));
                  // 设定账号简况数据
                  if (record.get(T_PROFILE.ID) != null) {
                    accountView.setProfile(record.into(T_PROFILE).into(UserProfilePojo.class));
                  }

                  return accountView;
                },
                Collectors.mapping(record -> record.get(REL_ACCOUNT_ROLE.ROLE), Collectors.toList())
            ));

    // 查询结果为空，返回空
    if (CollectionUtils.isEmpty(results)) {
      return null;
    }

    // 遍历查询结果, 构建角色视图信息并排序后返回
    return results.entrySet().stream().map(entry -> {
      // LeftJoin情况下，角色信息可能为空，空的情况下不设定角色信息
      if (entry.getValue().getFirst() != null) {
        entry.getKey().setRoles(entry.getValue());
      }
      return entry.getKey();
    }).sorted().toList();
  }
}
