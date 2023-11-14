package oceanstars.ecommerce.integration.domain.gateway.entity;

import oceanstars.ecommerce.common.constant.CommonConstant;
import oceanstars.ecommerce.common.domain.BaseEntityIdentifier;

/**
 * 集成路由实体唯一识别符对象
 *
 * @author Clover
 * @version 1.0.0
 * @since 2021/12/30 12:22 下午
 */
public class RouteIdentifier extends BaseEntityIdentifier {

  /**
   * 集成触发事件
   */
  private final String event;

  /**
   * 集成处理组
   */
  private final String group;

  /**
   * 集成目标服务终结点
   */
  private final String endpoint;

  /**
   * ID前缀
   */
  private static final String ID_PREFIX = "ROUTE-";

  /**
   * 构造函数：初始化成员变量
   *
   * @param event    集成触发事件
   * @param endpoint 集成目标服务终结点
   */
  public RouteIdentifier(final String event, final String group, final String endpoint) {
    this.event = event;
    this.group = group;
    this.endpoint = endpoint;
  }

  @Override
  public String generateIdentifier() {

    // ID生成规则相关元素拼接
    final String elements = this.event + CommonConstant.SEPARATOR_HYPHEN + this.group + CommonConstant.SEPARATOR_HYPHEN + this.endpoint;

    // 获取UUID
    final String[] uuid = super.uuid(elements);

    return ID_PREFIX + uuid[0];
  }
}
