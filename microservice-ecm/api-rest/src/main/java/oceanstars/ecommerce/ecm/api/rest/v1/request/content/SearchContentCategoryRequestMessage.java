package oceanstars.ecommerce.ecm.api.rest.v1.request.content;

import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serial;
import oceanstars.ecommerce.common.restful.RestRequestMessage;

/**
 * 搜索内容分类接口请求参数
 *
 * @author Clover
 * @version 1.0.0
 * @since 2024/1/15 15:25
 */
@Schema(name = "SearchContentCategoryRequestMessage", description = "搜索内容分类接口请求参数")
public class SearchContentCategoryRequestMessage extends RestRequestMessage {

  @Serial
  private static final long serialVersionUID = -1354418572646215300L;
}
