package oceanstars.ecommerce.user.domain.resource.entity.valueobject;

import oceanstars.ecommerce.common.domain.ValueObject;

/**
 * 资源数据链接值对象
 *
 * @author Clover
 * @version 1.0.0
 * @since 2022/1/6 3:57 PM
 */
public class ResourceLinkValueObject extends ValueObject {

  /**
   * 资源数据API路径
   */
  private final String href;

  /**
   * 资源数据API的HTTP请求方法
   */
  private final String method;

  /**
   * 资源数据PI的HTTP请求Body(JSON字符串)
   */
  private final String body;

  /**
   * 构造函数：根据构建器初始化成员变量
   *
   * @param builder 构建器
   */
  private ResourceLinkValueObject(Builder builder) {
    href = builder.href;
    method = builder.method;
    body = builder.body;
  }

  /**
   * 创建资源数据链接构建器
   *
   * @return 资源数据链接构建器
   */
  public static Builder newBuilder() {
    return new Builder();
  }

  public String getHref() {
    return href;
  }

  public String getMethod() {
    return method;
  }

  public String getBody() {
    return body;
  }

  /**
   * 资源数据链接构建器
   *
   * @author Clover
   * @version 1.0.0
   * @since 2022/1/6 4:14 PM
   */
  public static final class Builder {

    private String href;
    private String method;
    private String body;

    public Builder href(String val) {
      href = val;
      return this;
    }

    public Builder method(String val) {
      method = val;
      return this;
    }

    public Builder body(String val) {
      body = val;
      return this;
    }

    public ResourceLinkValueObject build() {
      return new ResourceLinkValueObject(this);
    }
  }
}
