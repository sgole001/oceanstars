package oceanstars.ecommerce.user.api.rest.v1.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import oceanstars.ecommerce.common.exception.BaseException;
import oceanstars.ecommerce.common.restful.RestApi;
import oceanstars.ecommerce.common.restful.RestResponseMessage;
import oceanstars.ecommerce.user.api.rest.v1.request.account.AssignAccountRolesRequestMessage;
import oceanstars.ecommerce.user.api.rest.v1.request.account.CreateAccountProfileRequestMessage;
import oceanstars.ecommerce.user.api.rest.v1.request.account.CreateAccountRequestMessage;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * 账号外部API接口
 *
 * @author Clover
 * @version 1.0.0
 * @since 2024/5/28 14:37
 */
@Tag(name = "Role", description = "角色外部API接口")
public interface IAccountController extends RestApi {

  /**
   * 创建账号
   *
   * @param requestMessage 创建账号请求参数
   * @return 创建账号响应参数
   * @throws BaseException 处理异常信息抛出
   */
  @Operation(summary = "创建账号", description = "创建账号", tags = {"Account"})
  @PostMapping(value = "/accounts", produces = "application/vnd.api.v1.0.0+json")
  RestResponseMessage createAccount(@RequestBody CreateAccountRequestMessage requestMessage) throws BaseException;

  /**
   * 创建账号简况
   *
   * @param requestMessage 创建账号简况请求参数
   * @return 创建账号简况响应参数
   * @throws BaseException 处理异常信息抛出
   */
  @Operation(summary = "创建账号简况", description = "创建账号简况", tags = {"Account", "Profile"})
  @PostMapping(value = "/accounts/{account}/profiles", produces = "application/vnd.api.v1.0.0+json")
  RestResponseMessage createProfile(@PathVariable("account") Long account, @RequestBody CreateAccountProfileRequestMessage requestMessage)
      throws BaseException;

  /**
   * 分配账号角色
   *
   * @param requestMessage 分配账号角色请求参数
   * @return 分配账号角色响应参数
   * @throws BaseException 处理异常信息抛出
   */
  @Operation(summary = "分配账号角色", description = "分配账号角色", tags = {"Account", "Role"})
  @PostMapping(value = "/accounts/{account}/roles", produces = "application/vnd.api.v1.0.0+json")
  RestResponseMessage assignRoles(@PathVariable("account") Long account, @RequestBody AssignAccountRolesRequestMessage requestMessage)
      throws BaseException;
}
