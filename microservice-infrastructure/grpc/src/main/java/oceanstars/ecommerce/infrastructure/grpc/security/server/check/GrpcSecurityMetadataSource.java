package oceanstars.ecommerce.infrastructure.grpc.security.server.check;

import io.grpc.ServerCall;
import java.util.Collection;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityMetadataSource;

/**
 * gRPC请求安全元数据源接口
 *
 * @author Clover
 * @version 1.0.0
 * @since 2024/1/21 12:40
 */
public interface GrpcSecurityMetadataSource extends SecurityMetadataSource {

  /**
   * 获取gRPC请求的访问控制配置属性
   *
   * @param call gRPC服务调用请求对象
   * @return gRPC访问控制配置属性列表
   */
  Collection<ConfigAttribute> getAttributes(final ServerCall<?, ?> call);
}
