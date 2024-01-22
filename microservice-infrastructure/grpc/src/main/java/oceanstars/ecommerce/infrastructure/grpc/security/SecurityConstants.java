package oceanstars.ecommerce.infrastructure.grpc.security;

import io.grpc.Metadata;
import io.grpc.Metadata.Key;

/**
 * gRPC安全常量
 *
 * @author Clover
 * @version 1.0.0
 * @since 2024/1/19 16:16
 */
public final class SecurityConstants {

  /**
   * 私有化构造器
   */
  private SecurityConstants() {
  }

  /**
   * 认证头
   */
  public static final Key<String> AUTHORIZATION_HEADER = Key.of("Authorization", Metadata.ASCII_STRING_MARSHALLER);

  /**
   * 基本认证前缀
   */
  public static final String BASIC_AUTH_PREFIX = "Basic ";

  /**
   * Bearer认证前缀
   */
  public static final String BEARER_AUTH_PREFIX = "Bearer ";
}
