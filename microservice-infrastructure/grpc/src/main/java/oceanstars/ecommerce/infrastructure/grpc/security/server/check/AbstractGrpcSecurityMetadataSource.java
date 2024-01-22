package oceanstars.ecommerce.infrastructure.grpc.security.server.check;

import io.grpc.ServerCall;
import java.util.Collection;
import org.springframework.security.access.ConfigAttribute;

/**
 * gRPC请求安全元数据源接口虚类
 *
 * @author Clover
 * @version 1.0.0
 * @since 2024/1/21 12:48
 */
public abstract class AbstractGrpcSecurityMetadataSource implements GrpcSecurityMetadataSource {

  @Override
  public final Collection<ConfigAttribute> getAttributes(final Object object) throws IllegalArgumentException {
    if (object instanceof ServerCall) {
      return getAttributes((ServerCall<?, ?>) object);
    }
    throw new IllegalArgumentException("参数类型必须为ServerCall");
  }

  @Override
  public final boolean supports(final Class<?> clazz) {
    return ServerCall.class.isAssignableFrom(clazz);
  }
}
