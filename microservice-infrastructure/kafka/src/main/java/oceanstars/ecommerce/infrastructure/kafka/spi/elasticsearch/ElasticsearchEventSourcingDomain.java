package oceanstars.ecommerce.infrastructure.kafka.spi.elasticsearch;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

/**
 * 使用Elasticsearch作为事件溯源数据储存源
 *
 * @author Clover
 * @version 1.0.0
 * @since 2023/12/20 11:56
 */
@Document(indexName = "kafka-event-sourcing", createIndex = false)
public class ElasticsearchEventSourcingDomain {

  @Id
  private String id;

  /**
   * 事件ID
   */
  @Field(name = "event_id", type = FieldType.Keyword)
  private String eventId;

  /**
   * 事件类型
   */
  @Field(name = "event_type", type = FieldType.Keyword)
  private String eventType;

  /**
   * 事件发生时间
   */
  @Field(name = "event_time", type = FieldType.Date, format = {}, pattern = "uuuu-MM-dd HH:mm:ss")
  private Long eventTime;

  /**
   * 事件源
   */
  @Field(name = "event_source", type = FieldType.Text)
  private String eventSource;

  /**
   * 事件数据
   */
  @Field(name = "event_data")
  private String eventData;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getEventId() {
    return eventId;
  }

  public void setEventId(String eventId) {
    this.eventId = eventId;
  }

  public String getEventType() {
    return eventType;
  }

  public void setEventType(String eventType) {
    this.eventType = eventType;
  }

  public Long getEventTime() {
    return eventTime;
  }

  public void setEventTime(Long eventTime) {
    this.eventTime = eventTime;
  }

  public String getEventSource() {
    return eventSource;
  }

  public void setEventSource(String eventSource) {
    this.eventSource = eventSource;
  }

  public String getEventData() {
    return eventData;
  }

  public void setEventData(String eventData) {
    this.eventData = eventData;
  }

  private ElasticsearchEventSourcingDomain(Builder builder) {
    id = builder.id;
    this.eventId = builder.eventId;
    this.eventType = builder.eventType;
    this.eventTime = builder.eventTime;
    this.eventSource = builder.eventSource;
    this.eventData = builder.eventData;
  }

  public static final class Builder {

    private String id;
    private String eventId;
    private String eventType;
    private Long eventTime;
    private String eventSource;
    private String eventData;

    public Builder() {
    }

    public Builder id(String val) {
      id = val;
      return this;
    }

    public Builder eventId(String val) {
      eventId = val;
      return this;
    }

    public Builder eventType(String val) {
      eventType = val;
      return this;
    }

    public Builder eventTime(Long val) {
      eventTime = val;
      return this;
    }

    public Builder eventSource(String val) {
      eventSource = val;
      return this;
    }

    public Builder eventData(String val) {
      eventData = val;
      return this;
    }

    public ElasticsearchEventSourcingDomain build() {
      return new ElasticsearchEventSourcingDomain(this);
    }
  }
}
