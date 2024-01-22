package oceanstars.ecommerce.infrastructure.grpc.security.server.authentication;

import io.grpc.Metadata;
import io.grpc.ServerCall;
import jakarta.annotation.Nullable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

/**
 * gRPC认证读取器接口,将获取到的ServerCall和Metadata转换为Authentication,用于后续配合Authentication Provider实施认证处理（AuthenticationManager）
 *
 * @author Clover
 * @version 1.0.0
 * @since 2024/1/21 10:24
 */
@FunctionalInterface
public interface GrpcAuthenticationReader {

  /**
   * 读取gRPC认证信息，将获取到的ServerCall和Metadata转换为Authentication
   *
   * @param call    gRPC服务调用
   * @param headers gRPC元数据
   * @return gRPC认证信息
   * @throws AuthenticationException 认证异常
   */
  @Nullable
  Authentication readAuthentication(ServerCall<?, ?> call, Metadata headers) throws AuthenticationException;
}
