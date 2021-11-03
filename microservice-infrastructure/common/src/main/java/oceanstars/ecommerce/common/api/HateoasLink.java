package oceanstars.ecommerce.common.api;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * RestAPI HATEOAS设计接口
 *
 * @author Clover
 * @version 1.0.0
 * @since 2021/11/3 3:12 下午
 */
@ApiModel(value = "HateoasLink", description = "RestAPI HATEOAS设计接口")
public class HateoasLink {

  /**
   * 参照API与当前资源的关系
   */
  @ApiModelProperty(value = "参照API与当前资源的关系")
  private String rel;

  /**
   * 参照API路径
   */
  @ApiModelProperty(value = "参照API路径")
  private String href;

  /**
   * 参照API的HTTP请求方法
   */
  @ApiModelProperty(value = "参照API的HTTP请求方法")
  private String method;

  /**
   * 参照API的HTTP请求Body(JSON字符串)
   */
  @ApiModelProperty(value = "参照API的HTTP请求Body(JSON字符串)")
  private String body;

  public String getRel() {
    return rel;
  }

  public void setRel(String rel) {
    this.rel = rel;
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
