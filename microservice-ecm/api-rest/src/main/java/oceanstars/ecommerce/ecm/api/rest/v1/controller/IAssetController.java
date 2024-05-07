package oceanstars.ecommerce.ecm.api.rest.v1.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import oceanstars.ecommerce.common.exception.BaseException;
import oceanstars.ecommerce.common.restful.RestApi;
import oceanstars.ecommerce.common.restful.RestResponseMessage;
import oceanstars.ecommerce.ecm.api.rest.v1.request.asset.ip.CreateFunctionRequestMessage;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * ECM资产管理外部API接口
 *
 * @author Clover
 * @version 1.0.0
 * @since 2024/4/17 17:15
 */
@Tag(name = "Asset", description = "ECM资产管理外部API接口")
public interface IAssetController extends RestApi {

//  @Operation(summary = "创建资产", description = "创建资产", tags = {"Asset"})
//  @PostMapping(value = "/assets", produces = "application/vnd.api.v1.0.0+json")
//  RestResponseMessage createAsset(@RequestBody CreateAssetRequestMessage requestMessage) throws BaseException;

  /**
   * 创建知识产权资产-功能列表
   *
   * @param requestMessage 创建知识产权资产-功能列表接口请求参数
   * @return 创建知识产权资产-功能列表响应参数
   * @throws BaseException 处理异常信息抛出
   */
  @Operation(summary = "创建知识产权资产-功能列表", description = "创建知识产权资产-功能列表", tags = {"Asset", "Intellectual", "Function"})
  @PostMapping(value = "/assets/ips/functions", produces = "application/vnd.api.v1.0.0+json")
  RestResponseMessage createIps4Function(@RequestBody CreateFunctionRequestMessage requestMessage) throws BaseException;
}
