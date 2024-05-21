package oceanstars.ecommerce.user.api.rest.v1.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import oceanstars.ecommerce.common.exception.BaseException;
import oceanstars.ecommerce.common.restful.RestApi;
import oceanstars.ecommerce.common.restful.RestResponseMessage;
import oceanstars.ecommerce.user.api.rest.v1.request.permission.CreatePermissionRequestMessage;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * 权限外部API接口
 *
 * @author Clover
 * @version 1.0.0
 * @since 2022/1/14 10:36 AM
 */
@Tag(name = "Resource", description = "权限外部API接口")
public interface IPermissionController extends RestApi {

  /**
   * 创建权限
   *
   * @param requestMessage 创建权限请求参数
   * @return 创建权限响应参数
   * @throws BaseException 处理异常信息抛出
   */
  @Operation(summary = "创建权限", description = "创建权限", tags = {"Permission"})
  @PostMapping(value = "/permissions", produces = "application/vnd.api.v1.0.0+json")
  RestResponseMessage createPermission(@RequestBody CreatePermissionRequestMessage requestMessage) throws BaseException;
}
