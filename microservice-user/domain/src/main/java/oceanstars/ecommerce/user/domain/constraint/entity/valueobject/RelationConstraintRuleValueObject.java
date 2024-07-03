package oceanstars.ecommerce.user.domain.constraint.entity.valueobject;

import java.io.Serial;
import java.util.Set;
import oceanstars.ecommerce.user.constant.enums.UserEnums.ConstraintRuleType;
import org.springframework.util.CollectionUtils;

/**
 * 约束规则值对象: 关系约束规则值对象
 *
 * @author Clover
 * @version 1.0.0
 * @since 2024/6/5 18:35
 */
public class RelationConstraintRuleValueObject extends ConstraintRuleValueObject {

  @Serial
  private static final long serialVersionUID = 2101934933836598127L;

  /**
   * 互斥关系列表
   */
  private final Set<Long> exclusivity;

  /**
   * 最大基数
   */
  private final Integer maxCardinality;

  /**
   * 最小基数
   */
  private final Integer minCardinality;

  /**
   * 构造函数：根据构建器初始化成员变量
   *
   * @param builder 构建器
   */
  private RelationConstraintRuleValueObject(Builder builder) {
    super(builder);
    exclusivity = builder.exclusivity;
    maxCardinality = builder.maxCardinality;
    minCardinality = builder.minCardinality;
  }

  /**
   * 创建关系约束规则值对象构建器
   *
   * @return 关系约束规则值对象构建器
   */
  public static Builder newBuilder() {
    return new Builder();
  }

  @Override
  public ConstraintRuleType determineRuleType() {
    return ConstraintRuleType.RELATION;
  }

  @Override
  protected Boolean firedRule(final ConstraintFactValueObject fact) {

    // 约束条件检查：关系最大基数
    if (this.maxCardinality != null) {
      if (fact.getCardinality() == null || fact.getCardinality() > this.maxCardinality) {
        return Boolean.FALSE;
      }
    }

    // 约束条件检查：关系最小基数
    if (this.minCardinality != null) {
      if (fact.getCardinality() == null || fact.getCardinality() < this.minCardinality) {
        return Boolean.FALSE;
      }
    }

    // 约束条件检查：互斥关系
    if (!CollectionUtils.isEmpty(this.exclusivity)) {

      if (CollectionUtils.isEmpty(fact.getAllocation())) {
        return Boolean.FALSE;
      }

      for (Long id : this.exclusivity) {
        if (fact.getAllocation().contains(id)) {
          return Boolean.FALSE;
        }
      }
    }

    return Boolean.TRUE;
  }

  public Set<Long> getExclusivity() {
    return exclusivity;
  }

  public Integer getMaxCardinality() {
    return maxCardinality;
  }

  public Integer getMinCardinality() {
    return minCardinality;
  }

  /**
   * 关系约束规则值对象构建器
   *
   * @author Clover
   * @version 1.0.0
   * @since 2024/6/6 11:05
   */
  public static final class Builder extends ConstraintRuleValueObject.Builder<Builder> {

    private Set<Long> exclusivity;
    private Integer maxCardinality;
    private Integer minCardinality;

    public Builder() {
    }

    public Builder exclusivity(Set<Long> val) {
      exclusivity = val;
      return this;
    }

    public Builder maxCardinality(Integer val) {
      maxCardinality = val;
      return this;
    }

    public Builder minCardinality(Integer val) {
      minCardinality = val;
      return this;
    }

    public RelationConstraintRuleValueObject build() {
      return new RelationConstraintRuleValueObject(this);
    }
  }
}