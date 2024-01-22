package oceanstars.ecommerce.ecm.domain.content.entity;

import java.io.Serial;
import oceanstars.ecommerce.common.domain.BaseEntityIdentifier;

/**
 * 内容分类唯一标识符生成器
 *
 * @author Clover
 * @version 1.0.0
 * @since 2024/1/11 16:26
 */
public class ContentCategoryIdentifier extends BaseEntityIdentifier<String> {

  @Serial
  private static final long serialVersionUID = 8067519406419135674L;

  /**
   * 内容分类名称
   */
  private final String name;

  /**
   * 构造函数：初始化成员变量
   *
   * @param name 内容分类名称
   */
  public ContentCategoryIdentifier(String name) {
    super(null);
    this.name = name;
  }

  @Override
  public String generateIdentifier() {
    return this.name;
  }
}
