package oceanstars.ecommerce.infrastructure.grpc.factory.server;

import static java.util.Objects.requireNonNull;

import com.google.common.collect.Lists;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import oceanstars.ecommerce.infrastructure.grpc.config.server.GrpcServerProperties;
import oceanstars.ecommerce.infrastructure.grpc.service.provider.GrpcServiceDefinition;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.util.unit.DataSize;

/**
 * gRPC服务器工厂抽象类
 *
 * @author Clover
 * @version 1.0.0
 * @since 2021/11/9 1:49 下午
 */
public abstract class AbstractGrpcServerFactory<T extends ServerBuilder<T>> implements GrpcServerFactory {

  /**
   * gRPC服务定义列表
   */
  private final List<GrpcServiceDefinition> serviceList = Lists.newLinkedList();

  /**
   * gRPC服务器属性配置
   */
  protected final GrpcServerProperties properties;

  /**
   * gRPC服务器配置列表
   */
  protected final List<GrpcServerConfigurer> serverConfigurers;

  /**
   * 日志管理器
   */
  private final Logger logger = LogManager.getLogger(AbstractGrpcServerFactory.class.getName());

  /**
   * 构造函数
   *
   * @param properties        gRPC服务器属性配置
   * @param serverConfigurers gRPC服务器配置列表
   */
  protected AbstractGrpcServerFactory(final GrpcServerProperties properties, final List<GrpcServerConfigurer> serverConfigurers) {
    this.properties = requireNonNull(properties, "properties");
    this.serverConfigurers = requireNonNull(serverConfigurers, "serverConfigurers");
  }

  @Override
  public Server createServer() {
    // 创建gRPC服务器构建对象实例
    final T builder = newServerBuilder();
    // 配置构建对象
    configure(builder);
    // 构建gRPC服务器
    return builder.build();
  }

  /**
   * 创建服务器构建对象实例
   *
   * @return 服务器构建对象实例
   */
  protected abstract T newServerBuilder();

  /**
   * 配置gRPC服务器构建对象
   *
   * @param builder gRPC服务器构建对象实例
   */
  protected void configure(final T builder) {
    // 配置gRPC服务相关属性
    configureServices(builder);
    // 配置Keep Alive相关属性
    configureKeepAlive(builder);
    // 配置连接限制相关属性
    configureConnectionLimits(builder);
    // 配置安全相关属性
    configureSecurity(builder);
    // 配置服务器限制相关属性
    configureLimits(builder);
    for (final GrpcServerConfigurer serverConfigurer : this.serverConfigurers) {
      serverConfigurer.accept(builder);
    }
  }

  /**
   * 配置gRPC服务相关属性
   *
   * @param builder gRPC服务器构建对象实例
   */
  protected void configureServices(final T builder) {

    // 初始化gRPC服务名集合
    final Set<String> serviceNames = new LinkedHashSet<>();

    // 遍历gRPC服务列表
    for (final GrpcServiceDefinition service : this.serviceList) {

      // 获取gRPC服务名
      final String serviceName = service.definition().getServiceDescriptor().getName();

      if (!serviceNames.add(serviceName)) {
        throw new IllegalStateException("发现多个服务实现: " + serviceName);
      }

      // 添加gRPC服务
      builder.addService(service.definition());

      logger.debug("已经注册的gRPC服务: {}, bean: {}, class: {}", serviceName, service.beanName(), service.beanClazz().getName());
    }
  }

  /**
   * 配置Keep Alive相关属性
   *
   * @param builder gRPC服务器构建对象实例
   */
  protected void configureKeepAlive(final T builder) {
    if (this.properties.isEnableKeepAlive()) {
      throw new IllegalStateException("KeepAlive已开启，但当前实现不支持KeepAlive!");
    }
  }

  /**
   * 配置连接限制相关属性
   *
   * @param builder gRPC服务器构建对象实例
   */
  protected void configureConnectionLimits(final T builder) {
    if (this.properties.getMaxConnectionIdle() != null) {
      throw new IllegalStateException("MaxConnectionIdle已被设置，但当前实现不支持maxConnectionIdle!");
    }
    if (this.properties.getMaxConnectionAge() != null) {
      throw new IllegalStateException("MaxConnectionAge已被设置，但当前实现不支持maxConnectionAge!");
    }
    if (this.properties.getMaxConnectionAgeGrace() != null) {
      throw new IllegalStateException("MaxConnectionAgeGrace已被设置，但当前实现不支持maxConnectionAgeGrace!");
    }
  }

  /**
   * 配置安全相关属性
   *
   * @param builder gRPC服务器构建对象实例
   */
  protected void configureSecurity(final T builder) {
    // 如果开启了安全性，但当前实现不支持安全性，则抛出异常
    if (this.properties.getSecurity().isEnabled()) {
      throw new IllegalStateException("配置了安全性，但此实现不支持安全性!");
    }
  }

  /**
   * 配置服务器限制相关属性
   *
   * @param builder gRPC服务器构建对象实例
   */
  protected void configureLimits(final T builder) {
    final DataSize maxInboundMessageSize = this.properties.getMaxInboundMessageSize();
    if (maxInboundMessageSize != null) {
      builder.maxInboundMessageSize((int) maxInboundMessageSize.toBytes());
    }
    final DataSize maxInboundMetadataSize = this.properties.getMaxInboundMetadataSize();
    if (maxInboundMetadataSize != null) {
      builder.maxInboundMetadataSize((int) maxInboundMetadataSize.toBytes());
    }
  }

  @Override
  public String getAddress() {
    return this.properties.getAddress();
  }

  @Override
  public int getPort() {
    return this.properties.getPort();
  }

  @Override
  public void addService(final GrpcServiceDefinition service) {
    this.serviceList.add(service);
  }
}
