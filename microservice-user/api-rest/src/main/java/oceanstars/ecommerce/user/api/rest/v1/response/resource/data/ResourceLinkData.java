package oceanstars.ecommerce.user.api.rest.v1.response.resource.data;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serial;
import java.io.Serializable;

/**
 * 权限关联资源数据获取链接数据
 *
 * @author Clover
 * @version 1.0.0
 * @since 2022/1/14 10:59 AM
 */
@ApiModel(value = "ResourceLinkData", description = "权限关联资源数据获取链接数据")
public class ResourceLinkData implements Serializable {

  @Serial
  private static final long serialVersionUID = 3369803914228886697L;

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
   * 资源数据PI的HTTP请求Body(JSON字符串)
   */
  @ApiModelProperty(value = "资源数据PI的HTTP请求Body(JSON字符串)")
  private String body;

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
