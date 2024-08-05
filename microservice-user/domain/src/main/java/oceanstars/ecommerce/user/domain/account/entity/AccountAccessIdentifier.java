package oceanstars.ecommerce.user.domain.account.entity;

import java.io.Serial;
import oceanstars.ecommerce.common.domain.entity.BaseEntityIdentifier;
import oceanstars.ecommerce.common.exception.BusinessException;

/**
 * 账号访问方式实体唯一识别符生成器
 *
 * @author Clover
 * @version 1.0.0
 * @since 2024/7/10 17:29
 */
public class AccountAccessIdentifier extends BaseEntityIdentifier<String> {

  @Serial
  private static final long serialVersionUID = -2919024103359737170L;

  /**
   * 账号ID
   */
  private final Account account;

  /**
   * 账号访问认证方式
   */
  private final String access;

  /**
   * 构造函数：根据账号ID和访问方式初始化成员变量
   *
   * @param account 账号ID
   * @param access  访问方式
   */
  public AccountAccessIdentifier(Account account, String access) {
    super(account.getIdentifier().getIdentifier() + ":" + access);
    this.account = account;
    this.access = access;
  }

  public Account getAccount() {
    return account;
  }

  public String getAccess() {
    return access;
  }

  @Override
  public String generateIdentifier() {
    throw new BusinessException("账号访问方式实体唯一标识符非自动生成，账号和访问方式组合唯一");
  }
}
