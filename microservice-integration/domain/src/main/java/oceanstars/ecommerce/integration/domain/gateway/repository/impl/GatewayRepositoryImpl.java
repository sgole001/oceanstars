package oceanstars.ecommerce.integration.domain.gateway.repository.impl;

import io.protostuff.Service;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import jakarta.annotation.Resource;
import oceanstars.ecommerce.common.constant.CommonConstant;
import oceanstars.ecommerce.common.exception.BusinessException;
import oceanstars.ecommerce.integration.constant.enums.IntegrationEnum.IntegrationSystem;
import oceanstars.ecommerce.integration.constant.enums.IntegrationEnum.IntegrationType;
import oceanstars.ecommerce.integration.domain.gateway.entity.GatewayEntity;
import oceanstars.ecommerce.integration.domain.gateway.entity.GatewayIdentifier;
import oceanstars.ecommerce.integration.domain.gateway.entity.RouteEntity;
import oceanstars.ecommerce.integration.domain.gateway.entity.ServiceEntity;
import oceanstars.ecommerce.integration.domain.gateway.entity.valueobject.AuthenticationValueObject;
import oceanstars.ecommerce.integration.domain.gateway.repository.IGatewayRepository;
import oceanstars.ecommerce.integration.repository.generate.tables.daos.IntegrationAuthenticationDao;
import oceanstars.ecommerce.integration.repository.generate.tables.daos.IntegrationGatewayDao;
import oceanstars.ecommerce.integration.repository.generate.tables.daos.IntegrationRouteDao;
import oceanstars.ecommerce.integration.repository.generate.tables.daos.IntegrationServiceDao;
import oceanstars.ecommerce.integration.repository.generate.tables.pojos.IntegrationAuthenticationPojo;
import oceanstars.ecommerce.integration.repository.generate.tables.pojos.IntegrationGatewayPojo;
import oceanstars.ecommerce.integration.repository.generate.tables.pojos.IntegrationRoutePojo;
import oceanstars.ecommerce.integration.repository.generate.tables.pojos.IntegrationServicePojo;
import org.springframework.util.CollectionUtils;

/**
 * 集成网关聚合仓储实现类
 *
 * @author Clover
 * @version 1.0.0
 * @since 2022/1/4 4:22 PM
 */
@Service(value = "gatewayRepository")
public class GatewayRepositoryImpl implements IGatewayRepository {

  /**
   * 集成网关的数据访问对象
   */
  @Resource
  private IntegrationGatewayDao integrationGatewayDao;

  /**
   * 集成路由的数据访问对象
   */
  @Resource
  private IntegrationRouteDao integrationRouteDao;

  /**
   * 集成目标服务的数据访问对象
   */
  @Resource
  private IntegrationServiceDao integrationServiceDao;

  /**
   * 集成服务认证的数据访问对象
   */
  @Resource
  private IntegrationAuthenticationDao integrationAuthenticationDao;

  @Override
  public GatewayEntity findGatewayByIdentifier(final GatewayIdentifier identifier) {

    // 根据集成网关实体唯一标识符获取集成网关数据
    final IntegrationGatewayPojo gatewayPojo = this.integrationGatewayDao.fetchOneByCode(identifier.toString());

    if (null == gatewayPojo) {
      throw new BusinessException("集成网关编码为{0}的集成网关数据不存在！", identifier.toString());
    }

    return this.toGatewayEntity(gatewayPojo);
  }

  @Override
  public GatewayEntity findGatewayBySystem(final IntegrationSystem system) {

    // 根据集成目标系统获取集成网关数据
    final IntegrationGatewayPojo gatewayPojo = this.integrationGatewayDao.fetchOneBySystem(system.getCode().shortValue());

    if (null == gatewayPojo) {
      throw new BusinessException("集成目标系统为{0}的集成网关数据不存在！", system.getName());
    }

    return this.toGatewayEntity(gatewayPojo);
  }

  @Override
  public void createGateway(final GatewayEntity gatewayEntity) {

    // 获取集成网关唯一识别符
    final String identifier = gatewayEntity.getIdentifier().toString();
    // 根据集成网关唯一识别符获取集成网关数据
    IntegrationGatewayPojo gatewayPojo = this.integrationGatewayDao.fetchOneByCode(identifier);

    if (null != gatewayPojo) {
      throw new BusinessException("集成网关编码为{0}的集成网关数据已经存在", identifier);
    }

    // 根据集成网关数据实体构建集成网关数据数据库映射
    gatewayPojo = this.fromGatewayEntity(gatewayEntity);

    // 插入数据库
    this.integrationGatewayDao.insert(gatewayPojo);
  }

  @Override
  public void createRoute(final RouteEntity routeEntity) {

    // 获取集成路由唯一识别符
    final String identifier = routeEntity.getIdentifier().toString();
    // 根据集成网关唯一识别符获取集成网关数据
    IntegrationRoutePojo routePojo = this.integrationRouteDao.fetchOneByCode(identifier);

    if (null != routePojo) {
      throw new BusinessException("集成路由编码为{0}的集成路由数据已经存在", identifier);
    }

    // 根据集成路由数据实体构建集成路由数据数据库映射
    routePojo = this.fromRouteEntity(routeEntity);

    // 插入数据库
    this.integrationRouteDao.insert(routePojo);
  }

