package oceanstars.ecommerce.user.api.message.payload.role;

import java.io.Serializable;

/**
 * 领域事件业务负载: 角色已创建
 *
 * @author Clover
 * @version 1.0.0
 * @since 2024/5/23 15:28
 */
public record RoleCreatedPayload(Long role) implements Serializable {

}
