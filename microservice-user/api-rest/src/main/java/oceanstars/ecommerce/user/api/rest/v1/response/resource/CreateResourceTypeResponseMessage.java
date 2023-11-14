package oceanstars.ecommerce.user.api.rest.v1.response.resource;

import io.swagger.annotations.ApiModel;
import java.io.Serial;
import oceanstars.ecommerce.common.restful.RestResponseMessage;
import org.springframework.http.HttpStatus;

/**
 * 创建资源类型接口响应参数
 *
 * @author Clover
 * @version 1.0.0
 * @since 2022/1/14 10:46 AM
 */
@ApiModel(value = "CreateResourceTypeResponseMessage", description = "创建资源类型接口响应参数")
public class CreateResourceTypeResponseMessage extends RestResponseMessage {

  @Serial
  private static final long serialVersionUID = -2080365366669682153L;

  /**
   * 构造函数：初始化Http状态
   *
   * @param httpStatus Http状态
   */
  public CreateResourceTypeResponseMessage(final HttpStatus httpStatus) {
    super(httpStatus.toString());
  }
}
