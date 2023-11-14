package oceanstars.ecommerce.user.domain.resource.entity;

import oceanstars.ecommerce.common.domain.BaseEntityIdentifier;

/**
 * 资源实体唯一识别符生成器
 *
 * @author Clover
 * @version 1.0.0
 * @since 2022/1/6 3:49 PM
 */
public class ResourceTypeIdentifier extends BaseEntityIdentifier {

  /**
   * 资源名
   */
  private final String name;

  /**
   * ID前缀
   */
  private static final String ID_PREFIX = "RES-";

  /**
   * 构造函数：初始化成员变量
   *
   * @param name 资源名
   */
  public ResourceTypeIdentifier(String name) {
    this.name = name;
  }

  @Override
  public String generateIdentifier() {

    // 获取UUID
    final String[] uuid = super.uuid(this.name);

    return ID_PREFIX + uuid[0];
  }
}
