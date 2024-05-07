package oceanstars.ecommerce.integration.domain.gateway.entity.valueobject;

import io.protostuff.Exclude;
import oceanstars.ecommerce.common.domain.entity.ValueObject;
import oceanstars.ecommerce.common.tools.JsonUtil;
import oceanstars.ecommerce.integration.domain.gateway.entity.ServiceEntity;

/**
 * 集成请求基础信息值对象
 *
 * @author Clover
 * @version 1.0.0
 * @since 2021/12/30 11:14 上午
 */
public abstract class BaseIntegrationRequestValueObject extends ValueObject {

  /**
   * 发起集成请求的触发事件
   */
  @Exclude
  private final String event;

  /**
   * 集成请求处理组
   */
  @Exclude
  private final String group;

  protected BaseIntegrationRequestValueObject(BaseBuilder builder) {
    this.event = builder.event;
    this.group = builder.group;
  }

  /**
   * 集成请求处理
   *
   * @param service 集成服务对象实体
   * @return 集成响应信息
   */
  public abstract BaseIntegrationResponseValueObject handle(final ServiceEntity service);

  public String getEvent() {
    return event;
  }

  public String getGroup() {
    return group;
  }

  /**
   * 集成请求基础信息值对象构建器
   *
   * @author Clover
   * @version 1.0.0
   * @since 2021/12/30 8:40 PM
   */
  public static class BaseBuilder {

    private final String event;

    private final String group;

    public BaseBuilder(String event, String group) {
      this.event = event;
      this.group = group;
    }
  }

  @Override
  public String toString() {
    return JsonUtil.toStringWithLocalDateTime(this);
  }
}
