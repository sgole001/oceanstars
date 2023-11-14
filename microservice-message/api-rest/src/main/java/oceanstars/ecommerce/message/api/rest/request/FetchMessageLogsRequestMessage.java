package oceanstars.ecommerce.message.api.rest.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serial;
import oceanstars.ecommerce.common.restful.RestRequestMessage;

/**
 * 接口请求参数：拉取消息日志
 *
 * @author Clover
 * @version 1.0.0
 * @since 2021/11/25 5:04 下午
 */
@ApiModel(value = "接口请求参数：拉取消息日志")
public class FetchMessageLogsRequestMessage extends RestRequestMessage {

  @Serial
  private static final long serialVersionUID = 1241862882985564238L;
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
