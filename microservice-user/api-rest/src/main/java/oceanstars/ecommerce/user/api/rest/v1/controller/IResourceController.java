package oceanstars.ecommerce.user.api.rest.v1.controller;

import io.swagger.annotations.Api;
import oceanstars.ecommerce.common.restful.RestApi;
import oceanstars.ecommerce.common.restful.RestResponseMessage;
import oceanstars.ecommerce.user.api.rest.v1.request.resource.CreateResourceTypeRequestMessage;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * 权限资源外部API接口
 *
 * @author Clover
 * @version 1.0.0
 * @since 2022/1/14 10:36 AM
 */
@Api(tags = {"权限资源外部API接口"})
public interface IResourceController extends RestApi {

  /**
   * 创建资源类型
   *
   * @param requestMessage 创建资源类型请求参数
   * @return 创建资源类型响应参数
   * @throws Exception 处理异常信息抛出
   */
  @PostMapping(value = "/resources", produces = "application/vnd.api.v1.0.0+json")
  RestResponseMessage createResourceType(@RequestBody CreateResourceTypeRequestMessage requestMessage) throws Exception;
}
