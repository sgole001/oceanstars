package oceanstars.ecommerce.infrastructure.grpc.codec;

/**
 * 编解码器类型枚举
 *
 * @author Clover
 * @version 1.0.0
 * @since 2021/11/11 12:54 下午
 */
public enum CodecType {

  /**
   * 编解码器只用作压缩
   */
  COMPRESS(true, false),

  /**
   * 编解码器只用作解压
   */
  DECOMPRESS(false, true),

  /**
   * 编解码器压缩和解压都可以使用
   */
  ALL(true, true);

  /**
   * 是否压缩
   */
  private final boolean isCompression;

  /**
   * 是否解压
   */
  private final boolean isDecompression;

  CodecType(final boolean isCompression, final boolean isDecompression) {
    this.isCompression = isCompression;
    this.isDecompression = isDecompression;
  }

  public boolean isCompression() {
    return this.isCompression;
  }

  public boolean isDecompression() {
    return this.isDecompression;
  }
}
