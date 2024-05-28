package oceanstars.ecommerce.user.api.rest.v1.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import oceanstars.ecommerce.common.exception.BaseException;
import oceanstars.ecommerce.common.restful.RestApi;
import oceanstars.ecommerce.common.restful.RestResponseMessage;
import oceanstars.ecommerce.user.api.rest.v1.request.role.CreateRoleRequestMessage;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * 角色外部API接口
 *
 * @author Clover
 * @version 1.0.0
 * @since 2024/5/23 15:12
 */
@Tag(name = "Role", description = "角色外部API接口")
public interface IRoleController extends RestApi {

  /**
   * 创建角色
   *
   * @param requestMessage 创建角色请求参数
   * @return 创建角色响应参数
   * @throws BaseException 处理异常信息抛出
   */
  @Operation(summary = "创建角色", description = "创建角色", tags = {"Role"})
  @PostMapping(value = "/roles", produces = "application/vnd.api.v1.0.0+json")
  RestResponseMessage createRole(@RequestBody CreateRoleRequestMessage requestMessage) throws BaseException;
}
