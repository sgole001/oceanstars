package oceanstars.ecommerce.common.dto;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * DTO基础类
 *
 * @author Clover
 * @version 1.0.0
 * @since 2021/11/3 4:38 下午
 */
public class BaseDto implements Serializable {

  @Serial
  private static final long serialVersionUID = 634574865595974115L;

  /**
   * 唯一识别ID
   */
  private Long id;

  /**
   * 创建时间
   */
  private LocalDateTime createAt;

  /**
   * 创建者
   */
  private String createBy;

  /**
   * 更新时间
   */
  private LocalDateTime updateAt;

  /**
   * 更新者
   */
  private String updateBy;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public LocalDateTime getCreateAt() {
    return createAt;
  }

  public void setCreateAt(LocalDateTime createAt) {
    this.createAt = createAt;
  }

  public String getCreateBy() {
    return createBy;
  }

  public void setCreateBy(String createBy) {
    this.createBy = createBy;
  }

  public LocalDateTime getUpdateAt() {
    return updateAt;
  }

  public void setUpdateAt(LocalDateTime updateAt) {
    this.updateAt = updateAt;
  }

  public String getUpdateBy() {
    return updateBy;
  }

  public void setUpdateBy(String updateBy) {
    this.updateBy = updateBy;
  }
}
