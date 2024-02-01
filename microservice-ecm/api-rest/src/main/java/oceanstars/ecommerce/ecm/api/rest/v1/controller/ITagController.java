package oceanstars.ecommerce.ecm.api.rest.v1.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import oceanstars.ecommerce.common.exception.BaseException;
import oceanstars.ecommerce.common.restful.RestApi;
import oceanstars.ecommerce.common.restful.RestResponseMessage;
import oceanstars.ecommerce.ecm.api.rest.v1.request.tag.CreateTagRequestMessage;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * ECM标签管理外部API接口
 *
 * @author Clover
 * @version 1.0.0
 * @since 2024/1/25 12:22
 */
@Tag(name = "Tag", description = "ECM标签管理外部API接口")
public interface ITagController extends RestApi {

  /**
   * 创建标签
   *
   * @param requestMessage 创建标签请求参数
   * @return 创建标签响应参数
   * @throws BaseException 处理异常信息抛出
   */
  @Operation(summary = "创建标签", description = "创建标签", tags = {"Tag"})
  @PostMapping(value = "/tags", produces = "application/vnd.api.v1.0.0+json")
  RestResponseMessage createTag(@RequestBody CreateTagRequestMessage requestMessage) throws BaseException;
}
