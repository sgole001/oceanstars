package oceanstars.ecommerce.infrastructure.kafka.configuration;

import oceanstars.ecommerce.infrastructure.kafka.interceptor.Record4SessionInterceptor;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.listener.RecordInterceptor;

/**
 * kafka配置信息
 *
 * @author Clover
 * @version 1.0.0
 * @since 2021/11/3 11:55 下午
 */
@EnableKafka
@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties({KafkaProperties.class})
public class KafkaConfig {

//  /**
//   * Spring Kafka自动配置信息
//   */
//  private final KafkaProperties properties;
//
//  /**
//   * 构造函数：初始化Spring Kafka自动配置信息
//   *
//   * @param properties Spring Kafka自动配置信息
//   */
//  public KafkaConfig(KafkaProperties properties) {
//    this.properties = properties;
//  }

  @Bean
  public RecordInterceptor<Object, Object> recordInterceptor() {
    return new Record4SessionInterceptor<>();
  }

//  /**
//   * 构建消息生产者工厂类
//   *
//   * @param customizers 生产者工厂配置类
//   * @return 生产者工厂对象
//   */
//  @Bean
//  public ProducerFactory<Object, Object> kafkaProducerFactory(ObjectProvider<DefaultKafkaProducerFactoryCustomizer> customizers) {
//
//    // 构建Kafka消息生产者配置信息
//    final Map<String, Object> configs = this.properties.buildProducerProperties();
//    // 生产端拦截器
//    final List<String> interceptors = new ArrayList<>();
//    interceptors.add(Producer4SessionInterceptor.class.getName());
//    configs.put(ProducerConfig.INTERCEPTOR_CLASSES_CONFIG, interceptors);
//
//    // 初始化创建Kafka消息生产者工厂对象
//    final DefaultKafkaProducerFactory<Object, Object> factory = new DefaultKafkaProducerFactory<>(configs);
//    // 事务前缀设定
//    String transactionIdPrefix = this.properties.getProducer().getTransactionIdPrefix();
//    if (transactionIdPrefix != null) {
//      factory.setTransactionIdPrefix(transactionIdPrefix);
//    }
//
//    // 根据生产者工厂配置类配置Kafka消息生产者工厂对象
//    customizers.orderedStream().forEach(customizer -> customizer.customize(factory));
//
//    return factory;
//  }
//
//  @Bean
//  public ConsumerFactory<Object, Object> kafkaConsumerFactory(ObjectProvider<DefaultKafkaConsumerFactoryCustomizer> customizers) {
//
//    // 构建Kafka消息消费者配置信息
//    final Map<String, Object> configs = this.properties.buildConsumerProperties();
//    // 消费端拦截器
//    final List<String> interceptors = new ArrayList<>();
//    interceptors.add(Consumer4AckInterceptor.class.getName());
//    configs.put(ProducerConfig.INTERCEPTOR_CLASSES_CONFIG, interceptors);
//
//    final DefaultKafkaConsumerFactory<Object, Object> factory = new DefaultKafkaConsumerFactory<>(configs);
//    customizers.orderedStream().forEach(customizer -> customizer.customize(factory));
//    return factory;
//  }

}
