package oceanstars.ecommerce.ecm.domain.content.entity;

import java.io.Serial;
import oceanstars.ecommerce.common.domain.entity.BaseEntityIdentifier;
import oceanstars.ecommerce.common.exception.BusinessException;
import oceanstars.ecommerce.ecm.constant.enums.EcmEnums.ContentType;

/**
 * 内容聚合根唯一标识符生成器
 *
 * @author Clover
 * @version 1.0.0
 * @since 2024/1/11 13:43
 */
public class ContentIdentifier extends BaseEntityIdentifier<String> {

  @Serial
  private static final long serialVersionUID = -8734552560365655928L;

  /**
   * 内容类型
   */
  private final ContentType type;

  /**
   * 内容名称
   */
  private final String name;

  /**
   * 构造函数：初始化成员变量
   *
   * @param type 内容类型
   * @param name 内容名称
   */
  public ContentIdentifier(ContentType type, String name) {
    super(type.value() + "#" + name.hashCode());
    this.type = type;
    this.name = name;
  }

  public ContentType getType() {
    return type;
  }

  public String getName() {
    return name;
  }

  @Override
  public String generateIdentifier() {
    throw new BusinessException("内容实体唯一标识符非自动生成，内容类型和内容名称组合唯一");
  }
}
