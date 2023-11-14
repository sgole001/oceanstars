package oceanstars.ecommerce.message.domain.aggregate.event.event;

import java.io.Serial;
import java.time.Clock;
import java.time.LocalDateTime;
import oceanstars.ecommerce.common.domain.AbstractDomainEvent;
import oceanstars.ecommerce.message.domain.aggregate.event.entity.EventMetaEntity;
import oceanstars.ecommerce.message.domain.aggregate.event.event.data.EventData;

public final class EventMetaCreated extends AbstractDomainEvent<EventMetaEntity, EventData> {

  @Serial
  private static final long serialVersionUID = 1696555445837239297L;

  public EventMetaCreated(EventMetaEntity aggregate) {
    super(aggregate);
  }

  public EventMetaCreated(EventMetaEntity aggregate, Clock clock) {
    super(aggregate, clock);
  }

  @Override
  public String withDomain() {
    return "message";
  }

  @Override
  public String withAggregate() {
    return "event";
  }

  @Override
  public void applyOn() {
    super.data = new EventData();
    super.data.setEventId(1L);
    super.data.setEventName("Event111");
    super.data.setEventCreatedAt(LocalDateTime.now());
  }
}
