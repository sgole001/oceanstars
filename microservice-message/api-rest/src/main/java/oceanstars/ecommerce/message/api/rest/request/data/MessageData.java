package oceanstars.ecommerce.message.api.rest.request.data;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 消息发送请求数据
 *
 * @author Clover
 * @version 1.0.0
 * @since 2021/11/25 5:04 下午
 */
@ApiModel(value = "消息数据模型")
public class MessageData implements Serializable {

  @Serial
  private static final long serialVersionUID = 5297093369136956134L;

  /**
   * 消息类型
   */
  @ApiModelProperty(value = "消息类型", required = true)
  private String type;

  /**
   * 消息内容
   */
  @ApiModelProperty(value = "消息内容", required = true)
  private MessageContentData content;

  /**
   * 消息源信息
   */
  @ApiModelProperty(value = "消息源信息", required = true)
  private MessageSourceData source;

  /**
   * 消息发生时间
   */
  @ApiModelProperty(value = "消息发生时间", required = true)
  private LocalDateTime occurredOn;

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public MessageContentData getContent() {
    return content;
  }

  public void setContent(MessageContentData content) {
    this.content = content;
  }

  public MessageSourceData getSource() {
    return source;
  }

  public void setSource(MessageSourceData source) {
    this.source = source;
  }

  public LocalDateTime getOccurredOn() {
    return occurredOn;
  }

  public void setOccurredOn(LocalDateTime occurredOn) {
    this.occurredOn = occurredOn;
  }
}
