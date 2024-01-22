package oceanstars.ecommerce.infrastructure.grpc.security.server.authentication;

import io.grpc.Metadata;
import io.grpc.ServerCall;
import java.util.ArrayList;
import java.util.List;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

/**
 * 组合gRPC认证读取器
 *
 * @author Clover
 * @version 1.0.0
 * @since 2024/1/21 10:49
 */
public class CompositeGrpcAuthenticationReader implements GrpcAuthenticationReader {

  /**
   * gRPC认证读取器列表
   */
  private final List<GrpcAuthenticationReader> authenticationReaders;

  /**
   * 构造函数
   *
   * @param authenticationReaders gRPC认证读取器列表
   */
  public CompositeGrpcAuthenticationReader(final List<GrpcAuthenticationReader> authenticationReaders) {
    this.authenticationReaders = new ArrayList<>(authenticationReaders);
  }

  @Override
  public Authentication readAuthentication(final ServerCall<?, ?> call, final Metadata headers) throws AuthenticationException {

    // 遍历所有的gRPC认证读取器，返回第一个不为空的认证信息
    for (final GrpcAuthenticationReader authenticationReader : this.authenticationReaders) {
      final Authentication authentication = authenticationReader.readAuthentication(call, headers);
      if (authentication != null) {
        return authentication;
      }
    }
    return null;
  }
}
