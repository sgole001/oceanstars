package oceanstars.ecommerce.common.api;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serial;
import java.io.Serializable;

/**
 * RestApi响应消息基类
 *
 * @author Clover
 * @version 1.0.0
 * @since 2021/11/3 3:15 下午
 */
@ApiModel(value = "RestApi响应消息基类")
public class RestResponseMessage<T> implements Serializable {

  @Serial
  private static final long serialVersionUID = 7796796468759554810L;
  /**
   * Http响应返回状态
   */
  @ApiModelProperty(value = "Http响应返回状态")
  private String httpStatus;

  /**
   * 业务响应返回状态
   */
  @ApiModelProperty(value = "业务响应返回状态")
  private String status;

  /**
   * 业务响应返回额外信息
   */
  @ApiModelProperty(value = "业务响应返回额外信息")
  private String message;

  /**
   * 响应返回数据信息
   */
  @ApiModelProperty(value = "响应返回数据信息")
  private T data;

  public String getHttpStatus() {
    return httpStatus;
  }

  public void setHttpStatus(String httpStatus) {
    this.httpStatus = httpStatus;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public T getData() {
    return data;
  }

  public void setData(T data) {
    this.data = data;
  }
}
