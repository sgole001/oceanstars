package oceanstars.ecommerce.common.restful;

import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Restful接口响应返回基础数据
 *
 * @author Clover
 * @version 1.0.0
 * @since 2021/11/3 3:32 下午
 */
@Schema(name = "BaseRestResponseData", description = "Restful接口响应返回基础数据")
public class BaseRestResponseData implements Serializable {

  @Serial
  private static final long serialVersionUID = 1943216699485982959L;
  /**
   * 唯一识别ID
   */
  @Schema(description = "唯一识别ID")
  private String id;

  /**
   * 创建时间
   */
  @Schema(description = "创建时间")
  private LocalDateTime createAt;

  /**
   * 创建者
   */
  @Schema(description = "创建者")
  private String createBy;

  /**
   * 更新时间
   */
  @Schema(description = "更新时间")
  private LocalDateTime updateAt;

  /**
   * 更新者
   */
  @Schema(description = "更新者")
  private String updateBy;

  public String getId() {
    return id;
  }

  public void setId(String id) {
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
