package oceanstars.ecommerce.user.controller.v1.account;

import oceanstars.ecommerce.common.exception.BaseException;
import oceanstars.ecommerce.common.restful.RestApiController;
import oceanstars.ecommerce.common.restful.RestBus;
import oceanstars.ecommerce.common.restful.RestResponseMessage;
import oceanstars.ecommerce.user.api.rest.v1.controller.IAccountController;
import oceanstars.ecommerce.user.api.rest.v1.request.account.AssignAccountRolesRequestMessage;
import oceanstars.ecommerce.user.api.rest.v1.request.account.CreateAccountProfileRequestMessage;
import oceanstars.ecommerce.user.api.rest.v1.request.account.CreateAccountRequestMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RestController;

/**
 * 账号外部API实现类
 *
 * @author Clover
 * @version 1.0.0
 * @since 2024/5/28 15:50
 */
@RestController
public class AccountController extends RestApiController implements IAccountController {

  /**
   * Restful API网关
   */
  private final RestBus restGateway;

  /**
   * 日志管理器
   */
  private final Logger logger = LoggerFactory.getLogger(AccountController.class);

  /**
   * 构造函数：初始化Restful API网关
   *
   * @param restGateway Restful API网关
   */
  public AccountController(RestBus restGateway) {
    this.restGateway = restGateway;
  }

  @Override
  public RestResponseMessage createAccount(CreateAccountRequestMessage requestMessage) throws BaseException {
    return restGateway.handle(requestMessage);
  }

  @Override
  public RestResponseMessage createProfile(Long account, CreateAccountProfileRequestMessage requestMessage) throws BaseException {
    requestMessage.setAccount(account);
    return restGateway.handle(requestMessage);
  }

  @Override
  public RestResponseMessage assignRoles(Long account, AssignAccountRolesRequestMessage requestMessage) throws BaseException {
    requestMessage.setAccount(account);
    return restGateway.handle(requestMessage);
  }
}
