package oceanstars.ecommerce.message.domain.aggregate.event.entity;

import oceanstars.ecommerce.common.constant.CommonConstant;
import oceanstars.ecommerce.common.domain.BaseEntityIdentifier;
import oceanstars.ecommerce.message.constant.enums.MessageEnum.MessageBus;

/**
 * 事件元聚合根唯一识别符生成器
 *
 * @author Clover
 * @version 1.0.0
 * @since 2021/12/7 6:11 下午
 */
public class EventMetaIdentifier extends BaseEntityIdentifier {

  /**
   * 事件类型
   */
  private final String type;

  /**
   * 事件总线
   */
  private final MessageBus bus;

  /**
   * ID前缀
   */
  private static final String ID_PREFIX = "EVENT-";

  /**
   * 构造函数：初始化和生成唯一识别符的相关条件
   *
   * @param type 事件类型
   * @param bus  事件总线
   */
  public EventMetaIdentifier(final String type, final MessageBus bus) {
    this.type = type;
    this.bus = bus;
  }

  @Override
  public String generateIdentifier() {

    // ID生成规则相关元素拼接
    final String elements = this.type
        + CommonConstant.SEPARATOR_HYPHEN
        + this.bus.getName();

    // 获取UUID
    final String[] uuid = super.uuid(elements);

    return ID_PREFIX + uuid[0];
  }
}
