package oceanstars.ecommerce.iam.domain.access.entity;

import java.io.Serial;
import java.text.MessageFormat;
import oceanstars.ecommerce.common.domain.entity.BaseEntityIdentifier;
import oceanstars.ecommerce.common.exception.BusinessException;
import oceanstars.ecommerce.iam.constant.enums.IamEnums.AccessType;

/**
 * @author Clover
 * @version 1.0.0
 * @since 2024/7/8 13:57
 */
public class AccessIdentifier extends BaseEntityIdentifier<String> {

  @Serial
  private static final long serialVersionUID = -8392919331514542963L;

  /**
   * Access目标资源IO
   */
  private final Long resource;

  /**
   * Access类型
   */
  private final AccessType type;

  /**
   * 构造函数：根据校验和初始化成员变量
   *
   * @param resource Access目标资源IO
   * @param type     Access类型
   */
  public AccessIdentifier(Long resource, AccessType type) {
    super(MessageFormat.format("{0}:{1}", type.name(), resource));
    this.resource = resource;
    this.type = type;
  }

  public Long getResource() {
    return resource;
  }

  public AccessType getType() {
    return type;
  }

  @Override
  public String generateIdentifier() {
    throw new BusinessException("Access实体唯一标识符非自动生成，Access目标资源ID和Access类型确认唯一标识符。");
  }
}
