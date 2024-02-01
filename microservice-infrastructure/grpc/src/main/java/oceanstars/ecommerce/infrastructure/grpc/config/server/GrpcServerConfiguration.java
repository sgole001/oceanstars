package oceanstars.ecommerce.infrastructure.grpc.config.server;

import io.grpc.CompressorRegistry;
import io.grpc.DecompressorRegistry;
import io.grpc.Server;
import java.util.Collections;
import java.util.List;
import oceanstars.ecommerce.infrastructure.grpc.config.common.GrpcCodecConfiguration;
import oceanstars.ecommerce.infrastructure.grpc.factory.server.GrpcServerConfigurer;
import oceanstars.ecommerce.infrastructure.grpc.factory.server.GrpcServerFactory;
import oceanstars.ecommerce.infrastructure.grpc.factory.server.GrpcServerLifecycle;
import oceanstars.ecommerce.infrastructure.grpc.interceptor.server.AnnotationGlobalServerInterceptorConfigurer;
import oceanstars.ecommerce.infrastructure.grpc.interceptor.server.GlobalServerInterceptorRegistry;
import oceanstars.ecommerce.infrastructure.grpc.service.provider.AnnotationGrpcServiceDiscoverer;
import oceanstars.ecommerce.infrastructure.grpc.service.provider.GrpcServiceDiscoverer;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;

/**
 * grpc服务器配置类
 *
 * @author Clover
 * @version 1.0.0
 * @since 2021/11/11 2:22 下午
 */
@AutoConfiguration(after = {GrpcCodecConfiguration.class})
@EnableConfigurationProperties
@ConditionalOnClass(Server.class)
public class GrpcServerConfiguration {

  /**
   * 配置注入gRPC服务器属性配置Bean
   *
   * @return gRPC服务器属性配置Bean
   */
  @ConditionalOnMissingBean
  @Bean
  public GrpcServerProperties defaultGrpcServerProperties() {
    return new GrpcServerProperties();
  }

  /**
   * 配置注入gRPC服务端全局拦截器注册Bean
   *
   * @param applicationContext Spring应用程序上下文环境
   * @return gRPC服务端全局拦截器注册Bean
   */
  @ConditionalOnMissingBean
  @Bean
  GlobalServerInterceptorRegistry globalServerInterceptorRegistry(final ApplicationContext applicationContext) {
    return new GlobalServerInterceptorRegistry(applicationContext);
  }

  /**
   * 配置注入gRPC服务端全局拦截器配置Bean (延迟注入)
   *
   * @param applicationContext Spring应用程序上下文环境
   * @return gRPC服务端全局拦截器配置Bean
   */
  @Bean
  @Lazy
  AnnotationGlobalServerInterceptorConfigurer annotationGlobalServerInterceptorConfigurer(final ApplicationContext applicationContext) {
    return new AnnotationGlobalServerInterceptorConfigurer(applicationContext);
  }

  /**
   * 配置注入gRPC服务发现Bean
   *
   * @return gRPC服务发现Bean
   */
  @ConditionalOnMissingBean
  @Bean
  public GrpcServiceDiscoverer defaultGrpcServiceDiscoverer() {
    return new AnnotationGrpcServiceDiscoverer();
  }

  /**
   * 配置注入gRPC编码压缩注册配置Bean
   *
   * @param registry 压缩注册Bean
   * @return gRPC编码压缩注册配置Bean
   */
  @ConditionalOnBean(CompressorRegistry.class)
  @Bean
  public GrpcServerConfigurer compressionServerConfigurer(final CompressorRegistry registry) {
    return builder -> builder.compressorRegistry(registry);
  }

  /**
   * 配置注入gRPC解码解压注册配置Bean
   *
   * @param registry 解压注册Bean
   * @return gRPC解码解压注册配置Bean
   */
  @ConditionalOnBean(DecompressorRegistry.class)
  @Bean
  public GrpcServerConfigurer decompressionServerConfigurer(final DecompressorRegistry registry) {
    return builder -> builder.decompressorRegistry(registry);
  }

  /**
   * 配置注入默认gRPC服务器配置Bean （不存在其他服务器配置的情况下）
   *
   * @return 默认gRPC服务器配置Bean
   */
  @ConditionalOnMissingBean(GrpcServerConfigurer.class)
  @Bean
  public List<GrpcServerConfigurer> defaultServerConfigurers() {
    return Collections.emptyList();
  }

  /**
   * 配置注入gRPC服务器生命周期Bean
   *
   * @param factory    gRPC服务器工厂
   * @param properties gRPC服务器属性配置
   * @return gRPC服务器生命周期Bean
   */
  @ConditionalOnMissingBean
  @ConditionalOnBean(GrpcServerFactory.class)
  @Bean
  public GrpcServerLifecycle grpcServerLifecycle(final GrpcServerFactory factory, final GrpcServerProperties properties) {
    return new GrpcServerLifecycle(factory, properties.getShutdownGracePeriod());
  }
}
