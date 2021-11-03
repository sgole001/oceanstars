package oceanstars.ecommerce.common.session;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
 * Session信息缓存对象
 *
 * @author Clover
 * @version 1.0.0
 * @since 2021/11/3 5:06 下午
 */
public class Sessions implements Serializable {

  @Serial
  private static final long serialVersionUID = -7919412467377944059L;

  public static final String SESSION_KEY = "session";

  /**
   * 用户ID
   */
  private Long userId;

  /**
   * 餐厅ID
   */
  private Long restaurantId;

  /**
   * 桌号ID
   */
  private Long tableId;

  /**
   * 房号ID
   */
  private Long roomId;

  /**
   * 账号运营范围：区域
   */
  private List<Long> regions;

  /**
   * 账号运营范围：酒店
   */
  private List<Long> hotels;

  /**
   * 账号运营范围：餐厅
   */
  private List<Long> restaurants;

  /**
   * 账号权限范围
   */
  private List<Long> permissions;

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

  /**
   * 账号角色范围
   */
  private List<Long> roles;

  /**
   * 是否为admin
   */
  private Boolean adminFlag;

  /**
   * 多线程Session共享
   *
   * @param target 目标可运行逻辑
   */
  public void share(Runnable target) {

    try {
      // 将Session共享到当前线程
      SessionThreadLocal.setSessions(this);
      // 具体逻辑运行
      target.run();
    } finally {
      // 手动回收ThreadLocal，避免内存溢出
      SessionThreadLocal.removeSessions();
    }
  }

  public Long getUserId() {
    return userId;
  }

  public void setUserId(Long userId) {
    this.userId = userId;
  }

  public Long getRestaurantId() {
    return restaurantId;
  }

  public void setRestaurantId(Long restaurantId) {
    this.restaurantId = restaurantId;
  }

  public Long getTableId() {
    return tableId;
  }

  public void setTableId(Long tableId) {
    this.tableId = tableId;
  }

  public Long getRoomId() {
    return roomId;
  }

  public void setRoomId(Long roomId) {
    this.roomId = roomId;
  }

  public List<Long> getRegions() {
    return regions;
  }

  public void setRegions(List<Long> regions) {
    this.regions = regions;
  }

  public List<Long> getHotels() {
    return hotels;
  }

  public void setHotels(List<Long> hotels) {
    this.hotels = hotels;
  }

  public List<Long> getRestaurants() {
    return restaurants;
  }

  public void setRestaurants(List<Long> restaurants) {
    this.restaurants = restaurants;
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

  public List<Long> getPermissions() {
    return permissions;
  }

  public void setPermissions(List<Long> permissions) {
    this.permissions = permissions;
  }

  public Boolean getAdminFlag() {
    return adminFlag;
  }

  public void setAdminFlag(Boolean adminFlag) {
    this.adminFlag = adminFlag;
  }

  public List<Long> getRoles() {
    return roles;
  }

  public void setRoles(List<Long> roles) {
    this.roles = roles;
  }
}
