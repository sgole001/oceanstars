package oceanstars.ecommerce.infrastructure.grpc.codec;

import com.google.common.collect.ImmutableList;
import io.grpc.Codec;
import java.util.Collection;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.lang.NonNull;

/**
 * gRPC编解码器发现函数式接口实现类
 *
 * @author Clover
 * @version 1.0.0
 * @since 2021/11/11 1:23 下午
 */
public class AnnotationGrpcCodecDiscoverer implements ApplicationContextAware, GrpcCodecDiscoverer {

  /**
   * Spring应用程序上下文环境
   */
  private ApplicationContext applicationContext;

  /**
   * gRPC编解码器定义集合
   */
  private Collection<GrpcCodecDefinition> definitions;

  /**
   * 日志管理器
   */
  private final Logger logger = LogManager.getLogger(AnnotationGrpcCodecDiscoverer.class.getName());

  @Override
  public void setApplicationContext(@NonNull ApplicationContext applicationContext) throws BeansException {
    this.applicationContext = applicationContext;
  }

  @Override
  public Collection<GrpcCodecDefinition> findGrpcCodecs() {

    if (this.definitions == null) {

      // 获取被注解GrpcCodec标记的bean名集合
      final String[] beanNames = this.applicationContext.getBeanNamesForAnnotation(GrpcCodec.class);
      // 创建gRPC编解码器集合构建器
      final ImmutableList.Builder<GrpcCodecDefinition> builder = ImmutableList.builder();

      // 遍历被注解GrpcCodec标记的bean名集合
      for (final String beanName : beanNames) {

        // 获取编解码器Bean对象
        final Codec codec = this.applicationContext.getBean(beanName, Codec.class);
        // 获取获取编解码器注解实例对象
        final GrpcCodec annotation = this.applicationContext.findAnnotationOnBean(beanName, GrpcCodec.class);

        if (null != annotation) {
          // 添加gRPC编解码器定义
          builder.add(new GrpcCodecDefinition(codec, annotation.advertised(), annotation.codecType()));
          // 调试信息
          logger.debug("发现gRPC编解码器: {}, bean: {}, class: {}", codec.getMessageEncoding(), beanName, codec.getClass().getName());
        }
      }

      // 构建gRPC编解码器集合
      this.definitions = builder.build();
    }

    return this.definitions;
  }
}
