package oceanstars.ecommerce.user.domain.operation.entity;

import oceanstars.ecommerce.common.domain.AggregateRoot;

/**
 * 操作实体：聚合根
 *
 * @author Clover
 * @version 1.0.0
 * @since 2022/1/5 2:05 PM
 */
public final class OperationEntity extends AggregateRoot<OperationIdentifier> {

  /**
   * 操作行为
   */
  private final String behavior;

  /**
   * 操作说明
   */
  private String desc;

  /**
   * 操作逻辑有效标志位
   */
  private Boolean enabled;

  /**
   * 构造函数：根据构建器初始化成员变量
   *
   * @param builder 构建器
   */
  private OperationEntity(Builder builder) {
    super(new OperationIdentifier(builder.behavior));
    behavior = builder.behavior;
    desc = builder.desc;
    enabled = builder.enabled;
  }

  /**
   * 创建操作实体构建器
   *
   * @param behavior 操作行为
   * @return 操作实体构建器
   */
  public static Builder newBuilder(final String behavior) {
    return new Builder(behavior);
  }

  public String getBehavior() {
    return behavior;
  }

  public String getDesc() {
    return desc;
  }

  public Boolean getEnabled() {
    return enabled;
  }

  public void setDesc(String desc) {
    this.desc = desc;
  }

  public void setEnabled(Boolean enabled) {
    this.enabled = enabled;
  }

  /**
   * 操作实体构建器
   *
   * @author Clover
   * @version 1.0.0
   * @since 2022/1/6 12:13 AM
   */
  public static final class Builder {

    private final String behavior;
    private String desc;
    private Boolean enabled;

    public Builder(String behavior) {
      this.behavior = behavior;
    }

    public Builder desc(String val) {
      desc = val;
      return this;
    }

    public Builder enabled(Boolean val) {
      enabled = val;
      return this;
    }

    public OperationEntity build() {
      return new OperationEntity(this);
    }
  }
}
