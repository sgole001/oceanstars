package oceanstars.ecommerce.infrastructure.kafka.configuration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import oceanstars.ecommerce.infrastructure.kafka.interceptor.Consumer4SessionInterceptor;
import oceanstars.ecommerce.infrastructure.kafka.interceptor.Producer4SessionInterceptor;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;

/**
 * kafka配置信息
 *
 * @author Clover
 * @version 1.0.0
 * @since 2021/11/3 11:55 下午
 */
@EnableKafka
@Configuration
public class KafkaConfig {

  /**
   * 消息生产者配置信息
   */
  @Resource(name = "kafkaProducerConfig")
  private KafkaProducerConfigBean producerConfigBean;

  /**
   * 消息消费者配置信息
   */
  @Resource(name = "kafkaConsumerConfig")
  private KafkaConsumerConfigBean consumerConfigBean;

  /**
   * Admin配置信息Bean
   */
  @Resource(name = "kafkaAdminConfig")
  private KafkaAdminConfigBean adminConfigBean;

  /**
   * 构建消息管理配置
   *
   * @return 消息管理配置
   */
  @Bean(name = "kafkaAdminConfigs")
  public Map<String, Object> adminConfigs() {

    Map<String, Object> configs = new HashMap<>(1);
    configs.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, adminConfigBean.getBootstrapServers());

    return configs;
  }

  /**
   * 构建消息生产者工厂类
   *
   * @return 消息生产者工厂类
   * @throws Exception 构建异常
   */
  @Bean
  public ProducerFactory<Integer, String> producerFactory() throws Exception {
    return new DefaultKafkaProducerFactory<>(this.producerConfigs());
  }

  /**
   * 构建消息生产者配置信息
   *
   * @return 消息生产者配置信息
   * @throws Exception 构建异常
   */
  @Bean
  public Map<String, Object> producerConfigs() throws Exception {
    Map<String, Object> props = new HashMap<>();
    props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, producerConfigBean.getBootstrapServers());
    props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, Class.forName(producerConfigBean.getKeySerializer()));
    props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, Class.forName(producerConfigBean.getValueSerializer()));
    // 生产端拦截器
    List<String> interceptors = new ArrayList<>();
    interceptors.add(Producer4SessionInterceptor.class.getName());
    props.put(ProducerConfig.INTERCEPTOR_CLASSES_CONFIG, interceptors);
    // See https://kafka.apache.org/documentation/#producerconfigs for more properties
    return props;
  }

  /**
   * 构建消息发送模板对象
   *
   * @return 消息发送模板对象
   * @throws Exception 构建异常
   */
  @Bean
  public KafkaTemplate<Integer, String> kafkaTemplate() throws Exception {
    return new KafkaTemplate<>(this.producerFactory());
  }

  /**
   * 构建消息监听容器工厂对象
   *
   * @return 消息监听容器工厂对象
   * @throws Exception 构建异常
   */
  @Bean
  KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<Integer, String>> kafkaListenerContainerFactory() throws Exception {
    ConcurrentKafkaListenerContainerFactory<Integer, String> factory = new ConcurrentKafkaListenerContainerFactory<>();
    factory.setConsumerFactory(this.consumerFactory());
    factory.setConcurrency(consumerConfigBean.getConcurrency());
    factory.getContainerProperties().setPollTimeout(consumerConfigBean.getPollTimeout());
    factory.setRecordInterceptor(new Consumer4SessionInterceptor<>());
    return factory;
  }

  /**
   * 构建消息消费者工厂类
   *
   * @return 消息消费者工厂类
   * @throws Exception 构建异常
   */
  @Bean
  public ConsumerFactory<Integer, String> consumerFactory() throws Exception {
    return new DefaultKafkaConsumerFactory<>(this.consumerConfigs());
  }

  /**
   * 构建消息消费者配置信息
   *
   * @return 消息消费者配置信息
   * @throws Exception 构建异常
   */
  @Bean
  public Map<String, Object> consumerConfigs() throws Exception {
    Map<String, Object> props = new HashMap<>();
    props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, consumerConfigBean.getBootstrapServers());
    props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, Class.forName(consumerConfigBean.getKeyDeserializer()));
    props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, Class.forName(consumerConfigBean.getValueDeserializer()));
    // See https://kafka.apache.org/documentation/#consumerconfigs for more properties
    return props;
  }

//  @Override
//  public void configureKafkaListeners(KafkaListenerEndpointRegistrar kafkaListenerEndpointRegistrar) {
//    kafkaListenerEndpointRegistrar.setValidator(this.validator);
//  }
}
