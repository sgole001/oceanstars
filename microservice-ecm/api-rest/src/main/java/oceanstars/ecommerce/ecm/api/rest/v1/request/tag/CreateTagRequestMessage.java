package oceanstars.ecommerce.ecm.api.rest.v1.request.tag;

import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serial;
import oceanstars.ecommerce.common.restful.RestRequestMessage;

/**
 * 创建标签接口请求参数
 *
 * @author Clover
 * @version 1.0.0
 * @since 2024/1/25 12:24
 */
@Schema(name = "CreateTagRequestMessage", description = "创建标签接口请求参数")
public class CreateTagRequestMessage extends RestRequestMessage {

  @Serial
  private static final long serialVersionUID = 8277672773309836599L;

  /**
   * 标签名
   */
  @Schema(description = "标签名")
  private String name;

  /**
   * 标签展示名
   */
  @Schema(description = "标签展示名")
  private String displayName;

  /**
   * 标签描述
   */
  @Schema(description = "标签描述")
  private String description;

  /**
   * 标签链接
   */
  @Schema(description = "标签链接")
  private String url;

  /**
   * 标签图标(链接)
   */
  @Schema(description = "标签图标(链接)")
  private String icon;

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

  public String getIcon() {
    return icon;
  }

  public void setIcon(String icon) {
    this.icon = icon;
  }
}
