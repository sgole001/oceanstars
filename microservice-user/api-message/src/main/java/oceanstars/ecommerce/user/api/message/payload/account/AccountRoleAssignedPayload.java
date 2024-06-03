package oceanstars.ecommerce.user.api.message.payload.account;

import java.io.Serializable;

/**
 * 领域事件业务负载: 账号角色已分配
 *
 * @author Clover
 * @version 1.0.0
 * @since 2024/5/31 16:30
 */
public record AccountRoleAssignedPayload(Long account) implements Serializable {

}
