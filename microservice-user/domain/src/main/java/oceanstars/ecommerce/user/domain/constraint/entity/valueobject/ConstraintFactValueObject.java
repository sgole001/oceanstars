package oceanstars.ecommerce.user.domain.constraint.entity.valueobject;

import java.io.Serial;
import java.util.Set;
import oceanstars.ecommerce.common.domain.entity.ValueObject;

/**
 * 约束规则事实值对象
 *
 * @author Clover
 * @version 1.0.0
 * @since 2024/6/4 18:50
 */
public class ConstraintFactValueObject extends ValueObject {

  @Serial
  private static final long serialVersionUID = -2719833059899844821L;

  /**
   * 分配列表
   */
  private final Set<Long> allocation;

  /**
   * 分配基数
   */
  private final Integer cardinality;

  /**
   * 构造函数：根据构建器初始化成员变量
   *
   * @param builder 构建器
   */
  private ConstraintFactValueObject(Builder builder) {
    allocation = builder.allocation;
    cardinality = builder.cardinality;
  }

  /**
   * 创建约束规则事实值对象构建器
   *
   * @return 约束规则事实值对象构建器
   */
  public static Builder newBuilder() {
    return new Builder();
  }

  public Set<Long> getAllocation() {
    return allocation;
  }

  public Integer getCardinality() {
    return cardinality;
  }

  /**
   * 创建约束规则事实值对象构建器
   *
   * @author Clover
   * @version 1.0.0
   * @since 2024/6/6 17:03
   */
  public static final class Builder {

    private Set<Long> allocation;
    private Integer cardinality;

    public Builder() {
    }

    public Builder allocation(Set<Long> val) {
      allocation = val;
      return this;
    }

    public Builder cardinality(Integer val) {
      cardinality = val;
      return this;
    }

    public ConstraintFactValueObject build() {
      return new ConstraintFactValueObject(this);
    }
  }
}
