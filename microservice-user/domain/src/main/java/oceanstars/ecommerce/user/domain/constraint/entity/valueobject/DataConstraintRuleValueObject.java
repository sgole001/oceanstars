package oceanstars.ecommerce.user.domain.constraint.entity.valueobject;

import java.io.Serial;
import oceanstars.ecommerce.user.constant.enums.UserEnums.ConstraintRuleType;

/**
 * 约束规则值对象: 数据约束规则值对象
 *
 * @author Clover
 * @version 1.0.0
 * @since 2024/7/2 16:44
 */
public class DataConstraintRuleValueObject extends ConstraintRuleValueObject {

  @Serial
  private static final long serialVersionUID = -2284771745793650984L;

  /**
   * 构造函数：根据构建器初始化成员变量
   *
   * @param builder 构建器
   */
  private DataConstraintRuleValueObject(Builder builder) {
    super(builder);
  }

  /**
   * 创建数据约束规则值对象构建器
   *
   * @return 数据约束规则值对象构建器
   */
  public static Builder newBuilder() {
    return new Builder();
  }

  @Override
  public ConstraintRuleType determineRuleType() {
    return ConstraintRuleType.DATA;
  }

  @Override
  protected Boolean firedRule(ConstraintFactValueObject fact) {
    return Boolean.TRUE;
  }

  /**
   * 数据约束规则值对象构建器
   *
   * @author Clover
   * @version 1.0.0
   * @since 2024/6/6 11:05
   */
  public static final class Builder extends ConstraintRuleValueObject.Builder<DataConstraintRuleValueObject.Builder> {

    public Builder() {
    }

    public DataConstraintRuleValueObject build() {
      return new DataConstraintRuleValueObject(this);
    }
  }
}
