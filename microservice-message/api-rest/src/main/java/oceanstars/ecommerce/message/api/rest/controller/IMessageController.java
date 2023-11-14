package oceanstars.ecommerce.message.api.rest.controller;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import oceanstars.ecommerce.common.restful.RestApi;
import oceanstars.ecommerce.message.api.rest.request.FetchMessageLogsRequestMessage;
import oceanstars.ecommerce.message.api.rest.response.FetchMessageLogsResponseMessage;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * 消息总线外部API接口
 *
 * @author Clover
 * @version 1.0.0
 * @since 2021/11/25 5:15 下午
 */
@Api(tags = "消息总线API接口")
public interface IMessageController extends RestApi {

  /**
   * 拉取消息操作日志
   *
   * @param msgId          消息ID
   * @param requestMessage 拉取消息操作日志请求参数
   * @return 拉取消息操作日志响应参数
   */
  @ApiOperation(value = "拉取消息操作日志接口")
  @ApiImplicitParams(value = {@ApiImplicitParam(name = "msgId", value = "消息ID", required = true, paramType = "path")})
  @GetMapping(value = "/messages/{msgId}/logs", produces = "application/vnd.api.v1.0.0+json")
  FetchMessageLogsResponseMessage fetchMessageLogs(@PathVariable(value = "msgId") String msgId,
      @RequestBody FetchMessageLogsRequestMessage requestMessage);
}