package oceanstars.ecommerce.common.controller.data;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.time.LocalDateTime;

/**
 * API基础响应参数
 *
 * @author Clover
 * @version 1.0.0
 * @since 2021/11/3 3:32 下午
 */
@ApiModel(value = "BaseData", description = "API基础响应参数")
public class BaseData {

  /**
   * 唯一识别ID
   */
  @ApiModelProperty(value = "唯一识别ID")
  private String id;

  /**
   * 创建时间
   */
  @ApiModelProperty(value = "创建时间")
  private LocalDateTime createAt;

  /**
   * 创建者
   */
  @ApiModelProperty(value = "创建者")
  private String createBy;

  /**
   * 更新时间
   */
  @ApiModelProperty(value = "更新时间")
  private LocalDateTime updateAt;

  /**
   * 更新者
   */
  @ApiModelProperty(value = "更新者")
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
