package oceanstars.ecommerce.infrastructure.grpc.config.common;

import io.grpc.Codec;
import io.grpc.CompressorRegistry;
import io.grpc.DecompressorRegistry;
import oceanstars.ecommerce.infrastructure.grpc.codec.AnnotationGrpcCodecDiscoverer;
import oceanstars.ecommerce.infrastructure.grpc.codec.GrpcCodecDefinition;
import oceanstars.ecommerce.infrastructure.grpc.codec.GrpcCodecDiscoverer;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;

/**
 * gRPC编解码器配置类
 *
 * @author Clover
 * @version 1.0.0
 * @since 2021/11/11 1:56 下午
 */
@AutoConfiguration
@ConditionalOnClass(Codec.class)
public class GrpcCodecConfiguration {

  /**
   * 配置注入gRPC编解码器发现用Bean
   *
   * @return gRPC编解码器发现用Bean
   */
  @ConditionalOnMissingBean
  @Bean
  public GrpcCodecDiscoverer defaultGrpcCodecDiscoverer() {
    return new AnnotationGrpcCodecDiscoverer();
  }

  /**
   * 配置注入创建gRPC压缩注册用Bean
   *
   * @param codecDiscoverer gRPC编解码器发现用Bean
   * @return gRPC压缩注册用Bean
   */
  @ConditionalOnBean(GrpcCodecDiscoverer.class)
  @ConditionalOnMissingBean
  @Bean
  public CompressorRegistry defaultCompressorRegistry(final GrpcCodecDiscoverer codecDiscoverer) {

    // 创建gRPC压缩注册实例
    final CompressorRegistry registry = CompressorRegistry.getDefaultInstance();

    // 查询获取gRPC编解码器，并遍历注册
    for (final GrpcCodecDefinition definition : codecDiscoverer.findGrpcCodecs()) {
      if (definition.codecType().isCompression()) {
        registry.register(definition.codec());
      }
    }

    return registry;
  }

  /**
   * 配置注入gRPC解压注册用Bean
   *
   * @param codecDiscoverer gRPC编解码器发现用Bean
   * @return gRPC解压注册用Bean
   */
  @ConditionalOnBean(GrpcCodecDiscoverer.class)
  @ConditionalOnMissingBean
  @Bean
  public DecompressorRegistry defaultDecompressorRegistry(final GrpcCodecDiscoverer codecDiscoverer) {

    // 创建gRPC解压注册实例
    DecompressorRegistry registry = DecompressorRegistry.getDefaultInstance();

    // 查询获取gRPC编解码器，并遍历注册
    for (final GrpcCodecDefinition definition : codecDiscoverer.findGrpcCodecs()) {
      if (definition.codecType().isDecompression()) {
        registry = registry.with(definition.codec(), definition.advertised());
      }
    }

    return registry;
  }
}
