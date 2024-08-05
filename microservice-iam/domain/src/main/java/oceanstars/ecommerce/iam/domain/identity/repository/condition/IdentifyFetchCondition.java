package oceanstars.ecommerce.iam.domain.identity.repository.condition;

import oceanstars.ecommerce.common.domain.repository.condition.BaseCondition;

/**
 * 身份查询条件
 *
 * @author Clover
 * @version 1.0.0
 * @since 2024/7/4 19:03
 */
public class IdentifyFetchCondition extends BaseCondition {

  /**
   * 构造函数：根据构建器初始化成员变量
   *
   * @param builder 构建器
   */
  private IdentifyFetchCondition(Builder builder) {
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
   * Token查询条件构建器
   *
   * @author Clover
   * @version 1.0.0
   * @since 2024/7/4 19:03
   */
  public static final class Builder extends BaseCondition.Builder<IdentifyFetchCondition, IdentifyFetchCondition.Builder> {

    public Builder() {
    }

    @Override
    public IdentifyFetchCondition build() {
      return new IdentifyFetchCondition(this);
    }
  }
}
