package oceanstars.ecommerce.user.repository.constraint.factory;

import oceanstars.ecommerce.common.tools.JsonUtil;
import oceanstars.ecommerce.user.constant.enums.UserEnums.ConstraintRuleType;
import oceanstars.ecommerce.user.domain.constraint.entity.valueobject.ConstraintRuleValueObject;
import oceanstars.ecommerce.user.domain.constraint.entity.valueobject.RelationConstraintRuleValueObject;

/**
 * 约束规则工厂
 *
 * @author Clover
 * @version 1.0.0
 * @since 2024/6/28 18:43
 */
public class ConstraintRuleFactory {

  /**
   * 获取约束规则值对象
   *
   * @param ruleType 约束规则类型
   * @param ruleData 约束规则数据
   * @return 约束规则值对象
   */
  public static ConstraintRuleValueObject getConstraintRule(Short ruleType, String ruleData) {

    // 关系约束规则
    if (ConstraintRuleType.RELATION.key().equals(ruleType.intValue())) {
      return JsonUtil.parse(ruleData, RelationConstraintRuleValueObject.Builder.class).build();
    }
    // 会话约束规则
    else if (ConstraintRuleType.SESSION.key().equals(ruleType.intValue())) {
      return JsonUtil.parse(ruleData, RelationConstraintRuleValueObject.Builder.class).build();
    }
    // 账户约束规则
    else if (ConstraintRuleType.ACCOUNT.key().equals(ruleType.intValue())) {
      return JsonUtil.parse(ruleData, RelationConstraintRuleValueObject.Builder.class).build();
    }
    // 数据约束规则
    else if (ConstraintRuleType.DATA.key().equals(ruleType.intValue())) {
      return JsonUtil.parse(ruleData, RelationConstraintRuleValueObject.Builder.class).build();
    }
    // 其他约束规则, 抛出异常
    else {
      throw new IllegalArgumentException("Unsupported constraint rule type: " + ruleType);
    }
  }
}
