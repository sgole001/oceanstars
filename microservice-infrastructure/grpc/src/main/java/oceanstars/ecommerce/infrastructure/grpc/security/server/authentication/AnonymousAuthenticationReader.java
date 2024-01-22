package oceanstars.ecommerce.infrastructure.grpc.security.server.authentication;

import io.grpc.Metadata;
import io.grpc.ServerCall;
import java.util.Collection;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;

/**
 * 匿名认证读取器
 *
 * @author Clover
 * @version 1.0.0
 * @since 2024/1/21 10:43
 */
public class AnonymousAuthenticationReader implements GrpcAuthenticationReader {

  /**
   * 识别Token用的键值
   */
  private final String key;

  /**
   * 匿名用户代表
   */
  private final Object principal;

  /**
   * 权限列表集合
   */
  private final Collection<? extends GrantedAuthority> authorities;

  /**
   * 日志管理器
   */
  private final Logger logger = LogManager.getLogger(AnonymousAuthenticationReader.class.getName());

  /**
   * 构造函数
   *
   * @param key 识别Token用的键值
   */
  public AnonymousAuthenticationReader(final String key) {
    this(key, "anonymousUser", AuthorityUtils.createAuthorityList("ROLE_ANONYMOUS"));
  }

  /**
   * 构造函数
   *
   * @param key         识别Token用的键值
   * @param principal   匿名用户代表
   * @param authorities 权限列表集合
   */
  public AnonymousAuthenticationReader(final String key, final Object principal,
      final Collection<? extends GrantedAuthority> authorities) {
    this.key = key;
    this.principal = principal;
    this.authorities = authorities;
  }

  @Override
  public Authentication readAuthentication(final ServerCall<?, ?> call, final Metadata headers) {
    return new AnonymousAuthenticationToken(this.key, this.principal, this.authorities);
  }
}
