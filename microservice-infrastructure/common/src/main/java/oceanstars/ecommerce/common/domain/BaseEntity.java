package oceanstars.ecommerce.common.domain;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 领域实体基础信息类
 *
 * @author Clover
 * @version 1.0.0
 * @since 2021/11/3 4:38 下午
 */
public class BaseEntity implements Serializable {

  @Serial
  private static final long serialVersionUID = 3920322845526259714L;
  /**
   * 实体对象唯一识别ID
   */
  private Long id;

  /**
   * 实体对象创建时间
   */
  private LocalDateTime createAt;

  /**
   * 实体对象创建者
   */
  private String createBy;

  /**
   * 实体对象更新时间
   */
  private LocalDateTime updateAt;

  /**
   * 实体对象更新者
   */
  private String updateBy;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getCreateBy() {
    return createBy;
  }

  public void setCreateBy(String createBy) {
    this.createBy = createBy;
  }

  public String getUpdateBy() {
    return updateBy;
  }

  public void setUpdateBy(String updateBy) {
    this.updateBy = updateBy;
  }

  public LocalDateTime getCreateAt() {
    return createAt;
  }

  public void setCreateAt(LocalDateTime createAt) {
    this.createAt = createAt;
  }

  public LocalDateTime getUpdateAt() {
    return updateAt;
  }

  public void setUpdateAt(LocalDateTime updateAt) {
    this.updateAt = updateAt;
  }
}
