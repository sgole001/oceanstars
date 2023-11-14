package oceanstars.ecommerce.message.api.rest.response;

import io.swagger.annotations.ApiModel;
import java.io.Serial;
import oceanstars.ecommerce.common.restful.RestResponseMessage;

/**
 * 接口返回参数：拉取消息日志
 *
 * @author Clover
 * @version 1.0.0
 * @since 2021/11/25 5:12 下午
 */
@ApiModel(value = "接口返回参数：拉取消息日志")
public class FetchMessageLogsResponseMessage extends RestResponseMessage {

  @Serial
  private static final long serialVersionUID = 1296401172428204487L;

  public FetchMessageLogsResponseMessage(String httpStatus) {
    super(httpStatus);
  }
}
