package oceanstars.ecommerce.user.domain.constraint.entity.valueobject;

import java.io.Serial;
import java.time.LocalDateTime;
import oceanstars.ecommerce.common.domain.entity.ValueObject;
import oceanstars.ecommerce.common.tools.SessionUtil;
import oceanstars.ecommerce.user.constant.enums.UserEnums.ConstraintRuleType;

/**
 * 约束规则值对象
 *
 * @author Clover
 * @version 1.0.0
 * @since 2024/6/4 16:51
 */
public abstract class ConstraintRuleValueObject extends ValueObject {

  @Serial
  private static final long serialVersionUID = 2340603164495890865L;

  /**
   * 规则执行优先级(数值越大优先级越高)
   */
  private final Integer salience;

  /**
   * 规则是否启用
   */
  private final Boolean enabled;

  /**
   * 规则生效时间
   */
  private final LocalDateTime effective;

  /**
   * 规则失效时间
   */
  private final LocalDateTime expire;

  /**
   * 构造函数：根据构建器初始化成员变量
   *
   * @param builder 构建器
   */
  protected ConstraintRuleValueObject(Builder<?> builder) {
    salience = builder.salience;
    enabled = builder.enabled;
    effective = builder.effective;
    expire = builder.expire;
  }

  /**
   * 约束规则匹配
   *
   * @param fact 约束规则事实值对象
   * @return 匹配结果
   */
  public Boolean isSatisfied(final ConstraintFactValueObject fact) {

    if (this.isActivated()) {
      return this.firedRule(fact);
    }

    return Boolean.TRUE;
  }

  /**
   * 通用规则检查
   *
   * @return 检查结果
   */
  private Boolean isActivated() {

    // 判断规则是否启用
    if (!this.enabled) {
      return Boolean.FALSE;
    }

    // 获取会话发起时间
    final LocalDateTime time = SessionUtil.getSessionAttribute().getTime();

    // 判断规则是否生效
    if (null != this.effective && time.isBefore(this.effective)) {
      return Boolean.FALSE;
    }
    // 判断规则是否失效
    if (null != this.expire && time.isAfter(this.expire)) {
      return Boolean.FALSE;
    }

    return Boolean.TRUE;
  }

  /**
   * 确定规则类型
   *
   * @return 规则类型
   */
  public abstract ConstraintRuleType determineRuleType();

  /**
   * 业务规则检查
   *
   * @param fact 约束规则事实值对象
   * @return 检查结果
   */
  protected abstract Boolean firedRule(final ConstraintFactValueObject fact);

  public Integer getSalience() {
    return salience;
  }

  public Boolean getEnabled() {
    return enabled;
  }

  public LocalDateTime getEffective() {
    return effective;
  }

  public LocalDateTime getExpire() {
    return expire;
  }

  /**
   * 构建器
   *
   * @author Clover
   * @version 1.0.0
   * @since 2024/6/5 17:49
   */
  @SuppressWarnings("unchecked")
  public static class Builder<B extends Builder<B>> {

    private Integer salience = 0;
    private Boolean enabled = java.lang.Boolean.TRUE;
    private LocalDateTime effective;
    private LocalDateTime expire;

    public Builder() {
    }

    public B salience(Integer val) {
      salience = val;
      return (B) this;
    }

    public B enabled(Boolean val) {
      enabled = val;
      return (B) this;
    }

    public B effective(LocalDateTime val) {
      effective = val;
      return (B) this;
    }

    public B expire(LocalDateTime val) {
      expire = val;
      return (B) this;
    }
  }
}
