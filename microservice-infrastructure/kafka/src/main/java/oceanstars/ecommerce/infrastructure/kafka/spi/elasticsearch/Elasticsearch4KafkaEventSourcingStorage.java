package oceanstars.ecommerce.infrastructure.kafka.spi.elasticsearch;

import oceanstars.ecommerce.common.domain.event.DomainEvent;
import oceanstars.ecommerce.common.spring.ApplicationContextProvider;
import oceanstars.ecommerce.common.tools.JsonUtil;
import oceanstars.ecommerce.infrastructure.kafka.spi.KafkaEventSourcingStorageSPI;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;

/**
 * Kafka事件存储源策略：Elasticsearch
 *
 * @author Clover
 * @version 1.0.0
 * @since 2023/12/20 11:29
 */
public class Elasticsearch4KafkaEventSourcingStorage implements KafkaEventSourcingStorageSPI {

  /**
   * Elasticsearch操作器
   */
  private final ElasticsearchOperations elasticsearchOperations;

  public Elasticsearch4KafkaEventSourcingStorage() {
    this.elasticsearchOperations = (ElasticsearchOperations) ApplicationContextProvider.getApplicationContext().getBean("elasticsearchOperations");
  }

  @Override
  public <E extends DomainEvent<?, ?>> void store(E event) {

    // 获取事件ID
    final String eventId = event.getId();
    // 获取事件类型
    final String eventType = event.getClass().getName();
    // 获取事件发生时间
    final long eventTime = event.getTimestamp();
    // 获取事件源
    final String eventSource = event.getSource().toString();
    // 获取事件数据
    final String eventData = event.getData().getClass().getName() + "@" + JsonUtil.toString(event.getData());

    // 构建事件溯源存储对象
    ElasticsearchEventSourcingDomain storeObj = new ElasticsearchEventSourcingDomain.Builder()
        .eventId(eventId)
        .eventType(eventType)
        .eventTime(eventTime)
        .eventSource(eventSource)
        .eventData(eventData).build();

    storeObj = elasticsearchOperations.save(storeObj);
  }
}
