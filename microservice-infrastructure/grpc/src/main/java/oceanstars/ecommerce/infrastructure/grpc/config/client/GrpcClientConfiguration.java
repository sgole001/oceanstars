package oceanstars.ecommerce.infrastructure.grpc.config.client;

import io.grpc.CompressorRegistry;
import io.grpc.DecompressorRegistry;
import java.util.Collections;
import java.util.List;
import oceanstars.ecommerce.infrastructure.grpc.config.common.GrpcCodecConfiguration;
import oceanstars.ecommerce.infrastructure.grpc.factory.client.channel.GrpcChannelConfigurer;
import oceanstars.ecommerce.infrastructure.grpc.factory.client.channel.GrpcChannelFactory;
import oceanstars.ecommerce.infrastructure.grpc.factory.client.channel.InProcessChannelFactory;
import oceanstars.ecommerce.infrastructure.grpc.factory.client.channel.NettyChannelFactory;
import oceanstars.ecommerce.infrastructure.grpc.factory.client.channel.ShadedNettyChannelFactory;
import oceanstars.ecommerce.infrastructure.grpc.factory.client.stub.AsyncStubFactory;
import oceanstars.ecommerce.infrastructure.grpc.factory.client.stub.BlockingStubFactory;
import oceanstars.ecommerce.infrastructure.grpc.factory.client.stub.FutureStubFactory;
import oceanstars.ecommerce.infrastructure.grpc.interceptor.client.AnnotationGlobalClientInterceptorConfigurer;
import oceanstars.ecommerce.infrastructure.grpc.interceptor.client.GlobalClientInterceptorRegistry;
import oceanstars.ecommerce.infrastructure.grpc.service.consumer.GrpcClientBeanPostProcessor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

/**
 * gRPC客户端配置类
 *
 * @author Clover
 * @version 1.0.0
 * @since 2021/11/16 1:46 上午
 */
@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties
@AutoConfigureAfter(GrpcCodecConfiguration.class)
public class GrpcClientConfiguration {

  /**
   * 日志管理器
   */
  private final Logger logger = LogManager.getLogger(GrpcClientConfiguration.class.getName());

  /**
   * 配置注入gRPC客户端连接通道和Stub信息Bean
   *
   * @param applicationContext Spring应用程序上下文环境
   * @return gRPC客户端连接通道和Stub信息Bean
   */
  @Bean
  static GrpcClientBeanPostProcessor grpcClientBeanPostProcessor(final ApplicationContext applicationContext) {
    return new GrpcClientBeanPostProcessor(applicationContext);
  }

  /**
   * 配置注入gRPC客户端异步Stub工厂Bean
   *
   * @return gRPC客户端异步Stub工厂Bean
   */
  @Bean
  AsyncStubFactory asyncStubFactory() {
    return new AsyncStubFactory();
  }

  /**
   * 配置注入gRPC客户端一元阻塞Stub工厂Bean
   *
   * @return gRPC客户端一元阻塞Stub工厂Bean
   */
  @Bean
  BlockingStubFactory blockingStubFactory() {
    return new BlockingStubFactory();
  }

  /**
   * 配置注入gRPC客户端并行guava future(direct | callback) Stub工厂Bean
   *
   * @return gRPC客户端并行guava future(direct | callback) Stub工厂Bean
   */
  @Bean
  FutureStubFactory futureStubFactory() {
    return new FutureStubFactory();
  }

  /**
   * 配置注入gRPC客户端Channel属性Bean
   *
   * @return gRPC客户端Channel属性Bean
   */
  @ConditionalOnMissingBean
  @Bean
  GrpcChannelsProperties grpcChannelsProperties() {
    return new GrpcChannelsProperties();
  }

  /**
   * 配置注入gRPC全局客户端拦截器注册Bean
   *
   * @param applicationContext Spring应用程序上下文环境
   * @return gRPC全局客户端拦截器注册Bean
   */
  @ConditionalOnMissingBean
  @Bean
  GlobalClientInterceptorRegistry globalClientInterceptorRegistry(final ApplicationContext applicationContext) {
    return new GlobalClientInterceptorRegistry(applicationContext);
  }

  /**
   * 配置注入gRPC全局客户端拦截器配置Bean
   *
   * @param applicationContext Spring应用程序上下文环境
   * @return gRPC全局客户端拦截器配置Bean
   */
  @Bean
  @Lazy
  AnnotationGlobalClientInterceptorConfigurer annotationGlobalClientInterceptorConfigurer(final ApplicationContext applicationContext) {
    return new AnnotationGlobalClientInterceptorConfigurer(applicationContext);
  }

