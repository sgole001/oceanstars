package oceanstars.ecommerce.user.api.message.payload.constraint;

import java.io.Serializable;

/**
 * 领域事件业务负载: 约束已创建
 *
 * @author Clover
 * @version 1.0.0
 * @since 2024/7/1 11:36
 */
public record ConstraintCreatedPayload(Long constraint) implements Serializable {

}
