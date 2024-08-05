package oceanstars.ecommerce.iam.api.rest.v1.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import oceanstars.ecommerce.common.exception.BaseException;
import oceanstars.ecommerce.common.restful.RestApi;
import oceanstars.ecommerce.common.restful.RestResponseMessage;
import oceanstars.ecommerce.iam.api.rest.v1.request.authn.EnrollMfa4SmsRequestMessage;
import oceanstars.ecommerce.iam.api.rest.v1.request.authn.LoginRequestMessage;
import oceanstars.ecommerce.iam.api.rest.v1.request.authn.LogoutRequestMessage;
import oceanstars.ecommerce.iam.api.rest.v1.request.authn.SignupRequestMessage;
import oceanstars.ecommerce.iam.api.rest.v1.request.authn.VerifyRequestMessage;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * IAM外部API接口
 *
 * @author Clover
 * @version 1.0.0
 * @since 2024/7/4 19:19
 */
@Tag(name = "IAM", description = "IAM外部API接口")
@RequestMapping(value = "{domain}")
public interface IAuthnController extends RestApi {

  /**
   * 使用邮箱注册账号
   *
   * @param requestMessage 注册请求参数
   * @return 注册响应参数
   * @throws BaseException 处理异常信息抛出
   */
  @Operation(summary = "邮箱注册", description = "使用邮箱注册账号", tags = {"IAM", "Signup", "Email"})
  @PostMapping(value = "/email/signup", produces = "application/vnd.api.v1.0.0+json")
  RestResponseMessage signupByEmail(@RequestBody SignupRequestMessage requestMessage, @PathVariable("domain") String domain) throws BaseException;

  /**
   * 使用手机注册账号
   *
   * @param requestMessage 注册请求参数
   * @return 注册响应参数
   * @throws BaseException 处理异常信息抛出
   */
  @Operation(summary = "手机注册", description = "使用手机注册账号", tags = {"IAM", "Signup", "Mobile"})
  @PostMapping(value = "/mobile/signup", produces = "application/vnd.api.v1.0.0+json")
  RestResponseMessage signupByMobile(@RequestBody SignupRequestMessage requestMessage, @PathVariable("domain") String domain)
      throws BaseException;

  /**
   * 登录
   *
   * @param requestMessage 登录请求参数
   * @return 登录响应参数
   * @throws BaseException 处理异常信息抛出
   */
  @Operation(summary = "登录", description = "登录", tags = {"IAM", "Login"})
  @PostMapping(value = "/login", produces = "application/vnd.api.v1.0.0+json")
  RestResponseMessage login(@RequestBody LoginRequestMessage requestMessage, @PathVariable("domain") String domain) throws BaseException;

  /**
   * MFA验证
   *
   * @param requestMessage 验证请求参数
   * @return 验证响应参数
   * @throws BaseException 处理异常信息抛出
   */
  @Operation(summary = "MFA验证", description = "MFA验证", tags = {"IAM", "Verify", "MFA"})
  @PostMapping(value = "/verify", produces = "application/vnd.api.v1.0.0+json")
  RestResponseMessage verify(@RequestBody VerifyRequestMessage requestMessage, @PathVariable("domain") String domain) throws BaseException;

  /**
   * 登出
   *
   * @param requestMessage 登出请求参数
   * @return 登出响应参数
   * @throws BaseException 处理异常信息抛出
   */
  @Operation(summary = "登出", description = "登出", tags = {"IAM", "Logout"})
  @PostMapping(value = "/logout", produces = "application/vnd.api.v1.0.0+json")
  RestResponseMessage logout(@RequestBody LogoutRequestMessage requestMessage, @PathVariable("domain") String domain) throws BaseException;

  /**
   * MFA：短信验证码（SMS OTP）
   *
   * @param requestMessage MFA请求参数
   * @return MFA响应参数
   * @throws BaseException 处理异常信息抛出
   */
  @Operation(summary = "MFA：短信验证码", description = "MFA：短信验证码（SMS OTP）", tags = {"IAM", "Enroll", "MFA", "SMS"})
  @PostMapping(value = "/enroll/mfa/sms", produces = "application/vnd.api.v1.0.0+json")
  RestResponseMessage enrollMfa4Sms(@RequestBody EnrollMfa4SmsRequestMessage requestMessage, @PathVariable("domain") String domain)
      throws BaseException;
}
