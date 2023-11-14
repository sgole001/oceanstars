package oceanstars.ecommerce.user.controller.v1.resource;

import jakarta.annotation.Resource;
import oceanstars.ecommerce.common.restful.RestApiController;
import oceanstars.ecommerce.common.restful.RestResponseMessage;
import oceanstars.ecommerce.user.api.rest.v1.controller.IResourceController;
import oceanstars.ecommerce.user.api.rest.v1.request.resource.CreateResourceTypeRequestMessage;
import oceanstars.ecommerce.user.controller.v1.resource.handler.CreateResourceTypeRestHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RestController;

/**
 * 权限资源外部API实现类
 *
 * @author Clover
 * @version 1.0.0
 * @since 2022/1/14 11:20 AM
 */
@RestController
public class ResourceController extends RestApiController implements IResourceController {

  @Resource
  private CreateResourceTypeRestHandler createResourceTypeRestHandler;

  /**
   * 日志管理器
   */
  private final Logger logger = LoggerFactory.getLogger(ResourceController.class);

  @Override
  public RestResponseMessage createResourceType(CreateResourceTypeRequestMessage requestMessage) throws Exception {

    return createResourceTypeRestHandler.handle(requestMessage);
  }
}
