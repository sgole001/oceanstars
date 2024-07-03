package oceanstars.ecommerce.user.api.rest.v1.request.constraint.data;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;

/**
 * 约束规则数据: 关系约束规则
 *
 * @author Clover
 * @version 1.0.0
 * @since 2024/7/1 17:22
 */
@Schema(name = "RelationConstraintRule", description = "关系约束规则")
public class RelationConstraintRule extends BaseConstraintRule {

  /**
   * 互斥关系列表
   */
  @Schema(description = "互斥关系列表")
  private List<Long> exclusivity;

  /**
   * 最大基数
   */
  @Schema(description = "最大基数")
  private Integer maxCardinality;

  /**
   * 最小基数
   */
  @Schema(description = "最小基数")
  private Integer minCardinality;

  public List<Long> getExclusivity() {
    return exclusivity;
  }

  public void setExclusivity(List<Long> exclusivity) {
    this.exclusivity = exclusivity;
  }

  public Integer getMaxCardinality() {
    return maxCardinality;
  }

  public void setMaxCardinality(Integer maxCardinality) {
    this.maxCardinality = maxCardinality;
  }

  public Integer getMinCardinality() {
    return minCardinality;
  }

  public void setMinCardinality(Integer minCardinality) {
    this.minCardinality = minCardinality;
  }
}
