package oceanstars.ecommerce.infrastructure.kafka.configuration;

import java.util.List;
import oceanstars.ecommerce.infrastructure.kafka.interceptor.Record4SessionInterceptor;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.boot.autoconfigure.kafka.KafkaAutoConfiguration;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.KafkaAdmin;
import org.springframework.kafka.core.KafkaAdmin.NewTopics;
import org.springframework.kafka.listener.CompositeRecordInterceptor;
import org.springframework.kafka.listener.RecordInterceptor;
import org.springframework.util.CollectionUtils;

/**
 * kafka配置信息
 *
 * @author Clover
 * @version 1.0.0
 * @since 2021/11/3 11:55 下午
 */
@EnableKafka
@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties({KafkaProperties.class, KafkaTopicsProperties.class})
@Import({KafkaAutoConfiguration.class})
public class KafkaConfig {

  /**
   * 消息拦截器(RecordInterceptor的实现)，提供在消费消息前后进行一些自定义的操作。
   *
   * @param <K> 消息键类型
   * @param <V> 消息值类型
   * @return 自定义消息拦截器对象
   */
  @Bean
  public <K, V> RecordInterceptor<K, V> recordInterceptor() {

    // 消息监听器容器拦截：解析头部session信息
    final Record4SessionInterceptor<K, V> record4SessionInterceptor = new Record4SessionInterceptor<>();

    return new CompositeRecordInterceptor<>(record4SessionInterceptor);
  }

  @Bean
  public KafkaAdmin.NewTopics topics(KafkaTopicsProperties properties) {

    // 获取Topic信息
    final List<KafkaTopicProperties> topics = properties.getTopics();

    if (CollectionUtils.isEmpty(topics)) {
      return null;
    }

    // 初始化创建Kafka Topic列表
    final NewTopic[] kafkaTopics = new NewTopic[topics.size()];

    // 遍历Topic配置，添加Topic信息至Topic列表
    for (int i = 0; i < topics.size(); i++) {

      // 获取Topic配置信息
      final KafkaTopicProperties topicBean = topics.get(i);

      // 构建Topic
      TopicBuilder topicBuilder = TopicBuilder.name(topicBean.getName());

      if (null != topicBean.getPartitions()) {
        topicBuilder.partitions(topicBean.getPartitions());
      }
      if (null != topicBean.getReplicas()) {
        topicBuilder.replicas(topicBean.getReplicas());
      }
      if (null != topicBean.getCompact()) {
        topicBuilder.compact();
      }
      if (null != topicBean.getReplicasAssignments()) {
        topicBuilder.replicasAssignments(topicBean.getReplicasAssignments());
      }
      if (null != topicBean.getConfigs()) {
        topicBuilder.configs(topicBean.getConfigs());
      }

      kafkaTopics[i] = topicBuilder.build();
    }

    return new NewTopics(kafkaTopics);
  }
}
