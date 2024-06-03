package oceanstars.ecommerce.user.domain.account.repository;

import oceanstars.ecommerce.common.domain.repository.DomainRepository;
import oceanstars.ecommerce.user.domain.account.entity.Account;

/**
 * 账号聚合仓储接口
 *
 * @author Clover
 * @version 1.0.0
 * @since 2024/1/10 16:33
 */
public interface AccountRepository extends DomainRepository<Account> {

  /**
   * 保存账号简况
   *
   * @param account 账号实体
   */
  void saveProfile(Account account);

  /**
   * 分配账号角色
   *
   * @param account 账号实体
   */
  void assignRoles(Account account);
}
