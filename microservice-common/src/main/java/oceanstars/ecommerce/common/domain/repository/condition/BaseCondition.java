package oceanstars.ecommerce.common.domain.repository.condition;

import java.time.LocalDateTime;

/**
 * 基础字段查询条件
 *
 * @author Clover
 * @version 1.0.0
 * @since 2024/4/24 10:46
 */
public class BaseCondition implements ICondition {

  /**
   * 领域对象委托ID
   */
  private final Long id;

  /**
   * 领域对象创建开始时间
   */
  private final LocalDateTime createStartTime;

  /**
   * 领域对象创建结束时间
   */
  private final LocalDateTime createEndTime;

  /**
   * 领域对象更新开始时间
   */
  private final LocalDateTime updateStartTime;

  /**
   * 领域对象更新结束时间
   */
  private final LocalDateTime updateEndTime;

  /**
   * 领域对象创建人
   */
  private final String createBy;

  /**
   * 领域对象更新人
   */
  private final String updateBy;

  /**
   * 构造函数
   *
   * @param builder 构造器
   */
  protected BaseCondition(Builder<?, ?> builder) {
    id = builder.id;
    createStartTime = builder.createStartTime;
    createEndTime = builder.createEndTime;
    updateStartTime = builder.updateStartTime;
    updateEndTime = builder.updateEndTime;
    createBy = builder.createBy;
    updateBy = builder.updateBy;
  }

  public static Builder<?, ?> newBuilder() {
    return new Builder<>();
  }

  public Long getId() {
    return id;
  }

  public LocalDateTime getCreateStartTime() {
    return createStartTime;
  }

  public LocalDateTime getCreateEndTime() {
    return createEndTime;
  }

  public LocalDateTime getUpdateStartTime() {
    return updateStartTime;
  }

  public LocalDateTime getUpdateEndTime() {
    return updateEndTime;
  }

  public String getCreateBy() {
    return createBy;
  }

  public String getUpdateBy() {
    return updateBy;
  }

  /**
   * 构造器
   *
   * @author Clover
   * @version 1.0.0
   * @since 2024/4/24 10:51
   */
  @SuppressWarnings("unchecked")
  public static class Builder<T extends BaseCondition, B extends Builder<T, B>> {

    private Long id;
    private LocalDateTime createStartTime;
    private LocalDateTime createEndTime;
    private LocalDateTime updateStartTime;
    private LocalDateTime updateEndTime;
    private String createBy;
    private String updateBy;

    public B id(Long val) {
      id = val;
      return (B) this;
    }

    public B createStartTime(LocalDateTime val) {
      createStartTime = val;
      return (B) this;
    }

    public B createEndTime(LocalDateTime val) {
      createEndTime = val;
      return (B) this;
    }

    public B updateStartTime(LocalDateTime val) {
      updateStartTime = val;
      return (B) this;
    }

    public B updateEndTime(LocalDateTime val) {
      updateEndTime = val;
      return (B) this;
    }

    public B createBy(String val) {
      createBy = val;
      return (B) this;
    }

    public B updateBy(String val) {
      updateBy = val;
      return (B) this;
    }

    public T build() {
      return (T) new BaseCondition(this);
    }
  }
}
