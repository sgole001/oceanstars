package oceanstars.ecommerce.ecm.controller.v1.content.handle;

import com.google.protobuf.GeneratedMessageV3;
import oceanstars.ecommerce.common.restful.BaseRestHandler;
import oceanstars.ecommerce.common.restful.RestResponseMessage;
import oceanstars.ecommerce.ecm.api.rest.v1.request.content.GetContentCategoryRequestMessage;
import org.springframework.stereotype.Component;

/**
 * Restful请求处理: 获取内容分类
 *
 * @author Clover
 * @version 1.0.0
 * @since 2024/1/15 16:21
 */
@Component
public class GetContentCategoryRestHandler extends BaseRestHandler<GetContentCategoryRequestMessage> {

  @Override
  public GeneratedMessageV3[] parsingRequestMessage(GetContentCategoryRequestMessage restRequestMessage) {
    return new GeneratedMessageV3[0];
  }

  @Override
  public GeneratedMessageV3[] process(GeneratedMessageV3[] messages) {
    return new GeneratedMessageV3[0];
  }

  @Override
  public RestResponseMessage packageResponseMessage(GeneratedMessageV3[] results) {
    return null;
  }
}
