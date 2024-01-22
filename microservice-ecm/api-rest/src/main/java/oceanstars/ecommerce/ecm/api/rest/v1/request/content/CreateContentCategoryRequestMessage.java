package oceanstars.ecommerce.ecm.api.rest.v1.request.content;

import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serial;
import oceanstars.ecommerce.common.restful.RestRequestMessage;

/**
 * 创建内容分类接口请求参数
 *
 * @author Clover
 * @version 1.0.0
 * @since 2024/1/15 14:30
 */
@Schema(name = "CreateContentCategoryRequestMessage", description = "创建内容分类接口请求参数")
public class CreateContentCategoryRequestMessage extends RestRequestMessage {

  @Serial
  private static final long serialVersionUID = 1576610997317113029L;
}
