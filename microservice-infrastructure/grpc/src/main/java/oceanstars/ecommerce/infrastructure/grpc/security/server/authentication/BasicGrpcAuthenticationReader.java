package oceanstars.ecommerce.infrastructure.grpc.security.server.authentication;

import static java.nio.charset.StandardCharsets.UTF_8;
import static oceanstars.ecommerce.infrastructure.grpc.security.SecurityConstants.AUTHORIZATION_HEADER;
import static oceanstars.ecommerce.infrastructure.grpc.security.SecurityConstants.BASIC_AUTH_PREFIX;

import io.grpc.Metadata;
import io.grpc.ServerCall;
import java.util.Base64;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

/**
 * 基本认证读取器
 *
 * @author Clover
 * @version 1.0.0
 * @since 2024/1/21 10:43
 */
public class BasicGrpcAuthenticationReader implements GrpcAuthenticationReader {

  /**
   * 基本认证前缀
   */
  private static final String PREFIX = BASIC_AUTH_PREFIX.toLowerCase();

  /**
   * 基本认证前缀长度
   */
  private static final int PREFIX_LENGTH = PREFIX.length();

  @Override
  public Authentication readAuthentication(final ServerCall<?, ?> call, final Metadata headers) throws AuthenticationException {

    // 从gRPC元数据中获取认证头
    final String header = headers.get(AUTHORIZATION_HEADER);
    // 如果认证头为空或者不是以基本认证前缀开头，则返回空
    if (header == null || !header.toLowerCase().startsWith(PREFIX)) {
      return null;
    }
    // 提取并解码认证头
    final String[] decoded = extractAndDecodeHeader(header);
    // 创建基本认证令牌
    return new UsernamePasswordAuthenticationToken(decoded[0], decoded[1]);
  }

  /**
   * 提取并解码认证头
   *
   * @param header 认证头
   * @return 解码后的认证头
   */
  private String[] extractAndDecodeHeader(final String header) {

    // 获取认证头的Base64编码的Token
    final byte[] base64Token = header.substring(PREFIX_LENGTH).getBytes(UTF_8);

    // 解码Base64编码的Token
    byte[] decoded;
    try {
      decoded = Base64.getDecoder().decode(base64Token);
    } catch (final IllegalArgumentException e) {
      throw new BadCredentialsException("基础认证令牌解码失败！", e);
    }

    // 将解码后的Token转换为字符串
    final String token = new String(decoded, UTF_8);
    // 如果Token中不包含冒号，则抛出异常
    final int delim = token.indexOf(':');
    if (delim == -1) {
      throw new BadCredentialsException("无效的基本认证令牌！");
    }

    // 返回用户名和密码
    return new String[]{token.substring(0, delim), token.substring(delim + 1)};
  }
}
