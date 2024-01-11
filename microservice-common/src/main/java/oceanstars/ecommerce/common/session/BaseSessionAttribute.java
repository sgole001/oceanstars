package oceanstars.ecommerce.common.session;

import java.io.Serial;
import java.io.Serializable;

/**
 * Session属性对象
 *
 * @author Clover
 * @version 1.0.0
 * @since 2023/11/22 14:26
 */
public class BaseSessionAttribute implements Serializable {

  @Serial
  private static final long serialVersionUID = 850903871456246616L;

  /**
   * 用户ID
   */
  private String userId;

  /**
   * 会话调用链ID
   */
  private String traceId;

  /**
   * 会话发起方
   */
  private String traceConsumer;

  /**
   * 会话接受方
   */
  private String traceProvider;

  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }

  public String getTraceId() {
    return traceId;
  }

  public void setTraceId(String traceId) {
    this.traceId = traceId;
  }

  public String getTraceConsumer() {
    return traceConsumer;
  }

  public void setTraceConsumer(String traceConsumer) {
    this.traceConsumer = traceConsumer;
  }

  public String getTraceProvider() {
    return traceProvider;
  }

  public void setTraceProvider(String traceProvider) {
    this.traceProvider = traceProvider;
  }
}
