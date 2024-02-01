package oceanstars.ecommerce.ecm.domain.content.event;

import java.io.Serial;
import java.time.Clock;
import oceanstars.ecommerce.common.domain.DomainEvent;
import oceanstars.ecommerce.ecm.api.message.payload.content.ContentCreatedPayload;
import oceanstars.ecommerce.ecm.domain.content.entity.Content;

/**
 * 领域事件: 内容已创建
 *
 * @author Clover
 * @version 1.0.0
 * @since 2024/1/25 10:02
 */
public class ContentCreated extends DomainEvent<Content, ContentCreatedPayload> {

  @Serial
  private static final long serialVersionUID = 8343411787623713230L;

  public ContentCreated(Content source, ContentCreatedPayload data) {
    super(source, data);
  }

  public ContentCreated(Content source, ContentCreatedPayload data, Clock clock) {
    super(source, data, clock);
  }
}
