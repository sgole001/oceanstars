package oceanstars.ecommerce.user.domain.account.entity;

import java.io.Serial;
import oceanstars.ecommerce.common.domain.entity.BaseEntityIdentifier;

/**
 * 账号实体唯一识别符生成器
 *
 * @author Clover
 * @version 1.0.0
 * @since 2022/1/6 10:55 AM
 */
public final class AccountIdentifier extends BaseEntityIdentifier<String> {

  @Serial
  private static final long serialVersionUID = 7255231152849737890L;

  /**
   * 账号名称
   */
  private final String name;

  /**
   * 账号域
   */
  private final Integer domain;

  /**
   * 构造函数：初始化成员变量
   *
   * @param name   账号邮箱
   * @param domain 账号注册源
   */
  public AccountIdentifier(String name, Integer domain) {
    super(domain + "#" + name);
    this.name = name;
    this.domain = domain;
  }

  public String getName() {
    return name;
  }

  public Integer getDomain() {
    return domain;
  }

  @Override
  public String generateIdentifier() {
    throw new UnsupportedOperationException("账号实体唯一标识符非自动生成，账号名称和注册域组合唯一");
  }
}
