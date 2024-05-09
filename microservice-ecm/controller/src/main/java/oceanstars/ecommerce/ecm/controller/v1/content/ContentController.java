package oceanstars.ecommerce.ecm.controller.v1.content;

import oceanstars.ecommerce.common.exception.BaseException;
import oceanstars.ecommerce.common.restful.RestApiController;
import oceanstars.ecommerce.common.restful.RestBus;
import oceanstars.ecommerce.common.restful.RestResponseMessage;
import oceanstars.ecommerce.ecm.api.rest.v1.controller.IContentController;
import oceanstars.ecommerce.ecm.api.rest.v1.request.content.CreateMenuRequestMessage;
import oceanstars.ecommerce.ecm.constant.enums.EcmEnums.ContentType;
import oceanstars.ecommerce.ecm.controller.v1.content.restful.strategy.impl.ContentRequestMessageStrategyContext;
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
  private final RestBus restGateway;

  /**
   * 日志管理器
   */
  private final Logger logger = LoggerFactory.getLogger(ContentController.class);

  /**
   * 构造函数
   *
   * @param restGateway Restful API网关
   */
  public ContentController(RestBus restGateway) {
    this.restGateway = restGateway;
  }

  @Override
  public RestResponseMessage createMenu(CreateMenuRequestMessage requestMessage) throws BaseException {
    return restGateway.handle(new ContentRequestMessageStrategyContext(ContentType.MENU).unifyCreateRequestMessage(requestMessage));
  }
}
