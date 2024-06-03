package oceanstars.ecommerce.user.api.message.payload.account;

import java.io.Serializable;

/**
 * 领域事件业务负载: 账号简况已创建
 *
 * @author Clover
 * @version 1.0.0
 * @since 2024/5/28 17:17
 */
public record AccountProfileCreatedPayload(Long account) implements Serializable {

}
