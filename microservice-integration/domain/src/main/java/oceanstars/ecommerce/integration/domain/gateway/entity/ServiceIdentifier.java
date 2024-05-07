package oceanstars.ecommerce.integration.domain.gateway.entity;

import oceanstars.ecommerce.common.domain.entity.BaseEntityIdentifier;

/**
 * 集成目标系统服务实体唯一识别符对象
 *
 * @author Clover
 * @version 1.0.0
 * @since 2021/12/29 10:24 上午
 */
public class ServiceIdentifier extends BaseEntityIdentifier {

  /**
   * 目标服务终结点
   */
  private final String endpoint;

  /**
   * ID前缀
   */
  private static final String ID_PREFIX = "SERVICE-";

  /**
   * 构造函数：初始化目标服务终结点
   *
   * @param endpoint 目标服务终结点
   */
  public ServiceIdentifier(final String endpoint) {
    this.endpoint = endpoint;
  }

  @Override
  public String generateIdentifier() {

    // 获取UUID
    final String[] uuid = super.uuid(this.endpoint);

    return ID_PREFIX + uuid[0];
  }
}
