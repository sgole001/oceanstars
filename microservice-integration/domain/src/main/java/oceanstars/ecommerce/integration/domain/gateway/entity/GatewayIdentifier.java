package oceanstars.ecommerce.integration.domain.gateway.entity;

import oceanstars.ecommerce.common.domain.BaseEntityIdentifier;
import oceanstars.ecommerce.integration.constant.enums.IntegrationEnum.IntegrationSystem;

/**
 * 集成网关唯一识别符对象
 *
 * @author Clover
 * @version 1.0.0
 * @since 2021/12/29 9:53 上午
 */
public class GatewayIdentifier extends BaseEntityIdentifier {

  /**
   * 集成目标系统(一个集成网关只集成一个外部系统)
   */
  private final IntegrationSystem system;

  /**
   * ID前缀
   */
  private static final String ID_PREFIX = "GW-";

  /**
   * 构造杉树：初始化成员变量
   *
   * @param system 集成目标系统
   */
  public GatewayIdentifier(final IntegrationSystem system) {
    this.system = system;
  }

  @Override
  public String generateIdentifier() {

    // 获取UUID
    final String[] uuid = super.uuid(system.getName());

    return ID_PREFIX + uuid[0];
  }
}
