package oceanstars.ecommerce.ecm.controller.v1.content;

import jakarta.annotation.Resource;
import oceanstars.ecommerce.common.exception.BaseException;
import oceanstars.ecommerce.common.restful.RestApiController;
import oceanstars.ecommerce.common.restful.RestGateway;
import oceanstars.ecommerce.common.restful.RestResponseMessage;
import oceanstars.ecommerce.ecm.api.rest.v1.controller.IContentController;
import oceanstars.ecommerce.ecm.api.rest.v1.request.content.CreateContentCategoryRequestMessage;
import oceanstars.ecommerce.ecm.api.rest.v1.request.content.CreateContentRequestMessage;
import oceanstars.ecommerce.ecm.api.rest.v1.request.content.GetContentCategoryRequestMessage;
import oceanstars.ecommerce.ecm.api.rest.v1.request.content.SearchContentCategoryRequestMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RestController;

/**
 * ECM内容管理外部API实现类
 *
 * @author Clover
 * @version 1.0.0
 * @since 2024/1/15 15:47
 */
@RestController
public class ContentController extends RestApiController implements IContentController {

  /**
   * Restful API网关
   */
  @Resource
  private RestGateway restGateway;

  /**
   * 日志管理器
   */
  private final Logger logger = LoggerFactory.getLogger(ContentController.class);

  @Override
  public RestResponseMessage createContent(CreateContentRequestMessage requestMessage) throws BaseException {
    return restGateway.handle(requestMessage);
  }

  @Override
  public RestResponseMessage createContentCategory(CreateContentCategoryRequestMessage requestMessage) throws BaseException {
    return restGateway.handle(requestMessage);
  }

  @Override
  public RestResponseMessage searchContentCategory(SearchContentCategoryRequestMessage requestMessage) throws BaseException {
    return restGateway.handle(requestMessage);
  }

  @Override
  public RestResponseMessage getContentCategory(Long id) throws BaseException {
    return restGateway.handle(new GetContentCategoryRequestMessage(id));
  }
}
