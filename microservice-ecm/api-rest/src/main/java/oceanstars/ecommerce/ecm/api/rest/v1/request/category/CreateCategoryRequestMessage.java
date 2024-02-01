package oceanstars.ecommerce.ecm.api.rest.v1.request.category;

import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serial;
import java.util.List;
import oceanstars.ecommerce.common.restful.RestRequestMessage;

/**
 * 创建分类接口请求参数
 *
 * @author Clover
 * @version 1.0.0
 * @since 2024/1/15 14:30
 */
@Schema(name = "CreateCategoryRequestMessage", description = "创建分类接口请求参数")
public class CreateCategoryRequestMessage extends RestRequestMessage {

  @Serial
  private static final long serialVersionUID = 1576610997317113029L;

  /**
   * 分类名称
   */
  @Schema(description = "分类名称")
  private String name;

  /**
   * 分类展示名称
   */
  @Schema(description = "分类展示名称")
  private String displayName;

  /**
   * 分类描述
   */
  @Schema(description = "分类描述")
  private String description;

  /**
   * 分类链接
   */
  @Schema(description = "分类链接")
  private String url;

  /**
   * 父级分类ID
   */
  @Schema(description = "父级分类ID")
  private List<Long> parentIds;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDisplayName() {
    return displayName;
  }

  public void setDisplayName(String displayName) {
    this.displayName = displayName;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public List<Long> getParentIds() {
    return parentIds;
  }

  public void setParentIds(List<Long> parentIds) {
    this.parentIds = parentIds;
  }
}
