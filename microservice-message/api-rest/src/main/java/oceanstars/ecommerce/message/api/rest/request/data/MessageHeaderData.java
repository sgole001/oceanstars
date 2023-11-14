package oceanstars.ecommerce.message.api.rest.request.data;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serial;
import java.io.Serializable;

/**
 * 消息头内容数据
 *
 * @author Clover
 * @version 1.0.0
 * @since 2021/12/17 10:36 上午
 */
@ApiModel(value = "消息发送内容数据")
public class MessageHeaderData implements Serializable {

  @Serial
  private static final long serialVersionUID = -2817348575701359441L;

  /**
   * 消息头主键
   */
  @ApiModelProperty(value = "消息头主键", required = true)
  private String key;

  /**
   * 消息头内容(JSON字符串)
   */
  @ApiModelProperty(value = "消息头内容（JSON字符串）", required = true)
  private String value;

  public String getKey() {
    return key;
  }

  public void setKey(String key) {
    this.key = key;
  }

  public String getValue() {
    return value;
  }

  public void setValue(String value) {
    this.value = value;
  }
}
