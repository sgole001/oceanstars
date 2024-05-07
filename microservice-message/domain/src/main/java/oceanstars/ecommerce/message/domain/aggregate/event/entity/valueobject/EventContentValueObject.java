package oceanstars.ecommerce.message.domain.aggregate.event.entity.valueobject;

import java.util.Collections;
import java.util.Map;
import oceanstars.ecommerce.common.domain.entity.ValueObject;
import oceanstars.ecommerce.common.tools.SerializeUtil.Serialization;

/**
 * 事件内容类
 *
 * @author Clover
 * @version 1.0.0
 * @since 2021/11/30 11:08 下午
 */
public class EventContentValueObject<T> extends ValueObject implements Serialization {

  /**
   * 事件负载内容
   */
  private final T payload;

  /**
   * 事件头信息
   */
  private final Map<String, Object> headers;

  /**
   * 私有构造函数:使用构造器构造事件内容
   *
   * @param builder 事件内容构造器
   */
  protected EventContentValueObject(Builder<T> builder) {
    payload = builder.payload;
    headers = builder.headers;
  }

  /**
   * 创建事件内容构造器
   *
   * @param payload 事件负载内容
   * @param <T>     事件负载内容类型
   * @return 消息内容构造器
   */
  public static <T> Builder<T> newBuilder(T payload) {
    return new Builder<>(payload);
  }

  public T getPayload() {
    return payload;
  }

  public Map<String, Object> getHeaders() {
    return this.headers;
  }

  /**
   * 事件内容构建器
   *
   * @author Clover
   * @version 1.0.0
   * @since 2021/12/2 6:28 下午
   */
  public static class Builder<T> {

    protected final T payload;
    protected Map<String, Object> headers;

    public Builder(T payload) {
      this.payload = payload;
    }

    public Builder<T> headers(Map<String, Object> headers) {
      this.headers = headers;
      return this;
    }

    public EventContentValueObject<T> build() {
      if (null == this.headers) {
        this.headers = Collections.emptyMap();
      }
      return new EventContentValueObject<>(this);
    }
  }
}
