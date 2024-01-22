package oceanstars.ecommerce.infrastructure.grpc.config.client;

/**
 * gRPC客户端协议协商类型
 *
 * @author Clover
 * @version 1.0.0
 * @since 2024/1/19 20:07
 */
public enum NegotiationType {

  /**
   * 使用TLS ALPN/NPN协商，假设有SSL连接
   */
  TLS,

  /**
   * 使用HTTP UPGRADE协议从HTTP/1.1明文(非ssl)升级到HTTP/2。
   */
  PLAINTEXT_UPGRADE,

  /**
   * 假设连接是明文的(非ssl)，并且远程端点直接支持HTTP/2而无需升级。
   */
  PLAINTEXT
}
