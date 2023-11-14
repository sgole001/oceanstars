package oceanstars.ecommerce.message.api.rest.request.data;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
 * 消息发送内容数据
 *
 * @author Clover
 * @version 1.0.0
 * @since 2021/12/17 10:31 上午
 */
@ApiModel(value = "消息发送内容数据模型")
public class MessageContentData implements Serializable {

  @Serial
  private static final long serialVersionUID = 8649081420684854181L;
  /**
   * 消息负载内容（JSON字符串）
   */
  @ApiModelProperty(value = "消息负载（JSON字符串）", required = true)
  private String payload;

  /**
   * 消息头信息
   */
  @ApiModelProperty(value = "消息头部信息")
  private List<MessageHeaderData> headers;

  public String getPayload() {
    return payload;
  }

  public void setPayload(String payload) {
    this.payload = payload;
  }

  public List<MessageHeaderData> getHeaders() {
    return headers;
  }

  public void setHeaders(List<MessageHeaderData> headers) {
    this.headers = headers;
  }
}
