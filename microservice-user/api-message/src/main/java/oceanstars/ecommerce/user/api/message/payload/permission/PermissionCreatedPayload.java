package oceanstars.ecommerce.user.api.message.payload.permission;

import java.io.Serial;
import java.io.Serializable;

/**
 * 领域事件业务负载: 权限已创建
 *
 * @param permission 权限代理ID
 * @author Clover
 * @version 1.0.0
 * @since 2023/12/11 12:14
 */
public record PermissionCreatedPayload(Long permission) implements Serializable {

  @Serial
  private static final long serialVersionUID = -5535586349832474459L;
}
