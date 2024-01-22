package oceanstars.ecommerce.infrastructure.grpc.codec;

import com.google.common.collect.ImmutableList;
import io.grpc.Codec;
import java.util.Collection;

/**
 * gRPC编解码器定义类
 *
 * @param codec      编解码器
 * @param advertised 是否推荐此编解码器
 * @param codecType  编解码器类型
 * @author Clover
 * @version 1.0.0
 * @since 2021/11/11 1:17 下午
 */
public record GrpcCodecDefinition(Codec codec, boolean advertised, CodecType codecType) {

  /**
   * gzip的编解码器定义
   */
  public static final GrpcCodecDefinition GZIP_DEFINITION = new GrpcCodecDefinition(new Codec.Gzip(), true, CodecType.ALL);

  /**
   * Identity (no-op: 恒等无操作)的编解码器定义
   */
  public static final GrpcCodecDefinition IDENTITY_DEFINITION = new GrpcCodecDefinition(Codec.Identity.NONE, false, CodecType.ALL);

  /**
   * gRPC默认使用的编码定义集合
   */
  public static final Collection<GrpcCodecDefinition> DEFAULT_DEFINITIONS =
      ImmutableList.<GrpcCodecDefinition>builder()
          .add(GZIP_DEFINITION)
          .add(IDENTITY_DEFINITION)
          .build();

  /**
   * 构造函数
   *
   * @param codec      编解码器
   * @param advertised 是否推荐此编解码器
   * @param codecType  编解码器类型
   */
  public GrpcCodecDefinition {
  }
}
