package oceanstars.ecommerce.ecm.domain.tag.entity;

import java.io.Serial;
import oceanstars.ecommerce.common.domain.BaseEntityIdentifier;
import oceanstars.ecommerce.common.exception.BusinessException;

/**
 * 标签唯一标识符生成器
 *
 * @author Clover
 * @version 1.0.0
 * @since 2024/1/11 16:10
 */
public class TagIdentifier extends BaseEntityIdentifier<String> {

  @Serial
  private static final long serialVersionUID = -1023224976567409391L;

  /**
   * 构造函数：初始化成员变量
   *
   * @param name 内容标签名称
   */
  public TagIdentifier(String name) {
    super(name);
  }

  @Override
  public String generateIdentifier() {
    throw new BusinessException("标签唯一标识符非自动生成，标签名称即为唯一标识符");
  }
}
