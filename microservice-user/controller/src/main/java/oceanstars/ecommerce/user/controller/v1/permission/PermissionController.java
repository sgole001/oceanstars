package oceanstars.ecommerce.user.controller.v1.permission;

import oceanstars.ecommerce.common.exception.BaseException;
import oceanstars.ecommerce.common.restful.RestApiController;
import oceanstars.ecommerce.common.restful.RestBus;
import oceanstars.ecommerce.common.restful.RestResponseMessage;
import oceanstars.ecommerce.user.api.rest.v1.controller.IPermissionController;
import oceanstars.ecommerce.user.api.rest.v1.request.permission.CreatePermissionRequestMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RestController;

/**
 * 权限外部API实现类
 *
 * @author Clover
 * @version 1.0.0
 * @since 2022/1/14 11:20 AM
 */
@RestController
public class PermissionController extends RestApiController implements IPermissionController {

  /**
   * Restful API网关
   */
  private final RestBus restGateway;

  /**
   * 日志管理器
   */
  private final Logger logger = LoggerFactory.getLogger(PermissionController.class);

  /**
   * 构造函数：初始化Restful API网关
   *
   * @param restGateway Restful API网关
   */
  public PermissionController(RestBus restGateway) {
    this.restGateway = restGateway;
  }

  @Override
  public RestResponseMessage createPermission(CreatePermissionRequestMessage requestMessage) throws BaseException {
    return restGateway.handle(requestMessage);
  }
}
