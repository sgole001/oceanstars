package oceanstars.ecommerce.message.configuration.kafka;

import java.util.List;
import java.util.Map;

/**
 * kafka主题配置Bean
 *
 * @author Clover
 * @version 1.0.0
 * @since 2021/11/25 5:25 下午
 */
public class KafkaTopicProperties {

  /**
   * 主题名
   */
  private String name;

  /**
   * 主题所需分区数量
   */
  private Integer partitions;

  /**
   * 主题所需备份数量
   */
  private Short replicas;

  /**
   * cleanup.policy配置是否compact
   */
  private Boolean compact;

  /**
   * 主题备份分配列表映射表
   */
  private Map<Integer, List<Integer>> replicasAssignments;

  /**
   * 主题其他配置信息映射表
   */
  private Map<String, String> configs;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Integer getPartitions() {
    return partitions;
  }

  public void setPartitions(Integer partitions) {
    this.partitions = partitions;
  }

  public Short getReplicas() {
    return replicas;
  }

  public void setReplicas(Short replicas) {
    this.replicas = replicas;
  }

  public Boolean getCompact() {
    return compact;
  }

  public void setCompact(Boolean compact) {
    this.compact = compact;
  }

  public Map<Integer, List<Integer>> getReplicasAssignments() {
    return replicasAssignments;
  }

  public void setReplicasAssignments(Map<Integer, List<Integer>> replicasAssignments) {
    this.replicasAssignments = replicasAssignments;
  }

  public Map<String, String> getConfigs() {
    return configs;
  }

  public void setConfigs(Map<String, String> configs) {
    this.configs = configs;
  }
}