  /**
   * 配置注入gRPC客户端连接通道的编码压缩属性Bean
   *
   * @param registry 编码压缩注册Bean
   * @return gRPC客户端连接通道的编码压缩属性Bean
   */
  @ConditionalOnBean(CompressorRegistry.class)
  @Bean
  GrpcChannelConfigurer compressionChannelConfigurer(final CompressorRegistry registry) {
    return (builder, name) -> builder.compressorRegistry(registry);
  }

  /**
   * 配置注入gRPC客户端连接通道的解码解压属性Bean
   *
   * @param registry 解码解压注册Bean
   * @return gRPC客户端连接通道的解码解压属性Bean
   */
  @ConditionalOnBean(DecompressorRegistry.class)
  @Bean
  GrpcChannelConfigurer decompressionChannelConfigurer(final DecompressorRegistry registry) {
    return (builder, name) -> builder.decompressorRegistry(registry);
  }

  /**
   * 配置注入gRPC客户端连接通道默认属性配置（空）
   *
   * @return gRPC客户端连接通道默认属性配置
   */
  @ConditionalOnMissingBean(GrpcChannelConfigurer.class)
  @Bean
  List<GrpcChannelConfigurer> defaultChannelConfigurers() {
    return Collections.emptyList();
  }

  /**
   * 配置注入使用Shaded Netty连接方式的gRPC客户端连接通道工厂Bean（gRPC推荐使用）
   *
   * @param properties                      gRPC客户端连接通道属性配置
   * @param globalClientInterceptorRegistry gRPC全局客户端拦截器注册Bean
   * @param channelConfigurers              gRPC客户端连接通道配置列表
   * @return 使用Shaded Netty连接方式的gRPC客户端连接通道工厂Bean
   */
  @ConditionalOnMissingBean(GrpcChannelFactory.class)
  @ConditionalOnClass(name = {"io.grpc.netty.shaded.io.netty.channel.Channel", "io.grpc.netty.shaded.io.grpc.netty.NettyChannelBuilder"})
  @Bean
  @Lazy
  GrpcChannelFactory shadedNettyGrpcChannelFactory(
      final GrpcChannelsProperties properties,
      final GlobalClientInterceptorRegistry globalClientInterceptorRegistry,
      final List<GrpcChannelConfigurer> channelConfigurers) {

    logger.info("*** *** 检测到grpc-netty-shaded: 创建ShadedNettyChannelFactory");

    return new ShadedNettyChannelFactory(properties, globalClientInterceptorRegistry, channelConfigurers);
  }

  /**
   * 配置注入使用普通Netty连接方式的gRPC客户端连接通道工厂Bean
   *
   * @param properties                      gRPC客户端连接通道属性配置
   * @param globalClientInterceptorRegistry gRPC全局客户端拦截器注册Bean
   * @param channelConfigurers              gRPC客户端连接通道配置列表
   * @return 使用普通Netty连接方式的gRPC客户端连接通道工厂Bean
   */
  @ConditionalOnMissingBean(GrpcChannelFactory.class)
  @ConditionalOnClass(name = {"io.netty.channel.Channel", "io.grpc.netty.NettyChannelBuilder"})
  @Bean
  @Lazy
  GrpcChannelFactory nettyGrpcChannelFactory(
      final GrpcChannelsProperties properties,
      final GlobalClientInterceptorRegistry globalClientInterceptorRegistry,
      final List<GrpcChannelConfigurer> channelConfigurers) {

    logger.info("*** 检测到普通grpc-netty: 创建NettyChannelFactory");

    return new NettyChannelFactory(properties, globalClientInterceptorRegistry, channelConfigurers);
  }

  /**
   * 配置注入使用In-Process连接方式的gRPC客户端连接通道工厂Bean
   *
   * @param properties                      gRPC客户端连接通道属性配置
   * @param globalClientInterceptorRegistry gRPC全局客户端拦截器注册Bean
   * @param channelConfigurers              gRPC客户端连接通道配置列表
   * @return 使用In-Process连接方式的gRPC客户端连接通道工厂Bean
   */
  @ConditionalOnMissingBean(GrpcChannelFactory.class)
  @Bean
  @Lazy
  GrpcChannelFactory inProcessGrpcChannelFactory(
      final GrpcChannelsProperties properties,
      final GlobalClientInterceptorRegistry globalClientInterceptorRegistry,
      final List<GrpcChannelConfigurer> channelConfigurers) {

    logger.warn("*** 未能发现GrpcChannelFactory: 创建InProcessChannelFactory作为后备");

    return new InProcessChannelFactory(properties, globalClientInterceptorRegistry, channelConfigurers);
  }
}
