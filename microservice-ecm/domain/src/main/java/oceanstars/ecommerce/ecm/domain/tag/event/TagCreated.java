package oceanstars.ecommerce.ecm.domain.tag.event;

import java.io.Serial;
import java.time.Clock;
import oceanstars.ecommerce.common.domain.DomainEvent;
import oceanstars.ecommerce.ecm.api.message.payload.tag.TagCreatedPayload;
import oceanstars.ecommerce.ecm.domain.tag.entity.Tag;

/**
 * 领域事件: 标签已创建
 *
 * @author Clover
 * @version 1.0.0
 * @since 2024/1/25 10:02
 */
public class TagCreated extends DomainEvent<Tag, TagCreatedPayload> {

  @Serial
  private static final long serialVersionUID = 1421811123764338949L;

  public TagCreated(Tag source, TagCreatedPayload data) {
    super(source, data);
  }

  public TagCreated(Tag source, TagCreatedPayload data, Clock clock) {
    super(source, data, clock);
  }
}
