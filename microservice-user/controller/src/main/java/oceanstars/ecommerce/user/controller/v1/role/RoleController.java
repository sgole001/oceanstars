package oceanstars.ecommerce.user.controller.v1.role;

import oceanstars.ecommerce.common.exception.BaseException;
import oceanstars.ecommerce.common.restful.RestApiController;
import oceanstars.ecommerce.common.restful.RestBus;
import oceanstars.ecommerce.common.restful.RestResponseMessage;
import oceanstars.ecommerce.user.api.rest.v1.controller.IRoleController;
import oceanstars.ecommerce.user.api.rest.v1.request.role.CreateRoleRequestMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RestController;

/**
 * 角色资源外部API实现类
 *
 * @author Clover
 * @version 1.0.0
 * @since 2024/5/23 15:32
 */
@RestController
public class RoleController extends RestApiController implements IRoleController {

  /**
   * Restful API网关
   */
  private final RestBus restGateway;

  /**
   * 日志管理器
   */
  private final Logger logger = LoggerFactory.getLogger(RoleController.class);

  /**
   * 构造函数：初始化Restful API网关
   *
   * @param restGateway Restful API网关
   */
  public RoleController(RestBus restGateway) {
    this.restGateway = restGateway;
  }

  @Override
  public RestResponseMessage createRole(CreateRoleRequestMessage requestMessage) throws BaseException {
    return restGateway.handle(requestMessage);
  }

}
