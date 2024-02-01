package oceanstars.ecommerce.ecm.api.message.payload.category;

import java.io.Serializable;

/**
 * 领域事件业务负载: 分类已创建
 *
 * @author Clover
 * @version 1.0.0
 * @since 2024/1/25 14:30
 */
public record CategoryCreatedPayload(Long categoryId) implements Serializable {

  /**
   * 构造函数: 根据分类ID构建业务负载
   *
   * @param categoryId 分类ID
   */
  public CategoryCreatedPayload {
  }
}
