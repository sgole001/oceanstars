package oceanstars.ecommerce.ecm.api.rest.v1.request.category;

import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serial;
import oceanstars.ecommerce.common.restful.RestRequestMessage;

/**
 * 获取分类接口请求参数
 *
 * @author Clover
 * @version 1.0.0
 * @since 2024/1/16 10:41
 */
@Schema(name = "GetCategoryRequestMessage", description = "获取分类接口请求参数")
public class GetCategoryRequestMessage extends RestRequestMessage {

  @Serial
  private static final long serialVersionUID = -2579762471230046741L;

  @Schema(description = "分类ID")
  private Long id;

  public GetCategoryRequestMessage(Long id) {
    this.id = id;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }
}
