package oceanstars.ecommerce.integration.domain.gateway.entity.valueobject;

import oceanstars.ecommerce.common.domain.ValueObject;

/**
 * 集成响应信息值对象
 *
 * @author Clover
 * @version 1.0.0
 * @since 2021/12/30 4:42 下午
 */
public class BaseIntegrationResponseValueObject extends ValueObject {

  /**
   * 业务响应返回状态
   */
  private final String status;

  /**
   * 业务响应返回额外信息
   */
  private final String message;

  /**
   * 响应返回数据信息
   */
  private final Object data;

  /**
   * 构造函数：根据构建器初始化成员变量
   *
   * @param builder 构建器
   */
  private BaseIntegrationResponseValueObject(Builder builder) {
    status = builder.status;
    message = builder.message;
    data = builder.data;
  }

  /**
   * 创建集成响应信息值对象构建器
   *
   * @param status 业务响应返回状态
   * @return 集成响应信息值对象构建器
   */
  public static Builder newBuilder(final String status) {
    return new Builder(status);
  }

  public String getStatus() {
    return status;
  }

  public String getMessage() {
    return message;
  }

  public Object getData() {
    return data;
  }

  /**
   * 集成响应信息值对象构建器
   *
   * @author Clover
   * @version 1.0.0
   * @since 2021/12/31 12:19 PM
   */
  public static final class Builder {

    private final String status;
    private String message;
    private Object data;

    public Builder(String status) {
      this.status = status;
    }

    public Builder message(String val) {
      message = val;
      return this;
    }

    public Builder data(Object val) {
      data = val;
      return this;
    }

    public BaseIntegrationResponseValueObject build() {
      return new BaseIntegrationResponseValueObject(this);
    }
  }
}
