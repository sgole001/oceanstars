package oceanstars.ecommerce.message.api.rest.request.data;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serial;
import java.io.Serializable;

/**
 * 消息源数据
 *
 * @author Clover
 * @version 1.0.0
 * @since 2021/12/17 10:43 上午
 */
@ApiModel(value = "消息源数据模型")
public class MessageSourceData implements Serializable {

  @Serial
  private static final long serialVersionUID = -4590085374573949128L;
  
  /**
   * 消息源类型：聚合对象类型
   */
  @ApiModelProperty(value = "消息源类型：聚合对象类型", required = true)
  private String type;

  /**
   * 消息源ID：聚合对象ID
   */
  @ApiModelProperty(value = "消息源ID：聚合对象ID", required = true)
  private String id;

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }
}
