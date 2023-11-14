package oceanstars.ecommerce.user.domain.operation.entity;

import oceanstars.ecommerce.common.domain.BaseEntityIdentifier;

/**
 * 操作实体唯一识别符生成器
 *
 * @author Clover
 * @version 1.0.0
 * @since 2022/1/5 2:04 PM
 */
public final class OperationIdentifier extends BaseEntityIdentifier {

  /**
   * 操作行为
   */
  private final String behavior;

  /**
   * ID前缀
   */
  private static final String ID_PREFIX = "OPS-";

  /**
   * 构造函数：初始化成全变量
   *
   * @param behavior 操作行为
   */
  public OperationIdentifier(String behavior) {
    this.behavior = behavior;
  }

  @Override
  public String generateIdentifier() {

    // 获取UUID
    final String[] uuid = super.uuid(this.behavior);

    return ID_PREFIX + uuid[0];
  }
}
