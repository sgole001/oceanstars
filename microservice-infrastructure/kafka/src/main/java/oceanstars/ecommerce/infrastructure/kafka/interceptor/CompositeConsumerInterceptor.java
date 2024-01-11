package oceanstars.ecommerce.infrastructure.kafka.interceptor;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import org.apache.kafka.clients.consumer.ConsumerInterceptor;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.OffsetAndMetadata;
import org.apache.kafka.common.TopicPartition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

/**
 * Kafka消费者组合拦截器
 *
 * @author Clover
 * @version 1.0.0
 * @since 2023/12/8 13:59
 */
public class CompositeConsumerInterceptor<K, V> implements ConsumerInterceptor<K, V>, Cloneable {

  /**
   * 消费者拦截器集合代理
   */
  private final List<ConsumerInterceptor<K, V>> delegates = new ArrayList<>();

  /**
   * 日志管理器
   */
  private final Logger logger = LoggerFactory.getLogger(CompositeConsumerInterceptor.class);

  @SafeVarargs
  public CompositeConsumerInterceptor(ConsumerInterceptor<K, V>... delegates) {
    Assert.notNull(delegates, "'delegates' cannot be null");
    Assert.noNullElements(delegates, "'delegates' cannot have null entries");
    this.delegates.addAll(Arrays.asList(delegates));
  }

  @Override
  public ConsumerRecords<K, V> onConsume(ConsumerRecords<K, V> consumerRecords) {

    // 消费数据拦截处理后对象
    ConsumerRecords<K, V> interceptRecords = consumerRecords;

    // 消费者拦截器迭代处理
    for (ConsumerInterceptor<K, V> interceptor : this.delegates) {
      try {
        // 消费者拦截器onConsume处理
        interceptRecords = interceptor.onConsume(consumerRecords);
      } catch (Exception ex) {
        logger.error(MessageFormat.format("Error executing interceptor onConsume callback: {0}", interceptor), ex);
      }
    }

    return interceptRecords;
  }

  @Override
  public void onCommit(Map<TopicPartition, OffsetAndMetadata> map) {

    // 消费者拦截器迭代处理
    for (ConsumerInterceptor<K, V> interceptor : this.delegates) {
      try {
        // 消费者拦截器onCommit处理
        interceptor.onCommit(map);
      } catch (Exception ex) {
        logger.error(MessageFormat.format("Error executing interceptor onCommit callback: {0}", interceptor), ex);
      }
    }
  }

  @Override
  public void close() {

    // 消费者拦截器迭代处理
    for (ConsumerInterceptor<K, V> interceptor : this.delegates) {
      try {
        // 消费者拦截器close处理
        interceptor.close();
      } catch (Exception ex) {
        logger.error(MessageFormat.format("Error executing interceptor close callback: {0}", interceptor), ex);
      }
    }
  }

  @Override
  public void configure(Map<String, ?> map) {

    // 消费者拦截器迭代处理
    for (ConsumerInterceptor<K, V> interceptor : this.delegates) {
      try {
        // 消费者拦截器configure处理
        interceptor.configure(map);
      } catch (Exception ex) {
        logger.error(MessageFormat.format("Error executing interceptor configure callback: {0}", interceptor), ex);
      }
    }
  }
}
