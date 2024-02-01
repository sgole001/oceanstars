package oceanstars.ecommerce.ecm.api.rest.v1.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import oceanstars.ecommerce.common.exception.BaseException;
import oceanstars.ecommerce.common.restful.RestApi;
import oceanstars.ecommerce.common.restful.RestResponseMessage;
import oceanstars.ecommerce.ecm.api.rest.v1.request.content.CreateContentRequestMessage;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * ECM内容管理外部API接口
 *
 * @author Clover
 * @version 1.0.0
 * @since 2024/1/15 14:27
 */
@Tag(name = "Content", description = "ECM内容管理外部API接口")
public interface IContentController extends RestApi {

  /**
   * 创建内容
   *
   * @param requestMessage 创建内容请求参数
   * @return 创建内容响应参数
   * @throws BaseException 处理异常信息抛出
   */
  @Operation(summary = "创建内容", description = "创建内容", tags = {"Content"})
  @PostMapping(value = "/contents", produces = "application/vnd.api.v1.0.0+json")
  RestResponseMessage createContent(@RequestBody CreateContentRequestMessage requestMessage) throws BaseException;
}
