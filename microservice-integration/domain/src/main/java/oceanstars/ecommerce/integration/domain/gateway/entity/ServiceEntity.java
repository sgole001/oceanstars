package oceanstars.ecommerce.integration.domain.gateway.entity;

import oceanstars.ecommerce.common.domain.Entity;
import oceanstars.ecommerce.integration.constant.enums.IntegrationEnum.IntegrationType;
import oceanstars.ecommerce.integration.domain.gateway.entity.valueobject.AuthenticationValueObject;
import oceanstars.ecommerce.integration.domain.gateway.entity.valueobject.BaseIntegrationRequestValueObject;
import oceanstars.ecommerce.integration.domain.gateway.entity.valueobject.BaseIntegrationResponseValueObject;

/**
 * 集成目标系统服务实体
 *
 * @author Clover
 * @version 1.0.0
 * @since 2021/12/29 10:25 上午
 */
public final class ServiceEntity extends Entity<ServiceIdentifier> {

  /**
   * 目标服务终结点
   */
  private final String endpoint;

  /**
   * 目标集成类型（数据出入境）
   */
  private final IntegrationType type;

  /**
   * 目标服务名
   */
  private String name;

  /**
   * 目标服务请求异常重试次数
   */
  private Integer retries;

  /**
   * 目标服务请求连接超时时间（单位：milliseconds）
   */
  private Integer connectTimeout;

  /**
   * 目标服务认证信息
   */
  private AuthenticationValueObject authentication;

  /**
   * 构造函数：根据构建器初始化成员变量
   *
   * @param builder 构建器
   */
  private ServiceEntity(Builder builder) {
    super(new ServiceIdentifier(builder.endpoint));
    endpoint = builder.endpoint;
    type = builder.type;
    name = builder.name;
    retries = builder.retries;
    connectTimeout = builder.connectTimeout;
    authentication = builder.authentication;
  }

  /**
   * 创建构建器
   *
   * @param endpoint 目标服务终结点
   * @param type     目标集成类型（数据出入境）
   * @return 构建器
   */
  public static Builder newBuilder(final String endpoint, final IntegrationType type) {
    return new Builder(endpoint, type);
  }

  /**
   * 集成目标服务处理
   *
   * @param request 集成请求信息
   * @return 集成响应信息
   */
  public BaseIntegrationResponseValueObject handle(final BaseIntegrationRequestValueObject request) {
    return request.handle(this);
  }

  public String getEndpoint() {
    return this.endpoint;
  }

  public IntegrationType getType() {
    return type;
  }

  public String getName() {
    return this.name;
  }

  public Integer getRetries() {
    return this.retries;
  }

  public Integer getConnectTimeout() {
    return this.connectTimeout;
  }

  public AuthenticationValueObject getAuthentication() {
    return this.authentication;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setRetries(Integer retries) {
    this.retries = retries;
  }

  public void setConnectTimeout(Integer connectTimeout) {
    this.connectTimeout = connectTimeout;
  }

  public void setAuthentication(AuthenticationValueObject authentication) {
    this.authentication = authentication;
  }

  /**
   * 集成目标系统服务实体构建器
   *
   * @author Clover
   * @version 1.0.0
   * @since 2021/12/29 4:49 下午
   */
  public static final class Builder {

    private final String endpoint;
    private final IntegrationType type;
    private String name;
    private Integer retries;
    private Integer connectTimeout;
    private AuthenticationValueObject authentication;

    public Builder(String endpoint, IntegrationType type) {
      this.endpoint = endpoint;
      this.type = type;
    }

    public Builder name(String val) {
      name = val;
      return this;
    }

    public Builder retries(Integer val) {
      retries = val;
      return this;
    }

    public Builder connectTimeout(Integer val) {
      connectTimeout = val;
      return this;
    }

    public Builder authentication(AuthenticationValueObject val) {
      authentication = val;
      return this;
    }

    public ServiceEntity build() {
      return new ServiceEntity(this);
    }
  }
}
