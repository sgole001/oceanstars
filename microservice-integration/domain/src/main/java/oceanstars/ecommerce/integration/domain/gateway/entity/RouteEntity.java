package oceanstars.ecommerce.integration.domain.gateway.entity;

import oceanstars.ecommerce.common.domain.Entity;

/**
 * 集成路由实体
 *
 * @author Clover
 * @version 1.0.0
 * @since 2021/12/30 12:24 下午
 */
public final class RouteEntity extends Entity<RouteIdentifier> {

  /**
   * 集成触发事件
   */
  private final String event;

  /**
   * 集成处理组
   */
  private final String group;

  /**
   * 集成路由服务
   */
  private final ServiceEntity service;

  /**
   * 集成路由名
   */
  private String name;

  /**
   * 集成路由描述
   */
  private String description;

  /**
   * 构造函数：根据构建器初始化成员变量
   *
   * @param builder 构建器
   */
  private RouteEntity(Builder builder) {
    super(new RouteIdentifier(builder.event, builder.group, builder.service.getEndpoint()));
    event = builder.event;
    group = builder.group;
    service = builder.service;
    name = builder.name;
    description = builder.description;
  }

  /**
   * 创建集成路由实体构建器
   *
   * @param event   集成触发事件
   * @param service 集成路由服务
   * @return 集成路由实体构建器
   */
  public static Builder newBuilder(final String event, final String group, final ServiceEntity service) {
    return new Builder(event, group, service);
  }

  public String getEvent() {
    return event;
  }

  public String getGroup() {
    return group;
  }

  public ServiceEntity getService() {
    return service;
  }

  public String getName() {
    return name;
  }

  public String getDescription() {
    return description;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  /**
   * 集成路由实体构建器
   *
   * @author Clover
   * @version 1.0.0
   * @since 2021/12/30 1:25 下午
   */
  public static final class Builder {

    private final String event;
    private final String group;
    private final ServiceEntity service;
    private String name;
    private String description;

    public Builder(String event, String group, ServiceEntity service) {
      this.event = event;
      this.group = group;
      this.service = service;
    }

    public Builder name(String val) {
      name = val;
      return this;
    }

    public Builder description(String val) {
      description = val;
      return this;
    }

    public RouteEntity build() {
      return new RouteEntity(this);
    }
  }
}
