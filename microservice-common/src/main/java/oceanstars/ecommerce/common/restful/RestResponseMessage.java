package oceanstars.ecommerce.common.restful;

import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serial;
import java.io.Serializable;
import org.springframework.http.HttpStatus;

/**
 * RestApi响应消息基类
 *
 * @author Clover
 * @version 1.0.0
 * @since 2021/11/3 3:15 下午
 */
@Schema(description = "RestApi响应消息基类")
public class RestResponseMessage implements Serializable {

  @Serial
  private static final long serialVersionUID = 7796796468759554810L;
  /**
   * Http响应返回状态
   */
  @Schema(description = "Http响应返回状态")
  private final HttpStatus httpStatus;

  /**
   * 业务响应返回状态
   */
  @Schema(description = "业务响应返回状态")
  private String status;

  /**
   * 业务响应返回额外信息
   */
  @Schema(description = "业务响应返回额外信息")
  private String message;

  /**
   * 响应返回数据信息
   */
  @Schema(description = "响应返回数据信息")
  private BaseRestResponseData data;

  /**
   * 构造函数：初始化Http状态
   *
   * @param builder 构建器
   */
  private RestResponseMessage(Builder builder) {
    httpStatus = builder.httpStatus;
    setStatus(builder.status);
    setMessage(builder.message);
    setData(builder.data);
  }

  /**
   * 构建器
   *
   * @param httpStatus Http状态
   * @return 构建器
   */
  public static Builder newBuilder(HttpStatus httpStatus) {
    return new Builder(httpStatus);
  }

  public HttpStatus getHttpStatus() {
    return httpStatus;
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

  public BaseRestResponseData getData() {
    return data;
  }

  public void setData(BaseRestResponseData data) {
    this.data = data;
  }

  /**
   * RestApi响应消息构建器
   *
   * @author Clover
   * @version 1.0.0
   * @since 2024/1/17 18:26
   */
  public static final class Builder {

    private final HttpStatus httpStatus;
    private String status;
    private String message;
    private BaseRestResponseData data;

    public Builder(HttpStatus httpStatus) {
      this.httpStatus = httpStatus;
    }

    public Builder status(String val) {
      status = val;
      return this;
    }

    public Builder message(String val) {
      message = val;
      return this;
    }

    public Builder data(BaseRestResponseData val) {
      data = val;
      return this;
    }

    public RestResponseMessage build() {
      return new RestResponseMessage(this);
    }
  }
}
