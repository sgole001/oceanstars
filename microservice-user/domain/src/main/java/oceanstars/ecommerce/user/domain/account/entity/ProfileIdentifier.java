package oceanstars.ecommerce.user.domain.account.entity;

import oceanstars.ecommerce.common.domain.entity.BaseEntityIdentifier;
import oceanstars.ecommerce.common.exception.BusinessException;

/**
 * 账号简况实体唯一识别符生成器
 *
 * @author Clover
 * @version 1.0.0
 * @since 2022/1/6 11:49 AM
 */
public final class ProfileIdentifier extends BaseEntityIdentifier<Long> {

  /**
   * 构造函数：初始化成员变量
   *
   * @param account 账号ID
   */
  public ProfileIdentifier(final Long account) {
    super(account);
  }

  @Override
  public Long generateIdentifier() {
    throw new BusinessException("账号简况实体唯一标识符非自动生成，需初始化关联账号实体数据物理PK");
  }
}
