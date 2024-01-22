package oceanstars.ecommerce.ecm.domain.content.entity;

import java.io.Serial;
import oceanstars.ecommerce.common.domain.BaseEntityIdentifier;

/**
 * 内容标签唯一标识符生成器
 *
 * @author Clover
 * @version 1.0.0
 * @since 2024/1/11 16:10
 */
public class ContentTagIdentifier extends BaseEntityIdentifier<String> {

  @Serial
  private static final long serialVersionUID = -1023224976567409391L;
  /**
   * 内容标签名称
   */
  private final String name;

  /**
   * 构造函数：初始化成员变量
   *
   * @param name 内容标签名称
   */
  public ContentTagIdentifier(String name) {
    super(null);
    this.name = name;
  }

  @Override
  public String generateIdentifier() {
    return this.name;
  }
}
