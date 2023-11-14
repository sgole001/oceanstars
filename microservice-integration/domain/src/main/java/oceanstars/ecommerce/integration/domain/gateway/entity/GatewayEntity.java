package oceanstars.ecommerce.integration.domain.gateway.entity;

import java.time.LocalDateTime;
import java.util.Map;
import oceanstars.ecommerce.common.constant.CommonConstant;
import oceanstars.ecommerce.common.domain.AggregateRoot;
import oceanstars.ecommerce.common.domain.ValueObjectDelegator;
import oceanstars.ecommerce.common.tools.PkWorker;
import oceanstars.ecommerce.common.tools.SessionUtil;
import oceanstars.ecommerce.integration.constant.enums.IntegrationEnum.IntegrationSystem;
import oceanstars.ecommerce.integration.domain.gateway.entity.valueobject.BaseIntegrationRequestValueObject;
import oceanstars.ecommerce.integration.domain.gateway.entity.valueobject.BaseIntegrationResponseValueObject;

/**
 * 集成网关实体：聚合根
 *
 * @author Clover
 * @version 1.0.0
 * @since 2021/12/29 10:02 上午
 */
public final class GatewayEntity extends AggregateRoot<GatewayIdentifier> {

  /**
   * 集成目标系统(一个集成网关只集成一个外部系统)
   */
  private final IntegrationSystem system;

  /**
   * 集成路由映射
   */
  private final Map<String, RouteEntity> routes;

  /**
   * 集成网关描述
   */
  private String description;

  /**
   * 构造函数：根据构建器初始化成员变量
   *
   * @param builder 构建器
   */
  private GatewayEntity(Builder builder) {
    super(new GatewayIdentifier(builder.system));
    system = builder.system;
    description = builder.description;
    routes = builder.routes;
  }

  /**
   * 创建构建器
   *
   * @param system 集成目标系统
   * @return 构建器
   */
  public static Builder newBuilder(final IntegrationSystem system) {
    return new Builder(system);
  }

  /**
   * 集成处理
   *
   * @param request 集成请求信息
   * @return 集成响应信息
   */
  public BaseIntegrationResponseValueObject integrate(final BaseIntegrationRequestValueObject request) {

    // 创建集成代理对象处理集成事件对账
    final ValueObjectDelegator delegator = ValueObjectDelegator.newBuilder(PkWorker.generateWorkerId(),
        LocalDateTime.now(), SessionUtil.getSessions().getTraceId()).build();

    // 集成请求事件存储
    request.delegate(delegator);
    this.requestEventStore(request);

    // 构建集成路由KEY
    final String routeKey = request.getEvent() + CommonConstant.SEPARATOR_HYPHEN + request.getGroup();
    // 根根集成请求信息获取集成路由列表
    final RouteEntity route = this.routes.get(routeKey);
    // 获取集成服务
    final ServiceEntity service = route.getService();
    // 集成服务处理
    final BaseIntegrationResponseValueObject response = service.handle(request);

    // 集成响应时间存储
    response.delegate(delegator);
    this.responseEventStore(response);

    return response;
  }

  /**
   * 集成请求事件存储
   *
   * @param request 集成请求信息
   */
  private void requestEventStore(final BaseIntegrationRequestValueObject request) {
    // 集成请求事件存储至ElasticSearch中
  }

  /**
   * 集成响应时间存储
   *
   * @param response 集成响应信息
   */
  private void responseEventStore(final BaseIntegrationResponseValueObject response) {
    // 集成响应事件存储至ElasticSearch中
  }

  public IntegrationSystem getSystem() {
    return this.system;
  }

  public String getDescription() {
    return this.description;
  }

  public Map<String, RouteEntity> getRoutes() {
    return routes;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  /**
   * 集成网关实体构建器
   *
   * @author Clover
   * @version 1.0.0
   * @since 2021/12/29 3:05 下午
   */
  public static final class Builder {

    private final IntegrationSystem system;
    private String description;
    private Map<String, RouteEntity> routes;

    public Builder(IntegrationSystem system) {
      this.system = system;
    }

    public Builder description(String val) {
      description = val;
      return this;
    }

    public Builder routes(Map<String, RouteEntity> val) {
      routes = val;
      return this;
    }

    public GatewayEntity build() {
      return new GatewayEntity(this);
    }
  }
}
