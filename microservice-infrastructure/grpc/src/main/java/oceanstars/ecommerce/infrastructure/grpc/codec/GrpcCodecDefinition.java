package oceanstars.ecommerce.infrastructure.grpc.codec;

import com.google.common.collect.ImmutableList;
import io.grpc.Codec;
import java.util.Collection;

/**
 * gRPC编解码器定义类
 *
 * @author Clover
 * @version 1.0.0
 * @since 2021/11/11 1:17 下午
 */
public class GrpcCodecDefinition {

  /**
   * gzip的编解码器定义
   */
  public static final GrpcCodecDefinition GZIP_DEFINITION = new GrpcCodecDefinition(new Codec.Gzip(), true, CodecType.ALL);

  /**
   * Identity (no-op)的编解码器定义
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
   * 编解码器
   */
  private final Codec codec;

  /**
   * 是否推荐此编解码器
   */
  private final boolean advertised;

  /**
   * 编解码器类型
   */
  private final CodecType codecType;

  /**
   * 构造函数
   *
   * @param codec      编解码器
   * @param advertised 是否推荐此编解码器
   * @param codecType  编解码器类型
   */
  public GrpcCodecDefinition(final Codec codec, final boolean advertised, final CodecType codecType) {
    this.codec = codec;
    this.advertised = advertised;
    this.codecType = codecType;
  }

  public Codec getCodec() {
    return codec;
  }

  public boolean isAdvertised() {
    return advertised;
  }

  public CodecType getCodecType() {
    return codecType;
  }
}
