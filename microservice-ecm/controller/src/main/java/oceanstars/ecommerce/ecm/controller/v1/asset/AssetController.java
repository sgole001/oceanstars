package oceanstars.ecommerce.ecm.controller.v1.asset;

import oceanstars.ecommerce.common.exception.BaseException;
import oceanstars.ecommerce.common.restful.RestApiController;
import oceanstars.ecommerce.common.restful.RestBus;
import oceanstars.ecommerce.common.restful.RestResponseMessage;
import oceanstars.ecommerce.ecm.api.rest.v1.controller.IAssetController;
import oceanstars.ecommerce.ecm.api.rest.v1.request.asset.ip.CreateFunctionRequestMessage;
import oceanstars.ecommerce.ecm.constant.enums.EcmEnums.AssetType;
import oceanstars.ecommerce.ecm.controller.v1.asset.restful.strategy.impl.AssetRequestMessageStrategyContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RestController;

/**
 * ECM资产管理外部API实现类
 *
 * @author Clover
 * @version 1.0.0
 * @since 2024/4/17 17:53
 */
@RestController
public class AssetController extends RestApiController implements IAssetController {

  /**
   * Restful API网关
   */
  private final RestBus restGateway;

  /**
   * 日志管理器
   */
  private final Logger logger = LoggerFactory.getLogger(AssetController.class);

  /**
   * 构造函数
   *
   * @param restGateway Restful API网关
   */
  public AssetController(RestBus restGateway) {
    this.restGateway = restGateway;
  }

  @Override
  public RestResponseMessage createIps4Function(CreateFunctionRequestMessage requestMessage) throws BaseException {
    return restGateway.handle(
        new AssetRequestMessageStrategyContext(AssetType.INTELLECTUAL_PROPERTY_FUNCTION).unifyCreateRequestMessage(requestMessage));
  }
}
