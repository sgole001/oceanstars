package oceanstars.ecommerce.user.api.message.payload.resource;

import java.io.Serial;
import java.io.Serializable;

/**
 * 领域事件业务负载: 资源类型已创建
 *
 * @author Clover
 * @version 1.0.0
 * @since 2023/12/11 12:14
 */
public class ResourceTypeCreatedPayload implements Serializable {

  @Serial
  private static final long serialVersionUID = -5535586349832474459L;

  /**
   * 资源类型ID
   */
  private String resourceId;

  public String getResourceId() {
    return resourceId;
  }

  public void setResourceId(String resourceId) {
    this.resourceId = resourceId;
  }
}
