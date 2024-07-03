package oceanstars.ecommerce.user.domain.constraint.entity.valueobject;

import java.io.Serial;
import oceanstars.ecommerce.user.constant.enums.UserEnums.ConstraintRuleType;

/**
 * 约束规则值对象: 会话约束规则值对象
 *
 * @author Clover
 * @version 1.0.0
 * @since 2024/7/2 16:33
 */
public class SessionConstraintRuleValueObject extends ConstraintRuleValueObject {

  @Serial
  private static final long serialVersionUID = 2327472754703846746L;

  /**
   * 构造函数：根据构建器初始化成员变量
   *
   * @param builder 构建器
   */
  private SessionConstraintRuleValueObject(Builder builder) {
    super(builder);
  }

  /**
   * 创建会话约束规则值对象构建器
   *
   * @return 会话约束规则值对象构建器
   */
  public static Builder newBuilder() {
    return new Builder();
  }

  @Override
  public ConstraintRuleType determineRuleType() {
    return ConstraintRuleType.SESSION;
  }

  @Override
  protected Boolean firedRule(ConstraintFactValueObject fact) {
    return Boolean.TRUE;
  }

  /**
   * 会话约束规则值对象构建器
   *
   * @author Clover
   * @version 1.0.0
   * @since 2024/6/6 11:05
   */
  public static final class Builder extends ConstraintRuleValueObject.Builder<SessionConstraintRuleValueObject.Builder> {

    public Builder() {
    }

    public SessionConstraintRuleValueObject build() {
      return new SessionConstraintRuleValueObject(this);
    }
  }
}
