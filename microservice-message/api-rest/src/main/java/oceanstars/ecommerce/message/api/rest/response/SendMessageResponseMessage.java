package oceanstars.ecommerce.message.api.rest.response;

import io.swagger.annotations.ApiModel;
import java.io.Serial;
import oceanstars.ecommerce.common.restful.RestResponseMessage;

/**
 * 接口返回参数：发送信息
 *
 * @author Clover
 * @version 1.0.0
 * @since 2021/11/25 5:13 下午
 */
@ApiModel(value = "接口返回参数：发送信息")
public class SendMessageResponseMessage extends RestResponseMessage {

  @Serial
  private static final long serialVersionUID = 641910279013939007L;

  public SendMessageResponseMessage(String httpStatus) {
    super(httpStatus);
  }
}
