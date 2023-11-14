package oceanstars.ecommerce.integration.domain.gateway.repository;

import oceanstars.ecommerce.integration.constant.enums.IntegrationEnum.IntegrationSystem;
import oceanstars.ecommerce.integration.domain.gateway.entity.GatewayEntity;
import oceanstars.ecommerce.integration.domain.gateway.entity.GatewayIdentifier;
import oceanstars.ecommerce.integration.domain.gateway.entity.RouteEntity;
import oceanstars.ecommerce.integration.domain.gateway.entity.ServiceEntity;
import oceanstars.ecommerce.integration.domain.gateway.entity.valueobject.AuthenticationValueObject;

/**
 * 集成网关聚合仓储接口
 *
 * @author Clover
 * @version 1.0.0
 * @since 2022/1/4 11:10 AM
 */
public interface IGatewayRepository {

  /**
   * 根据集成网关唯一标识符查找集成网关数据
   *
   * @param identifier 集成网关唯一标识符
   * @return 集成网关实体数据
   */
  GatewayEntity findGatewayByIdentifier(final GatewayIdentifier identifier);

  /**
   * 根据集成目标系统查找集成网关数据
   *
   * @param system 集成目标系统
   * @return 集成网关实体数据
   */
  GatewayEntity findGatewayBySystem(final IntegrationSystem system);

  /**
   * 创建集成网关数据
   *
   * @param gatewayEntity 集成网关实体数据
   */
  void createGateway(final GatewayEntity gatewayEntity);

  /**
   * 创建集成路由数据
   *
   * @param routeEntity 集成路由实体数据
   */
  void createRoute(final RouteEntity routeEntity);

  /**
   * 创建集成服务数据
   *
   * @param serviceEntity 集成服务实体数据
   */
  void createService(final ServiceEntity serviceEntity);

  /**
   * 创建集成目标服务认证数据
   *
   * @param authenticationValueObject 集成目标服务认证值对象数据
   */
  void createAuthentication(final AuthenticationValueObject authenticationValueObject);
}
