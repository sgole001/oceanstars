package oceanstars.ecommerce.ecm.api.rest.v1.request.category;

import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serial;
import oceanstars.ecommerce.common.restful.RestRequestMessage;

/**
 * 搜索分类接口请求参数
 *
 * @author Clover
 * @version 1.0.0
 * @since 2024/1/15 15:25
 */
@Schema(name = "SearchCategoryRequestMessage", description = "搜索分类接口请求参数")
public class SearchCategoryRequestMessage extends RestRequestMessage {

  @Serial
  private static final long serialVersionUID = -1354418572646215300L;
}
