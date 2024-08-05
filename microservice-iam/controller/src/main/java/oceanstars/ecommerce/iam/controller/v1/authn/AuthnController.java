package oceanstars.ecommerce.iam.controller.v1.authn;

import oceanstars.ecommerce.common.exception.BaseException;
import oceanstars.ecommerce.common.restful.RestApiController;
import oceanstars.ecommerce.common.restful.RestBus;
import oceanstars.ecommerce.common.restful.RestResponseMessage;
import oceanstars.ecommerce.iam.api.rest.v1.controller.IAuthnController;
import oceanstars.ecommerce.iam.api.rest.v1.request.authn.EnrollMfa4SmsRequestMessage;
import oceanstars.ecommerce.iam.api.rest.v1.request.authn.LoginRequestMessage;
import oceanstars.ecommerce.iam.api.rest.v1.request.authn.LogoutRequestMessage;
import oceanstars.ecommerce.iam.api.rest.v1.request.authn.SignupRequestMessage;
import oceanstars.ecommerce.iam.api.rest.v1.request.authn.VerifyRequestMessage;
import oceanstars.ecommerce.iam.constant.enums.IamEnums.IdentityAuthType;
import oceanstars.ecommerce.iam.constant.enums.IamEnums.IdentityDomain;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RestController;

/**
 * IAM认证外部API接口实现类
 *
 * @author Clover
 * @version 1.0.0
 * @since 2024/7/6 19:38
 */
@RestController
public class AuthnController extends RestApiController implements IAuthnController {

  /**
   * Restful API网关
   */
  private final RestBus restGateway;

  /**
   * 日志管理器
   */
  private final Logger logger = LoggerFactory.getLogger(AuthnController.class);

  /**
   * 构造函数：初始化Restful API网关
   *
   * @param restGateway Restful API网关
   */
  public AuthnController(RestBus restGateway) {
    this.restGateway = restGateway;
  }

  @Override
  public RestResponseMessage signupByEmail(SignupRequestMessage requestMessage, String domain) throws BaseException {
    // 检查身份域在指定范围内
    IdentityDomain.of(domain);
    // 设置身份域
    requestMessage.setDomain(domain);
    // 设置访问类型为邮箱
    requestMessage.setAccessType(IdentityAuthType.EMAIL.key());
    return restGateway.handle(requestMessage);
  }

  @Override
  public RestResponseMessage signupByMobile(SignupRequestMessage requestMessage, String domain) throws BaseException {
    // 检查身份域在指定范围内
    IdentityDomain.of(domain);
    // 设置身份域
    requestMessage.setDomain(domain);
    // 设置访问类型为手机号
    requestMessage.setAccessType(IdentityAuthType.MOBILE.key());
    return restGateway.handle(requestMessage);
  }

  @Override
  public RestResponseMessage login(LoginRequestMessage requestMessage, String domain) throws BaseException {
    // 检查身份域在指定范围内
    IdentityDomain.of(domain);
    // 设置身份域
    requestMessage.setDomain(domain);
    return restGateway.handle(requestMessage);
  }

  @Override
  public RestResponseMessage verify(VerifyRequestMessage requestMessage, String domain) throws BaseException {
    // 检查身份域在指定范围内
    IdentityDomain.of(domain);
    // 设置身份域
    requestMessage.setDomain(domain);
    return restGateway.handle(requestMessage);
  }

  @Override
  public RestResponseMessage logout(LogoutRequestMessage requestMessage, String domain) throws BaseException {
    // 检查身份域在指定范围内
    IdentityDomain.of(domain);
    // 设置身份域
    requestMessage.setDomain(domain);
    return restGateway.handle(requestMessage);
  }

  @Override
  public RestResponseMessage enrollMfa4Sms(EnrollMfa4SmsRequestMessage requestMessage, String domain) throws BaseException {
    // 检查身份域在指定范围内
    IdentityDomain.of(domain);
    // 设置身份域
    requestMessage.setDomain(domain);
    return restGateway.handle(requestMessage);
  }
}
