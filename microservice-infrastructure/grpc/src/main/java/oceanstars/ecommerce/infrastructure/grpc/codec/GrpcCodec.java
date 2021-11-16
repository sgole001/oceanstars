package oceanstars.ecommerce.infrastructure.grpc.codec;

import io.grpc.Codec;

/**
 * 注解：被标记的gRPC编解码器应该被注册到gRPC服务器中，并且注解只能添加到实现了{@link Codec}的Bean上。
 *
 * @author Clover
 * @version 1.0.0
 * @since 2021/11/11 1:03 下午
 */
public @interface GrpcCodec {

  /**
   * 推荐的编解码器，并会被添加到header的{@code Accept-Encoding}中，默认false
   *
   * @return True:编解码器会被推荐，否则False
   */
  boolean advertised() default false;

  /**
   * 编解码器类型，默认既可以用作压缩也可以用作解压
   *
   * @return 编解码器类型
   */
  CodecType codecType() default CodecType.ALL;
}
