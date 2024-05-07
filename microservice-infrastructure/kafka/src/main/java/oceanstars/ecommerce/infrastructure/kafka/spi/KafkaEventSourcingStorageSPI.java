package oceanstars.ecommerce.infrastructure.kafka.spi;

import oceanstars.ecommerce.common.domain.event.DomainEvent;

/**
 * Kafka事件存储SPI
 *
 * @author Clover
 * @version 1.0.0
 * @since 2023/12/20 11:08
 */
public interface KafkaEventSourcingStorageSPI {

  <E extends DomainEvent<?, ?>> void store(E event);

}
