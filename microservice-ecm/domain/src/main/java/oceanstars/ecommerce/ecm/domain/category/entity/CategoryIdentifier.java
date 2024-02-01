package oceanstars.ecommerce.ecm.domain.category.entity;

import java.io.Serial;
import oceanstars.ecommerce.common.domain.BaseEntityIdentifier;
import oceanstars.ecommerce.common.exception.BusinessException;

/**
 * 分类唯一标识符生成器
 *
 * @author Clover
 * @version 1.0.0
 * @since 2024/1/11 16:26
 */
public class CategoryIdentifier extends BaseEntityIdentifier<String> {

  @Serial
  private static final long serialVersionUID = 8067519406419135674L;

  /**
   * 构造函数：初始化成员变量
   *
   * @param name 分类名称
   */
  public CategoryIdentifier(String name) {
    super(name);
  }

  @Override
  public String generateIdentifier() {
    throw new BusinessException("分类唯一标识符非自动生成，分类名称即为唯一标识符");
  }
}
