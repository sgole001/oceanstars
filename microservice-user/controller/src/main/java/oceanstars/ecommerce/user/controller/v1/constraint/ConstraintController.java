package oceanstars.ecommerce.user.controller.v1.constraint;

import oceanstars.ecommerce.common.exception.BaseException;
import oceanstars.ecommerce.common.restful.RestApiController;
import oceanstars.ecommerce.common.restful.RestBus;
import oceanstars.ecommerce.common.restful.RestResponseMessage;
import oceanstars.ecommerce.user.api.rest.v1.controller.IConstraintController;
import oceanstars.ecommerce.user.api.rest.v1.request.constraint.CreateConstraintRequestMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RestController;

/**
 * 约束外部API实现类
 *
 * @author Clover
 * @version 1.0.0
 * @since 2024/7/1 19:08
 */
@RestController
public class ConstraintController extends RestApiController implements IConstraintController {

  /**
   * Restful API网关
   */
  private final RestBus restGateway;

  /**
   * 日志管理器
   */
  private final Logger logger = LoggerFactory.getLogger(ConstraintController.class);

  /**
   * 构造函数：初始化Restful API网关
   *
   * @param restGateway Restful API网关
   */
  public ConstraintController(RestBus restGateway) {
    this.restGateway = restGateway;
  }

  @Override
  public RestResponseMessage createConstraint(CreateConstraintRequestMessage requestMessage) throws BaseException {
    return restGateway.handle(requestMessage);
  }
}
