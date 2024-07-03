package oceanstars.ecommerce.user.repository.constraint.view.bo;

import java.util.List;
import java.util.Objects;
import oceanstars.ecommerce.user.repository.generate.tables.pojos.RelConstraintRulePojo;
import oceanstars.ecommerce.user.repository.generate.tables.pojos.UserConstraintPojo;

/**
 * 约束视图业务对象
 *
 * @author Clover
 * @version 1.0.0
 * @since 2024/6/28 14:42
 */
public class ConstraintView implements Comparable<ConstraintView> {

  /**
   * 约束数据
   */
  private UserConstraintPojo constraint;

  /**
   * 约束规则列表
   */
  private List<RelConstraintRulePojo> rules;

  public UserConstraintPojo getConstraint() {
    return constraint;
  }

  public void setConstraint(UserConstraintPojo constraint) {
    this.constraint = constraint;
  }

  public List<RelConstraintRulePojo> getRules() {
    return rules;
  }

  public void setRules(List<RelConstraintRulePojo> rules) {
    this.rules = rules;
  }

  @Override
  public int compareTo(ConstraintView o) {
    return this.getConstraint().getCreateAt().compareTo(o.getConstraint().getCreateAt());
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ConstraintView that = (ConstraintView) o;

    return this.constraint.getId().equals(that.constraint.getId());
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(constraint);
  }
}
