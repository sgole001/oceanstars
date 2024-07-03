package oceanstars.ecommerce.user.domain.constraint.entity;

import java.util.List;
import oceanstars.ecommerce.common.domain.entity.AggregateRoot;
import oceanstars.ecommerce.user.constant.enums.UserEnums.ConstraintType;
import oceanstars.ecommerce.user.domain.constraint.entity.valueobject.ConstraintRuleValueObject;

/**
 * 约束实体: 聚合根
 *
 * @author Clover
 * @version 1.0.0
 * @since 2024/6/3 18:11
 */
public final class Constraint extends AggregateRoot<ConstraintIdentifier> {

  /**
   * 约束类型
   */
  private final ConstraintType type;

  /**
   * 约束说明
   */
  private String description;

  /**
   * 约束规则列表
   */
  private List<ConstraintRuleValueObject> rules;

  /**
   * 构造函数：根据构建器初始化成员变量
   *
   * @param builder 构建器
   */
  private Constraint(Builder builder) {
    super(new ConstraintIdentifier(builder.name));
    type = builder.type;
    description = builder.description;
    rules = builder.rules;
  }

  /**
   * 创建约束实体构建器
   *
   * @return 约束实体构建器
   */
  public static Builder newBuilder(String name, ConstraintType type) {
    return new Builder(name, type);
  }

  public ConstraintType getType() {
    return type;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public List<ConstraintRuleValueObject> getRules() {
    return rules;
  }

  public void setRules(List<ConstraintRuleValueObject> rules) {
    this.rules = rules;
  }

  /**
   * 构建器
   *
   * @author Clover
   * @version 1.0.0
   * @since 2024/6/4 16:56
   */
  public static final class Builder {

    private final String name;
    private final ConstraintType type;
    private String description;
    private List<ConstraintRuleValueObject> rules;

    public Builder(String name, ConstraintType type) {
      this.name = name;
      this.type = type;
    }

    public Builder description(String val) {
      description = val;
      return this;
    }

    public Builder rules(List<ConstraintRuleValueObject> val) {
      rules = val;
      return this;
    }

    public Constraint build() {
      return new Constraint(this);
    }
  }
}
