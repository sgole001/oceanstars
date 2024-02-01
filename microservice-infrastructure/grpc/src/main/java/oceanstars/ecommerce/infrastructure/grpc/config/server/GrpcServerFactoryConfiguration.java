package oceanstars.ecommerce.infrastructure.grpc.config.server;

import java.util.List;
import oceanstars.ecommerce.infrastructure.grpc.condition.server.ConditionalOnInterprocessServer;
import oceanstars.ecommerce.infrastructure.grpc.factory.server.GrpcServerConfigurer;
import oceanstars.ecommerce.infrastructure.grpc.factory.server.GrpcServerFactory;
import oceanstars.ecommerce.infrastructure.grpc.factory.server.GrpcServerLifecycle;
import oceanstars.ecommerce.infrastructure.grpc.factory.server.InProcessGrpcServerFactory;
import oceanstars.ecommerce.infrastructure.grpc.factory.server.NettyGrpcServerFactory;
import oceanstars.ecommerce.infrastructure.grpc.factory.server.ShadedNettyGrpcServerFactory;
import oceanstars.ecommerce.infrastructure.grpc.service.provider.GrpcServiceDefinition;
import oceanstars.ecommerce.infrastructure.grpc.service.provider.GrpcServiceDiscoverer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;

/**
 * grpc服务器工厂配置类
 *
 * @author Clover
 * @version 1.0.0
 * @since 2021/11/11 5:10 下午
 */
@AutoConfiguration(after = {GrpcServerConfiguration.class})
@ConditionalOnMissingBean({GrpcServerFactory.class, GrpcServerLifecycle.class})
public class GrpcServerFactoryConfiguration {

  /**
   * 日志管理器
   */
  private final Logger logger = LogManager.getLogger(GrpcServerFactoryConfiguration.class.getName());

  /**
   * 配置注入使用Shaded Netty连接方式的gRPC服务器工厂Bean（gRPC推荐使用）
   *
   * @param properties        gRPC服务器属性配置
   * @param serviceDiscoverer gRPC服务发现接口
   * @param serverConfigurers gRPC服务配置列表
   * @return 使用Shaded Netty连接方式的gRPC服务器工厂Bean
   */
  @ConditionalOnClass(name = {"io.grpc.netty.shaded.io.netty.channel.Channel", "io.grpc.netty.shaded.io.grpc.netty.NettyServerBuilder"})
  @Conditional(ConditionalOnInterprocessServer.class)
  @Bean
  public ShadedNettyGrpcServerFactory shadedNettyGrpcServerFactory(
      final GrpcServerProperties properties,
      final GrpcServiceDiscoverer serviceDiscoverer,
      final List<GrpcServerConfigurer> serverConfigurers) {

    logger.info("*** 检测到grpc-netty-shaded: 创建ShadedNettyGrpcServerFactory");

    // 创建gRPC Shaded Netty连接 服务器工厂实例
    final ShadedNettyGrpcServerFactory factory = new ShadedNettyGrpcServerFactory(properties, serverConfigurers);

    // 查询gRPC服务，并遍历添加到gRPC服务器配置
    for (final GrpcServiceDefinition service : serviceDiscoverer.findGrpcServices()) {
      factory.addService(service);
    }

    return factory;
  }

  /**
   * 配置注入使用Shaded Netty连接方式的gRPC服务器的生命周期Bean
   *
   * @param factory    使用Shaded Netty连接方式的gRPC服务器工厂
   * @param properties gRPC服务器属性配置
   * @return 使用Shaded Netty连接方式的gRPC服务器的生命周期Bean
   */
  @ConditionalOnBean(ShadedNettyGrpcServerFactory.class)
  @Bean
  public GrpcServerLifecycle shadedNettyGrpcServerLifecycle(final ShadedNettyGrpcServerFactory factory, final GrpcServerProperties properties) {
    return new GrpcServerLifecycle(factory, properties.getShutdownGracePeriod());
  }

  /**
   * 配置注入使用普通Netty连接方式的gRPC服务器工厂Bean（在没有Shaded的情况下，退而求其次）
   *
   * @param properties        gRPC服务器属性配置
   * @param serviceDiscoverer gRPC服务发现接口
   * @param serverConfigurers gRPC服务配置列表
   * @return 使用普通Netty连接方式的gRPC服务器工厂Bean
   */
  @ConditionalOnMissingBean(ShadedNettyGrpcServerFactory.class)
  @Conditional(ConditionalOnInterprocessServer.class)
  @ConditionalOnClass(name = {"io.netty.channel.Channel", "io.grpc.netty.NettyServerBuilder"})
  @Bean
  public NettyGrpcServerFactory nettyGrpcServerFactory(
      final GrpcServerProperties properties,
      final GrpcServiceDiscoverer serviceDiscoverer,
      final List<GrpcServerConfigurer> serverConfigurers) {

    logger.info("*** 检测到grpc-netty: 创建NettyGrpcServerFactory");

    // 创建gRPC 普通Netty连接 服务器工厂实例
    final NettyGrpcServerFactory factory = new NettyGrpcServerFactory(properties, serverConfigurers);

    // 查询gRPC服务，并遍历添加到gRPC服务器配置
    for (final GrpcServiceDefinition service : serviceDiscoverer.findGrpcServices()) {
      factory.addService(service);
    }

    return factory;
  }

  /**
   * 配置注入使用普通Netty连接方式的gRPC服务器的生命周期Bean
   *
   * @param factory    使用普通Netty连接方式的gRPC服务器工厂
   * @param properties gRPC服务器属性配置
   * @return 使用普通Netty连接方式的gRPC服务器的生命周期Bean
   */
  @ConditionalOnBean(NettyGrpcServerFactory.class)
  @Bean
  public GrpcServerLifecycle nettyGrpcServerLifecycle(final NettyGrpcServerFactory factory, final GrpcServerProperties properties) {
    return new GrpcServerLifecycle(factory, properties.getShutdownGracePeriod());
  }

  /**
   * 配置注入in-process方式的gRPC服务器工厂Bean（一般作为测试使用，可以在同一JVM的环境中进行gRPC调用）
   *
   * @param properties        gRPC服务器属性配置
   * @param serviceDiscoverer gRPC服务发现接口
   * @return in-process方式的gRPC服务器工厂Bean
   */
  @ConditionalOnProperty(prefix = "grpc.server", name = "in-process-name")
  @Bean
  public InProcessGrpcServerFactory inProcessGrpcServerFactory(final GrpcServerProperties properties, final GrpcServiceDiscoverer serviceDiscoverer) {

    logger.info("*** 属性grpc.server.in-process-name被设定: 创建InProcessGrpcServerFactory");

    // 创建gRPC in-process服务器工厂实例
    final InProcessGrpcServerFactory factory = new InProcessGrpcServerFactory(properties);

    // 查询gRPC服务，并遍历添加到gRPC服务器配置
    for (final GrpcServiceDefinition service : serviceDiscoverer.findGrpcServices()) {
      factory.addService(service);
    }

    return factory;
  }

  /**
   * 配置注入in-process方式的gRPC服务器的生命周期Bean
   *
   * @param factory    使用in-process方式的gRPC服务器工厂
   * @param properties gRPC服务器属性配置
   * @return in-process方式的gRPC服务器的生命周期Bean
   */
  @ConditionalOnBean(InProcessGrpcServerFactory.class)
  @Bean
  public GrpcServerLifecycle inProcessGrpcServerLifecycle(final InProcessGrpcServerFactory factory, final GrpcServerProperties properties) {
    return new GrpcServerLifecycle(factory, properties.getShutdownGracePeriod());
  }
}
