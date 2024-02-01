package oceanstars.ecommerce.ecm.api.message.payload.tag;

import java.io.Serializable;

/**
 * 领域事件业务负载: 标签已创建
 *
 * @author Clover
 * @version 1.0.0
 * @since 2024/1/25 14:30
 */
public record TagCreatedPayload(Long tagId) implements Serializable {

  /**
   * 构造函数: 根据标签ID构建业务负载
   *
   * @param tagId 标签ID
   */
  public TagCreatedPayload {
  }
}
