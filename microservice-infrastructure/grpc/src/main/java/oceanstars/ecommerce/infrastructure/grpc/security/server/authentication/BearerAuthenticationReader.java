package oceanstars.ecommerce.infrastructure.grpc.security.server.authentication;

import static oceanstars.ecommerce.infrastructure.grpc.security.SecurityConstants.AUTHORIZATION_HEADER;
import static oceanstars.ecommerce.infrastructure.grpc.security.SecurityConstants.BEARER_AUTH_PREFIX;

import io.grpc.Metadata;
import io.grpc.ServerCall;
import java.util.function.Function;
import org.springframework.security.core.Authentication;

/**
 * Bearer认证读取器
 *
 * @author Clover
 * @version 1.0.0
 * @since 2024/1/21 10:44
 */
public class BearerAuthenticationReader implements GrpcAuthenticationReader {

  /**
   * Bearer认证前缀
   */
  private static final String PREFIX = BEARER_AUTH_PREFIX.toLowerCase();

  /**
   * Bearer认证前缀长度
   */
  private static final int PREFIX_LENGTH = PREFIX.length();

  /**
   * Token包装器
   */
  private final Function<String, Authentication> tokenWrapper;

  /**
   * 构造函数
   *
   * @param tokenWrapper Token包装器
   */
  public BearerAuthenticationReader(Function<String, Authentication> tokenWrapper) {
    this.tokenWrapper = tokenWrapper;
  }

  @Override
  public Authentication readAuthentication(final ServerCall<?, ?> call, final Metadata headers) {

    // 从gRPC元数据中获取认证头
    final String header = headers.get(AUTHORIZATION_HEADER);

    // 如果认证头为空或者不是以Bearer认证前缀开头，则返回空
    if (header == null || !header.toLowerCase().startsWith(PREFIX)) {
      return null;
    }

    // 提取并解码认证头
    final String accessToken = header.substring(PREFIX_LENGTH);

    // 创建Bearer认证
    return tokenWrapper.apply(accessToken);
  }
}