package oceanstars.ecommerce.infrastructure.grpc.config.server;

/**
 * gRPC客户端认证枚举
 *
 * @author Clover
 * @version 1.0.0
 * @since 2024/1/19 21:19
 */
public enum ClientAuth {

  /**
   * 不需要客户端认证
   */
  NONE,

  /**
   * 可选客户端认证
   */
  OPTIONAL,

  /**
   * 必须客户端认证
   */
  REQUIRE;
}
