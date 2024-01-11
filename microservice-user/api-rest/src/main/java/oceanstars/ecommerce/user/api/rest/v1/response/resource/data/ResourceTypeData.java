package oceanstars.ecommerce.user.api.rest.v1.response.resource.data;

import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serial;
import oceanstars.ecommerce.common.restful.BaseRestResponseData;

/**
 * <此类的功能说明>
 *
 * @author Clover
 * @version 1.0.0
 * @since 2022/1/14 10:56 AM
 */
@Schema(name = "ResourceTypeData", description = "权限资源类型数据")
public class ResourceTypeData extends BaseRestResponseData {

  @Serial
  private static final long serialVersionUID = 1931483208319731886L;

  /**
   * 资源类型编码
   */
  @Schema(description = "资源类型编码")
  private String code;

  /**
   * 资源类型名
   */
  @Schema(description = "资源类型名")
  private String name;

  /**
   * 资源类型功能说明
   */
  @Schema(description = "资源类型功能说明")
  private String desc;

  /**
   * 操作逻辑有效标志位
   */
  @Schema(description = "操作逻辑有效标志位")
  private Boolean enabled;

  /**
   * 资源数据链接
   */
  @Schema(description = "资源数据链接")
  private ResourceLinkData link;

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDesc() {
    return desc;
  }

  public void setDesc(String desc) {
    this.desc = desc;
  }

  public Boolean getEnabled() {
    return enabled;
  }

  public void setEnabled(Boolean enabled) {
    this.enabled = enabled;
  }

  public ResourceLinkData getLink() {
    return link;
  }

  public void setLink(ResourceLinkData link) {
    this.link = link;
  }
}
