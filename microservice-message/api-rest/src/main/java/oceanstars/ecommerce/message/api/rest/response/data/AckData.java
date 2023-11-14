package oceanstars.ecommerce.message.api.rest.response.data;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serial;
import java.io.Serializable;

/**
 * 消息返回通知信息
 *
 * @author Clover
 * @version 1.0.0
 * @since 2021/11/25 5:12 下午
 */
@ApiModel(value = "消息返回通知信息")
public class AckData implements Serializable {

  @Serial
  private static final long serialVersionUID = -6516819760469709798L;
  /**
   * 消息ID
   */
  @ApiModelProperty(value = "消息ID")
  private Long messageId;

  /**
   * 消息状态FLAG：是否已发送
   */
  @ApiModelProperty(value = "消息状态FLAG：是否已发送")
  private Boolean isSent;

  /**
   * 消息状态FLAG：是否已消费
   */
  @ApiModelProperty(value = "消息状态FLAG：是否已消费")
  private Boolean isConsumed;

  /**
   * 重试次数
   */
  @ApiModelProperty(value = "重试次数")
  private Integer retries;

  public Long getMessageId() {
    return messageId;
  }

  public void setMessageId(Long messageId) {
    this.messageId = messageId;
  }

  public Boolean getSent() {
    return isSent;
  }

  public void setSent(Boolean sent) {
    isSent = sent;
  }

  public Boolean getConsumed() {
    return isConsumed;
  }

  public void setConsumed(Boolean consumed) {
    isConsumed = consumed;
  }

  public Integer getRetries() {
    return retries;
  }

  public void setRetries(Integer retries) {
    this.retries = retries;
  }
}
