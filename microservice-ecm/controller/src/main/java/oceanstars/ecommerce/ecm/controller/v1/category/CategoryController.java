package oceanstars.ecommerce.ecm.controller.v1.category;

import oceanstars.ecommerce.common.exception.BaseException;
import oceanstars.ecommerce.common.restful.RestApiController;
import oceanstars.ecommerce.common.restful.RestBus;
import oceanstars.ecommerce.common.restful.RestResponseMessage;
import oceanstars.ecommerce.ecm.api.rest.v1.controller.ICategoryController;
import oceanstars.ecommerce.ecm.api.rest.v1.request.category.CreateCategoryRequestMessage;
import oceanstars.ecommerce.ecm.api.rest.v1.request.category.GetCategoryRequestMessage;
import oceanstars.ecommerce.ecm.api.rest.v1.request.category.SearchCategoryRequestMessage;
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
public class CategoryController extends RestApiController implements ICategoryController {

  /**
   * Restful API网关
   */
  private final RestBus restGateway;

  /**
   * 日志管理器
   */
  private final Logger logger = LoggerFactory.getLogger(CategoryController.class);

  /**
   * 构造函数
   *
   * @param restGateway Restful API网关
   */
  public CategoryController(RestBus restGateway) {
    this.restGateway = restGateway;
  }

  @Override
  public RestResponseMessage createCategory(CreateCategoryRequestMessage requestMessage) throws BaseException {
    return restGateway.handle(requestMessage);
  }

  @Override
  public RestResponseMessage searchCategory(SearchCategoryRequestMessage requestMessage) throws BaseException {
    return restGateway.handle(requestMessage);
  }

  @Override
  public RestResponseMessage getCategory(Long id) throws BaseException {
    return restGateway.handle(new GetCategoryRequestMessage(id));
  }
}
