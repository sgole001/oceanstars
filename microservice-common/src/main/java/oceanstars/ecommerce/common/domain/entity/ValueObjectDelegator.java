package oceanstars.ecommerce.common.domain.entity;

import java.time.LocalDateTime;

/**
 * 领域值对象委托者信息类
 *
 * @author Clover
 * @version 1.0.0
 * @since 2021/11/3 4:38 下午
 */
public class ValueObjectDelegator {

  /**
   * 值对象委托者唯一识别符
   */
  private final Long id;

  /**
   * 值对象委托者审计-创建时间
   */
  private final LocalDateTime createAt;

  /**
   * 值对象委托者审计-创建者
   */
  private final String createBy;

  /**
   * 私有构造函数:使用构造器构造值对象委托者对象
   *
   * @param builder 值对象代理构造器
   */
  private ValueObjectDelegator(Builder builder) {
    id = builder.id;
    createAt = builder.createAt;
    createBy = builder.createBy;
  }

  /**
   * 创建领域值对象委托者构造器
   *
   * @param id       代理键
   * @param createAt 代理创建时间
   * @param createBy 代理创建者
   * @return 领域值对象代理构造器
   */
  public static ValueObjectDelegator.Builder newBuilder(Long id, LocalDateTime createAt, String createBy) {
    return new Builder(id, createAt, createBy);
  }

  /**
   * 创建领域值对象委托者构造器
   *
   * @param id 代理键
   * @return 领域值对象代理构造器
   */
  public static ValueObjectDelegator.Builder newBuilder(Long id) {
    return new Builder(id, null, null);
  }

  public Long getId() {
    return id;
  }

  public String getCreateBy() {
    return createBy;
  }

  public LocalDateTime getCreateAt() {
    return createAt;
  }

  /**
   * 领域值对象委托者构建器
   *
   * @author Clover
   * @version 1.0.0
   * @since 2021/12/2 6:30 下午
   */
  public static final class Builder {

    private final Long id;
    private final LocalDateTime createAt;
    private final String createBy;

    public Builder(Long id, LocalDateTime createAt, String createBy) {
      this.id = id;
      this.createAt = createAt;
      this.createBy = createBy;
    }

    public ValueObjectDelegator build() {
      return new ValueObjectDelegator(this);
    }
  }
}
