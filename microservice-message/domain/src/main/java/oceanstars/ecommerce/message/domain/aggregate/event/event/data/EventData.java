package oceanstars.ecommerce.message.domain.aggregate.event.event.data;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

public class EventData implements Serializable {

  @Serial
  private static final long serialVersionUID = 4281210191346451031L;
  private Long eventId;

  private String eventName;

  private LocalDateTime eventCreatedAt;

  public Long getEventId() {
    return eventId;
  }

  public void setEventId(Long eventId) {
    this.eventId = eventId;
  }

  public String getEventName() {
    return eventName;
  }

  public void setEventName(String eventName) {
    this.eventName = eventName;
  }

  public LocalDateTime getEventCreatedAt() {
    return eventCreatedAt;
  }

  public void setEventCreatedAt(LocalDateTime eventCreatedAt) {
    this.eventCreatedAt = eventCreatedAt;
  }
}
