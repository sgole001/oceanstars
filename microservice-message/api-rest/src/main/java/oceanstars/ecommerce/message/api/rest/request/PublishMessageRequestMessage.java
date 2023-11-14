package oceanstars.ecommerce.message.api.rest.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serial;
import oceanstars.ecommerce.common.restful.RestRequestMessage;
import oceanstars.ecommerce.message.api.rest.request.data.MessageData;

/**
 * 接口请求参数：发布消息
 *
 * @author Clover
 * @version 1.0.0
 * @since 2021/11/25 5:05 下午
 */
@ApiModel(value = "接口请求参数：发布消息")
public class PublishMessageRequestMessage extends RestRequestMessage {

  @Serial
  private static final long serialVersionUID = 3715124944337345902L;

  /**
   * 发送消息数据
   */
  @ApiModelProperty(value = "发布消息数据", required = true)
  private MessageData messageData;

  public MessageData getMessageData() {
    return messageData;
  }

  public void setMessageData(MessageData messageData) {
    this.messageData = messageData;
  }
}
