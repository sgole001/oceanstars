package oceanstars.ecommerce.user.domain.constraint.repository.condition;

import java.util.Set;
import oceanstars.ecommerce.common.domain.repository.condition.BaseCondition;
import oceanstars.ecommerce.user.constant.enums.UserEnums.ConstraintRuleType;
import oceanstars.ecommerce.user.constant.enums.UserEnums.ConstraintType;

/**
 * 约束查询条件
 *
 * @author Clover
 * @version 1.0.0
 * @since 2024/6/27 15:18
 */
public class ConstraintFetchCondition extends BaseCondition {

  /**
   * 约束名称
   */
  private final String name;

  /**
   * 约束类型
   */
  private final Set<ConstraintType> types;

  /**
   * 约束规则类型
   */
  private final Set<ConstraintRuleType> ruleTypes;

  /**
   * 构造函数：根据构建器初始化成员变量
   *
   * @param builder 构建器
   */
  private ConstraintFetchCondition(Builder builder) {
    super(builder);
    name = builder.name;
    types = builder.types;
    ruleTypes = builder.ruleTypes;
  }

  /**
   * 创建约束查询条件构建器
   *
   * @return 约束查询条件构建器
   */
  public static Builder newBuilder() {
    return new Builder();
  }

  public String getName() {
    return name;
  }

  public Set<ConstraintType> getTypes() {
    return types;
  }

  public Set<ConstraintRuleType> getRuleTypes() {
    return ruleTypes;
  }

  /**
   * 构建器
   *
   * @author Clover
   * @version 1.0.0
   * @since 2024/6/27 15:41
   */
  public static final class Builder extends BaseCondition.Builder<ConstraintFetchCondition, ConstraintFetchCondition.Builder> {

    private String name;
    private Set<ConstraintType> types;
    private Set<ConstraintRuleType> ruleTypes;

    public Builder() {
    }

    public Builder name(String val) {
      name = val;
      return this;
    }

    public Builder types(Set<ConstraintType> val) {
      types = val;
      return this;
    }

    public Builder ruleTypes(Set<ConstraintRuleType> val) {
      ruleTypes = val;
      return this;
    }

    @Override
    public ConstraintFetchCondition build() {
      return new ConstraintFetchCondition(this);
    }
  }
}
