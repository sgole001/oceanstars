package oceanstars.ecommerce.message.api.rest.response.data;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serial;
import java.io.Serializable;

/**
 * 消息返回消息信息
 *
 * @author Clover
 * @version 1.0.0
 * @since 2021/11/25 5:12 下午
 */
@ApiModel(value = "基础消息数据模型")
public class MessageData implements Serializable {

  @Serial
  private static final long serialVersionUID = -1593411416737009824L;

  /**
   * 消息ID
   */
  @ApiModelProperty(value = "消息ID")
  private Long messageId;

  public Long getMessageId() {
    return messageId;
  }

  public void setMessageId(Long messageId) {
    this.messageId = messageId;
  }
}
