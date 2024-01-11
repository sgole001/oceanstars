package oceanstars.ecommerce.user.repository.account;

import oceanstars.ecommerce.user.repository.generate.tables.pojos.UserAccountPojo;

/**
 * 账号聚合仓储接口
 *
 * @author Clover
 * @version 1.0.0
 * @since 2024/1/10 16:33
 */
public interface IAccountRepository {

  /**
   * 创建账号
   *
   * @param accountPojo 账号数据
   */
  void createAccount(final UserAccountPojo accountPojo);

  /**
   * 更新账号
   *
   * @param accountPojo 账号数据
   */
  void updateAccount(final UserAccountPojo accountPojo);
}
