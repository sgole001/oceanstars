package oceanstars.ecommerce.ecm.domain.asset.repository.condition.ip;

import oceanstars.ecommerce.ecm.domain.asset.repository.condition.AssetFetchCondition;

/**
 * 功能查询条件
 *
 * @author Clover
 * @version 1.0.0
 * @since 2024/4/24 10:56
 */
public class FunctionFetchCondition extends AssetFetchCondition {

  /**
   * 功能隶属
   */
  private final Long parent;

  /**
   * 构造函数
   *
   * @param builder 构造器
   */
  private FunctionFetchCondition(Builder builder) {
    super(builder);
    parent = builder.parent;
  }

  /**
   * 创建构造器
   *
   * @return 构造器
   */
  public static Builder newBuilder() {
    return new Builder();
  }

  public Long getParent() {
    return parent;
  }

  /**
   * 功能查询条件构造器
   *
   * @author Clover
   * @version 1.0.0
   * @since 2024/4/24 10:55
   */
  public static final class Builder extends AssetFetchCondition.Builder {

    private Long parent;

    public Builder parent(Long val) {
      parent = val;
      return this;
    }

    @Override
    public FunctionFetchCondition build() {
      return new FunctionFetchCondition(this);
    }
  }
}