  @Override
  public void createService(final ServiceEntity serviceEntity) {

    // 获取集成目标服务唯一识别符
    final String identifier = serviceEntity.getIdentifier().toString();
    // 根据集成目标服务唯一识别符获取集成目标服务数据
    IntegrationServicePojo servicePojo = this.integrationServiceDao.fetchOneByCode(identifier);

    if (null != servicePojo) {
      throw new BusinessException("集成目标服务编码为{0}的集成目标服务数据已经存在", identifier);
    }

    // 根据集成目标服务数据实体构建集成目标服务数据数据库映射
    servicePojo = this.fromServiceEntity(serviceEntity);

    // 插入数据库
    this.integrationServiceDao.insert(servicePojo);
  }

  @Override
  public void createAuthentication(final AuthenticationValueObject authenticationValueObject) {

    // 根据集成服务认证数据值对象构建集成服务认证数据数据库映射
    final IntegrationAuthenticationPojo authenticationPojo = this.fromAuthenticationValueObject(authenticationValueObject);

    // 插入数据库
    this.integrationAuthenticationDao.insert(authenticationPojo);
  }

  /**
   * 根据集成网关实体构建集成网关数据库映射
   *
   * @param gatewayEntity 集成网关实体数据
   * @return 集成网关数据库映射
   */
  private IntegrationGatewayPojo fromGatewayEntity(final GatewayEntity gatewayEntity) {

    // 初始化集成网关数据库映射对象
    final IntegrationGatewayPojo integrationGatewayPojo = new IntegrationGatewayPojo();
    // 集成网关编码
    integrationGatewayPojo.setCode(gatewayEntity.getIdentifier().toString());
    // 集成目标系统
    integrationGatewayPojo.setSystem(gatewayEntity.getSystem().getCode().shortValue());
    // 集成网关描述
    integrationGatewayPojo.setDescription(gatewayEntity.getDescription());

    return integrationGatewayPojo;
  }

  /**
   * 根据集成网关数据库映射构建集成网关实体
   *
   * @param pojo 集成网关数据库映射
   * @return 集成网关实体
   */
  private GatewayEntity toGatewayEntity(final IntegrationGatewayPojo pojo) {

    // 根据集成网关ID获取路由数据
    final List<IntegrationRoutePojo> routes = this.integrationRouteDao.fetchByGateway(pojo.getId());

    if (CollectionUtils.isEmpty(routes)) {
      throw new BusinessException("集成目标系统为{0}的路由数据不存在！", IntegrationSystem.of(pojo.getSystem().intValue()).getName());
    }

    // 构建集成路由映射表
    final Map<String, RouteEntity> routeEntityMap = routes.stream()
        .collect(Collectors.toMap(route -> route.getEvent() + CommonConstant.SEPARATOR_HYPHEN + route.getGroup(), this::toRouteEntity));

    // 构建集成网关实体
    final GatewayEntity gatewayEntity = GatewayEntity.newBuilder(IntegrationSystem.of(pojo.getSystem().intValue()))
        // 集成网关描述
        .description(pojo.getDescription())
        // 集成路由
        .routes(routeEntityMap)
        // 执行构建
        .build();

    // 委托集成网关实体
    gatewayEntity.delegate(pojo);

    return gatewayEntity;
  }

  /**
   * 根据集成路由实体构建集成路由数据库映射
   *
   * @param routeEntity 集成路由实体数据
   * @return 集成路由数据库映射
   */
  private IntegrationRoutePojo fromRouteEntity(final RouteEntity routeEntity) {

    // 初始化集成路由数据库映射对象
    final IntegrationRoutePojo integrationRoutePojo = new IntegrationRoutePojo();
    // 集成路由编码
    integrationRoutePojo.setCode(routeEntity.getIdentifier().toString());
    // 集成触发事件
    integrationRoutePojo.setEvent(routeEntity.getEvent());
    // 集成处理组
    integrationRoutePojo.setGroup(routeEntity.getGroup());
    // 集成路由服务ID
    integrationRoutePojo.setService(routeEntity.getService().getDelegator().getId());
    // 集成网关ID
    integrationRoutePojo.setGateway(null);
    // 集成路由名
    integrationRoutePojo.setName(routeEntity.getName());
    // 集成路由描述
    integrationRoutePojo.setDescription(routeEntity.getDescription());

    return integrationRoutePojo;
  }

