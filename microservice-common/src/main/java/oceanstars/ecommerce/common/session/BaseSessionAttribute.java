package oceanstars.ecommerce.common.session;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

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
   * 会话发起对象
   */
  private String userId;

  /**
   * 会话发起时间
   */
  private LocalDateTime time;

  /**
   * 会话发起位置(ip信息)
   */
  private String location;

  /**
   * 会话发起设备
   */
  private String device;

  /**
   * 会话服务调用链ID
   */
  private String traceId;

  /**
   * 会话服务发起方
   */
  private String traceConsumer;

  /**
   * 会话服务接受方
   */
  private String traceProvider;

  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }

  public LocalDateTime getTime() {
    return time;
  }

  public void setTime(LocalDateTime time) {
    this.time = time;
  }

  public String getLocation() {
    return location;
  }

  public void setLocation(String location) {
    this.location = location;
  }

  public String getDevice() {
    return device;
  }

  public void setDevice(String device) {
    this.device = device;
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
