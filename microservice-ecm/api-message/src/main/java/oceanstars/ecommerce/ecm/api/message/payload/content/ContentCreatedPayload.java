package oceanstars.ecommerce.ecm.api.message.payload.content;

import java.io.Serializable;

/**
 * 领域事件业务负载: 内容已创建
 *
 * @param contentId 内容ID
 * @author Clover
 * @version 1.0.0
 * @since 2024/1/25 10:08
 */
public record ContentCreatedPayload(Long contentId) implements Serializable {

  /**
   * 构造函数: 根据内容ID构建业务负载
   *
   * @param contentId 内容ID
   */
  public ContentCreatedPayload {
  }
}
