package oceanstars.ecommerce.ecm.controller.v1.content.handle;

import com.google.protobuf.GeneratedMessageV3;
import jakarta.annotation.Resource;
import oceanstars.ecommerce.common.cqrs.CqrsGateway;
import oceanstars.ecommerce.common.restful.BaseRestHandler;
import oceanstars.ecommerce.common.restful.RestResponseMessage;
import oceanstars.ecommerce.ecm.api.rest.v1.request.content.CreateContentCategoryRequestMessage;
import org.springframework.stereotype.Component;

/**
 * Restful请求处理: 创建内容分类
 *
 * @author Clover
 * @version 1.0.0
 * @since 2024/1/15 15:54
 */
@Component
public class CreateContentCategoryRestHandler extends BaseRestHandler<CreateContentCategoryRequestMessage> {

  /**
   * CQRS处理总线
   */
  @Resource(name = "cqrsGateway")
  private CqrsGateway cqrsGateway;

  @Override
  public GeneratedMessageV3[] parsingRequestMessage(CreateContentCategoryRequestMessage restRequestMessage) {

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
