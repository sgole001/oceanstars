package oceanstars.ecommerce.ecm.domain.category.event;

import java.io.Serial;
import java.time.Clock;
import oceanstars.ecommerce.common.domain.DomainEvent;
import oceanstars.ecommerce.ecm.api.message.payload.category.CategoryCreatedPayload;
import oceanstars.ecommerce.ecm.domain.category.entity.Category;

/**
 * 领域事件: 分类已创建
 *
 * @author Clover
 * @version 1.0.0
 * @since 2024/1/25 10:02
 */
public class CategoryCreated extends DomainEvent<Category, CategoryCreatedPayload> {

  @Serial
  private static final long serialVersionUID = 1421811123764338949L;

  public CategoryCreated(Category source, CategoryCreatedPayload data) {
    super(source, data);
  }

  public CategoryCreated(Category source, CategoryCreatedPayload data, Clock clock) {
    super(source, data, clock);
  }
}
