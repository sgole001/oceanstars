package oceanstars.ecommerce.common.domain;

import java.time.LocalDateTime;

/**
 * 领域实体委托者类
 *
 * @author Clover
 * @version 1.0.0
 * @since 2021/11/3 4:38 下午
 */
public class EntityDelegator {

  /**
   * 实体委托者唯一标识符
   */
  private final Long id;

  /**
   * 实体委托者审计-创建时间
   */
  private LocalDateTime createAt;

  /**
   * 实体委托者审计-创建者
   */
  private String createBy;

  /**
   * 实体委托者审计-更新时间
   */
  private LocalDateTime updateAt;

  /**
   * 实体委托者审计-更新者
   */
  private String updateBy;

  /**
   * 实体委托者审计-乐观锁
   */
  private Integer version;

  private EntityDelegator(Builder builder) {
    id = builder.id;
    createAt = builder.createAt;
    createBy = builder.createBy;
    setUpdateAt(builder.updateAt);
    setUpdateBy(builder.updateBy);
    setVersion(builder.version);
  }

  /**
   * 创建领域实体代理构造器
   *
   * @param id 代理键
   * @return 领域实体代理构造器
   */
  public static Builder newBuilder(Long id) {
    return new Builder(id);
  }

  public Long getId() {
    return id;
  }

  public String getCreateBy() {
    return createBy;
  }

  public String getUpdateBy() {
    return updateBy;
  }

  public LocalDateTime getCreateAt() {
    return createAt;
  }

  public LocalDateTime getUpdateAt() {
    return updateAt;
  }

  public Integer getVersion() {
    return version;
  }

  //***********************************************
  //*****   消息实体可变属性开放对象属性设定接口    *****
  //***********************************************
  public void setCreateAt(LocalDateTime createAt) {
    this.createAt = createAt;
  }

  public void setCreateBy(String createBy) {
    this.createBy = createBy;
  }

  public void setUpdateAt(LocalDateTime updateAt) {
    this.updateAt = updateAt;
  }

  public void setUpdateBy(String updateBy) {
    this.updateBy = updateBy;
  }

  public void setVersion(Integer version) {
    this.version = version;
  }

  /**
   * 领域实体委托者构建器
   *
   * @author Clover
   * @version 1.0.0
   * @since 2021/12/2 6:30 下午
   */
  public static final class Builder {

    private final Long id;
    private LocalDateTime createAt;
    private String createBy;
    private LocalDateTime updateAt;
    private String updateBy;
    private Integer version;

    public Builder(Long id) {
      this.id = id;
    }

    public Builder createAt(LocalDateTime val) {
      createAt = val;
      return this;
    }

    public Builder createBy(String val) {
      createBy = val;
      return this;
    }

    public Builder updateAt(LocalDateTime val) {
      updateAt = val;
      return this;
    }

    public Builder updateBy(String val) {
      updateBy = val;
      return this;
    }

    public Builder version(Integer val) {
      version = val;
      return this;
    }

    public EntityDelegator build() {
      return new EntityDelegator(this);
    }
  }
}
