package oceanstars.ecommerce.ecm.api.rest.v1.request.asset.ip;

import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serial;
import oceanstars.ecommerce.common.restful.RestRequestMessage;

/**
 * 创建知识产权资产-功能列表接口请求参数
 *
 * @author Clover
 * @version 1.0.0
 * @since 2024/4/23 13:46
 */
@Schema(name = "CreateFunctionRequestMessage", description = "创建知识产权资产-功能列表接口请求参数")
public class CreateFunctionRequestMessage extends RestRequestMessage {

  @Serial
  private static final long serialVersionUID = -3892911392968642177L;

  /**
   * 资产名称
   */
  @Schema(description = "功能名称")
  private String name;

  /**
   * 资产描述
   */
  @Schema(description = "功能描述")
  private String description;

  /**
   * 功能隶属 - 通过资产ID关联隶属关系
   */
  @Schema(description = "功能隶属 - 通过资产ID关联隶属关系")
  private Long parent;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public Long getParent() {
    return parent;
  }

  public void setParent(Long parent) {
    this.parent = parent;
  }
}
