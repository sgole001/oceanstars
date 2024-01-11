package oceanstars.ecommerce.message.configuration.kafka;

import oceanstars.ecommerce.infrastructure.kafka.configuration.KafkaTopicsProperties;
import org.springframework.boot.autoconfigure.kafka.KafkaAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * 消息总线Topic信息集合
 *
 * @author Clover
 * @version 1.0.0
 * @since 2021/11/25 5:26 下午
 */
@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties(KafkaTopicsProperties.class)
@Import({KafkaAutoConfiguration.class})
public class TopicConfiguration {
//
//  /**
//   * Kafka的Topic配置信息
//   */
//  private final KafkaTopicsProperties properties;
//
//  /**
//   * 日志管理器
//   */
//  private final Logger logger = LoggerFactory.getLogger(TopicConfiguration.class);
//
//  /**
//   * 构造函数：初始化注入Kafka的Topic配置信息
//   *
//   * @param properties Kafka的Topic配置信息
//   */
//  public TopicConfiguration(KafkaTopicsProperties properties) {
//    this.properties = properties;
//  }
//
//  /**
//   * 构建Topic
//   *
//   * @param kafkaAdmin Kafka Admin对象
//   */
//  @Bean
//  public void newTopics(KafkaAdmin kafkaAdmin) {
//
//    // 初始化创建Kafka Topic列表
//    final List<NewTopic> kafkaTopics = new ArrayList<>();
//    // 获取Topic信息
//    final List<KafkaTopicProperties> topics = this.properties.getTopics();
//
//    if (CollectionUtils.isEmpty(topics)) {
//      return;
//    }
//
//    // 遍历Topic配置，添加Topic信息至Topic列表
//    topics.forEach(topicBean -> {
//
//      // 构建Topic
//      TopicBuilder topicBuilder = TopicBuilder.name(topicBean.getName());
//
//      if (null != topicBean.getPartitions()) {
//        topicBuilder.partitions(topicBean.getPartitions());
//      }
//      if (null != topicBean.getReplicas()) {
//        topicBuilder.replicas(topicBean.getReplicas());
//      }
//      if (null != topicBean.getCompact()) {
//        topicBuilder.compact();
//      }
//      if (null != topicBean.getReplicasAssignments()) {
//        topicBuilder.replicasAssignments(topicBean.getReplicasAssignments());
//      }
//      if (null != topicBean.getConfigs()) {
//        topicBuilder.configs(topicBean.getConfigs());
//      }
//
//      kafkaTopics.add(topicBuilder.build());
//    });
//
//    // 创建Kafka Admin客户端对象
//    try (AdminClient adminClient = AdminClient.create(kafkaAdmin.getConfigurationProperties())) {
//
//      // 批量创建Topic
//      CreateTopicsResult result = adminClient.createTopics(kafkaTopics);
//      // 阻塞直到所有Topic创建成功为止。
//      result.all().get();
//    } catch (InterruptedException | ExecutionException e) {
//      // 创建Topic异常日志记录
//      logger.error(e.getMessage());
//      // 中断当前线程
//      Thread.currentThread().interrupt();
//    }
//  }
}
