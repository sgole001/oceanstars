package oceanstars.ecommerce.infrastructure.grpc.security.server.check;

import io.grpc.ServerCall;
import java.util.Collection;
import java.util.function.Supplier;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.core.Authentication;

/**
 * gRPC访问权限管理器
 *
 * @author Clover
 * @version 1.0.0
 * @since 2024/1/22 10:27
 */
public class AccessPredicateManager implements AuthorizationManager<ServerCall<?, ?>> {

  /**
   * gRPC安全元数据源
   */
  private final GrpcSecurityMetadataSource securityMetadataSource;

  /**
   * 构造函数
   *
   * @param securityMetadataSource gRPC安全元数据源
   */
  public AccessPredicateManager(final GrpcSecurityMetadataSource securityMetadataSource) {
    this.securityMetadataSource = securityMetadataSource;
  }

  @Override
  public AuthorizationDecision check(Supplier<Authentication> authentication, ServerCall<?, ?> object) {

    // 获取gRPC安全元数据源中的访问权限
    final Collection<ConfigAttribute> attributes = this.securityMetadataSource.getAttributes(object);

    // 遍历gRPC安全元数据源中的访问权限，如果有一个访问权限满足，则返回允许访问
    for (ConfigAttribute configAttribute : attributes) {
      final AccessPredicate accessPredicate = ((AccessPredicateConfigAttribute) configAttribute).accessPredicate();
      if (accessPredicate.test(authentication.get(), object)) {
        return new AuthorizationDecision(true);
      }
    }

    return new AuthorizationDecision(false);
  }
}
