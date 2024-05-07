package oceanstars.ecommerce.ecm.controller.v1.tag;

import oceanstars.ecommerce.common.exception.BaseException;
import oceanstars.ecommerce.common.restful.RestBus;
import oceanstars.ecommerce.common.restful.RestResponseMessage;
import oceanstars.ecommerce.ecm.api.rest.v1.controller.ITagController;
import oceanstars.ecommerce.ecm.api.rest.v1.request.tag.CreateTagRequestMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RestController;

/**
 * ECM标签管理外部API实现类
 *
 * @author Clover
 * @version 1.0.0
 * @since 2024/1/25 12:39
 */
@RestController
public class TagController implements ITagController {

  /**
   * Restful API网关
   */
  private final RestBus restGateway;

  /**
   * 日志管理器
   */
  private final Logger logger = LoggerFactory.getLogger(TagController.class);

  /**
   * 构造函数
   *
   * @param restGateway Restful API网关
   */
  public TagController(RestBus restGateway) {
    this.restGateway = restGateway;
  }

  @Override
  public RestResponseMessage createTag(CreateTagRequestMessage requestMessage) throws BaseException {
    return restGateway.handle(requestMessage);
  }
}
