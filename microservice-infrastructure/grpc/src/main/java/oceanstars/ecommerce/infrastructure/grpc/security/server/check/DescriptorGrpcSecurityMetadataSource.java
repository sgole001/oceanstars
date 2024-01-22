package oceanstars.ecommerce.infrastructure.grpc.security.server.check;

import static com.google.common.collect.ImmutableList.of;

import io.grpc.MethodDescriptor;
import io.grpc.ServerCall;
import io.grpc.ServiceDescriptor;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.security.access.ConfigAttribute;

/**
 * gRPC方法描述符访问权限管理器
 *
 * @author Clover
 * @version 1.0.0
 * @since 2024/1/22 09:42
 */
public class DescriptorGrpcSecurityMetadataSource extends AbstractGrpcSecurityMetadataSource {

  /**
   * gRPC方法描述符与访问权限集合映射
   */
  private final Map<MethodDescriptor<?, ?>, Collection<ConfigAttribute>> accessMap = new HashMap<>();

  /**
   * 默认访问权限集合
   */
  private Collection<ConfigAttribute> defaultAttributes = this.wrap(AccessPredicate.denyAll());

  @Override
  public Collection<ConfigAttribute> getAttributes(ServerCall<?, ?> call) {
    return this.getAttributes(call.getMethodDescriptor());
  }

  @Override
  public Collection<ConfigAttribute> getAllConfigAttributes() {
    return this.accessMap.values().stream().flatMap(Collection::stream).collect(Collectors.toSet());
  }

  /**
   * 根据服务描述符设置访问权限
   *
   * @param service   服务描述符
   * @param predicate 访问权限
   * @return gRPC请求安全元数据源
   */
  public DescriptorGrpcSecurityMetadataSource set(final ServiceDescriptor service, final AccessPredicate predicate) {

    // 包装访问权限
    final Collection<ConfigAttribute> wrappedPredicate = this.wrap(predicate);
    // 遍历服务中的所有方法，设置访问权限
    for (final MethodDescriptor<?, ?> method : service.getMethods()) {
      this.accessMap.put(method, wrappedPredicate);
    }
    return this;
  }

  /**
   * 根据服务方法设置访问权限
   *
   * @param method    服务方法描述符
   * @param predicate 访问权限
   * @return gRPC请求安全元数据源
   */
  public DescriptorGrpcSecurityMetadataSource set(final MethodDescriptor<?, ?> method, final AccessPredicate predicate) {
    // 根据服务方法设置访问权限
    this.accessMap.put(method, this.wrap(predicate));
    return this;
  }

  /**
   * 设置默认访问权限
   *
   * @param predicate 访问权限
   * @return gRPC请求安全元数据源
   */
  public DescriptorGrpcSecurityMetadataSource setDefault(final AccessPredicate predicate) {
    this.defaultAttributes = this.wrap(predicate);
    return this;
  }

  /**
   * 移除服务中的所有方法的访问权限
   *
   * @param service 服务描述符
   * @return gRPC请求安全元数据源
   */
  public DescriptorGrpcSecurityMetadataSource remove(final ServiceDescriptor service) {
    // 遍历服务中的所有方法，移除访问权限
    for (final MethodDescriptor<?, ?> method : service.getMethods()) {
      this.accessMap.remove(method);
    }
    return this;
  }

  /**
   * 移除服务方法的访问权限
   *
   * @param method 服务方法描述符
   * @return gRPC请求安全元数据源
   */
  public DescriptorGrpcSecurityMetadataSource remove(final MethodDescriptor<?, ?> method) {
    // 移除服务方法的访问权限
    this.accessMap.remove(method);
    return this;
  }

  /**
   * 设置默认访问权限
   *
   * @param method 访问方法
   * @return 默认访问权限配置集合
   */
  private Collection<ConfigAttribute> getAttributes(final MethodDescriptor<?, ?> method) {
    return this.accessMap.getOrDefault(method, this.defaultAttributes);
  }

  /**
   * 包装访问权限
   *
   * @param predicate 访问权限
   * @return 访问权限集合
   */
  private Collection<ConfigAttribute> wrap(final AccessPredicate predicate) {
    return of(new AccessPredicateConfigAttribute(predicate));
  }
}
