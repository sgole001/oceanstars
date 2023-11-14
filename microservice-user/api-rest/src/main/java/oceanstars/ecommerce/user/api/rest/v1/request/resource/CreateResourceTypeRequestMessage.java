package oceanstars.ecommerce.user.api.rest.v1.request.resource;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serial;
import oceanstars.ecommerce.common.restful.RestRequestMessage;

/**
 * 创建资源类型接口请求参数
 *
 * @author Clover
 * @version 1.0.0
 * @since 2022/1/14 10:48 AM
 */
@ApiModel(value = "CreateResourceTypeRequestMessage", description = "创建资源类型接口请求参数")
public class CreateResourceTypeRequestMessage extends RestRequestMessage {

  @Serial
  private static final long serialVersionUID = 3725281164528396432L;

  /**
   * 资源类型名
   */
  @ApiModelProperty(value = "资源类型名")
  private String name;

  /**
   * 资源类型功能说明
   */
  @ApiModelProperty(value = "资源类型功能说明")
  private String desc;

  /**
   * 资源数据API路径
   */
  @ApiModelProperty(value = "资源数据API路径")
  private String href;

  /**
   * 资源数据API的HTTP请求方法
   */
  @ApiModelProperty(value = "资源数据API的HTTP请求方法")
  private String method;

  /**
   * 资源数据API的HTTP请求Body(JSON字符串)
   */
  @ApiModelProperty(value = "资源数据API的HTTP请求Body(JSON字符串)")
  private String body;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDesc() {
    return desc;
  }

  public void setDesc(String desc) {
    this.desc = desc;
  }

  public String getHref() {
    return href;
  }

  public void setHref(String href) {
    this.href = href;
  }

  public String getMethod() {
    return method;
  }

  public void setMethod(String method) {
    this.method = method;
  }

  public String getBody() {
    return body;
  }

  public void setBody(String body) {
    this.body = body;
  }
}
