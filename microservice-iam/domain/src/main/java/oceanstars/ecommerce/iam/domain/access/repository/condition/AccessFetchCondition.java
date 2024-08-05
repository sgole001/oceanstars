package oceanstars.ecommerce.iam.domain.access.repository.condition;

import oceanstars.ecommerce.common.domain.repository.condition.BaseCondition;

/**
 * Access查询条件
 *
 * @author Clover
 * @version 1.0.0
 * @since 2024/7/8 17:02
 */
public class AccessFetchCondition extends BaseCondition {

  /**
   * 构造函数：根据构建器初始化成员变量
   *
   * @param builder 构建器
   */
  private AccessFetchCondition(Builder builder) {
    super(builder);
  }

  /**
   * 创建Token查询条件构建器
   *
   * @return Token查询条件构建器
   */
  public static Builder newBuilder() {
    return new Builder();
  }

  /**
   * Access查询条件构建器
   *
   * @author Clover
   * @version 1.0.0
   * @since 2024/7/8 17:03
   */
  public static final class Builder extends BaseCondition.Builder<AccessFetchCondition, AccessFetchCondition.Builder> {

    public Builder() {
    }

    @Override
    public AccessFetchCondition build() {
      return new AccessFetchCondition(this);
    }
  }
}