  /**
   * 根据集成路由数据库映射构建集成路由实体
   *
   * @param pojo 集成路由数据库映射
   * @return 集成路由实体
   */
  private RouteEntity toRouteEntity(final IntegrationRoutePojo pojo) {

    // 根据集成服务ID获取集成目标服务数据
    final IntegrationServicePojo servicePojo = this.integrationServiceDao.fetchOneById(pojo.getService());

    if (null == servicePojo) {
      throw new BusinessException("集成路由为{0}对应的服务数据不存在！", pojo.getCode());
    }

    // 构建集成路由实体
    final RouteEntity routeEntity = RouteEntity.newBuilder(
            // 集成触发事件
            pojo.getEvent(),
            // 集成处理组
            pojo.getGroup(),
            // 集成路由服务ID
            this.toServiceEntity(servicePojo))
        // 集成路由名
        .name(pojo.getName())
        // 集成路由描述
        .description(pojo.getDescription())
        // 执行构建
        .build();

    // 委托集成路由实体
    routeEntity.delegate(pojo);

    return routeEntity;
  }

  /**
   * 根据集成服务实体构建集成服务数据库映射
   *
   * @param serviceEntity 集成服务实体
   * @return 集成服务数据库映射
   */
  private IntegrationServicePojo fromServiceEntity(final ServiceEntity serviceEntity) {

    // 初始化集成服务数据库映射对象
    final IntegrationServicePojo integrationServicePojo = new IntegrationServicePojo();
    // 集成目标服务编码
    integrationServicePojo.setCode(serviceEntity.getIdentifier().toString());
    // 目标服务终结点
    integrationServicePojo.setEndpoint(serviceEntity.getEndpoint());
    // 目标集成类型（数据出入境）
    integrationServicePojo.setType(serviceEntity.getType().getCode().shortValue());
    // 目标服务名
    integrationServicePojo.setName(serviceEntity.getName());
    // 目标服务请求异常重试次数
    integrationServicePojo.setRetries(serviceEntity.getRetries().shortValue());
    // 目标服务请求连接超时时间（单位：milliseconds）
    integrationServicePojo.setConnecttimeout(serviceEntity.getConnectTimeout());
    // 目标服务认证信息ID
    integrationServicePojo.setAuthentication(serviceEntity.getAuthentication().getDelegator().getId());

    return integrationServicePojo;
  }

  /**
   * 根据集成服务数据库映射构建集成服务实体
   *
   * @param pojo 集成服务数据库映射
   * @return 集成服务实体
   */
  private ServiceEntity toServiceEntity(final IntegrationServicePojo pojo) {

    // 根据集成服务认证ID获取集成服务认证数据
    final IntegrationAuthenticationPojo authenticationPojo = this.integrationAuthenticationDao.fetchOneById(pojo.getAuthentication());

    // 构建集成服务实体
    final ServiceEntity serviceEntity = ServiceEntity.newBuilder(
            // 目标服务终结点
            pojo.getEndpoint(),
            // 目标集成类型（数据出入境）
            IntegrationType.of(pojo.getType().intValue()))
        // 目标服务名
        .name(pojo.getName())
        // 目标服务请求异常重试次数
        .retries(pojo.getRetries().intValue())
        // 目标服务请求连接超时时间（单位：milliseconds）
        .connectTimeout(pojo.getConnecttimeout())
        // 目标服务认证信息ID
        .authentication(this.toAuthenticationValueObject(authenticationPojo))
        // 执行构建
        .build();

    // 委托集成服务实体
    serviceEntity.delegate(pojo);

    return serviceEntity;
  }

  /**
   * 根据集成服务认证值对象构建集成服务认证数据库映射
   *
   * @param authenticationValueObject 集成服务认证值对象
   * @return 集成服务认证数据库映射
   */
  private IntegrationAuthenticationPojo fromAuthenticationValueObject(final AuthenticationValueObject authenticationValueObject) {

    // 初始化集成服务认证数据库映射对象
    final IntegrationAuthenticationPojo integrationAuthenticationPojo = new IntegrationAuthenticationPojo();
    // 集成目标服务认证用Key
    integrationAuthenticationPojo.setAppkey(authenticationValueObject.getAppKey());
    // 集成目标服务认证用秘钥（AES256加密保存）
    integrationAuthenticationPojo.setAppsecret(authenticationValueObject.getAppSecret());
    // 集成目标服务认证证书
    integrationAuthenticationPojo.setX509certificate(authenticationValueObject.getX509Certificate());

    return integrationAuthenticationPojo;
  }

  /**
   * 根据集成服务认证数据库映射构建集成服务认证值对象
   *
   * @param pojo 集成服务认证数据库映射
   * @return 集成服务认证值对象
   */
  private AuthenticationValueObject toAuthenticationValueObject(final IntegrationAuthenticationPojo pojo) {

    // 构建集成服务认证值对象
    final AuthenticationValueObject authenticationValueObject = AuthenticationValueObject.newBuilder()
        // 集成目标服务认证用Key
        .appKey(pojo.getAppkey())
        // 集成目标服务认证用秘钥（AES256加密保存）
        .appSecret(pojo.getAppsecret())
        // 集成目标服务认证证书
        .x509Certificate(pojo.getX509certificate())
        // 执行构建
        .build();

    // 委托集成服务认证值对象
    authenticationValueObject.delegate(pojo);

    return authenticationValueObject;
  }
}
