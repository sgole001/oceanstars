package oceanstars.ecommerce.infrastructure.grpc.security.server.authentication;

import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.security.auth.x500.X500Principal;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * X509证书认证提供者
 *
 * @author Clover
 * @version 1.0.0
 * @since 2024/1/21 11:05
 */
public class X509CertificateAuthenticationProvider implements AuthenticationProvider {

  /**
   * 用户名提取函数
   */
  public static final Function<Authentication, String> PRINCIPAL_USERNAME_EXTRACTOR = Authentication::getName;

  /**
   * CN用户名提取函数
   */
  public static final Function<X509CertificateAuthentication, String> CN_USERNAME_EXTRACTOR = patternExtractor("CN", PRINCIPAL_USERNAME_EXTRACTOR);

  /**
   * 失败回退函数
   */
  public static final Function<Authentication, String> FAIL_FALLBACK = authentication -> null;

  /**
   * 正则表达式模式(匹配形如 key=value 的字符串)提取函数
   *
   * @param key      键
   * @param fallback 失败回退函数
   * @return 正则表达式模式提取函数
   */
  public static Function<X509CertificateAuthentication, String> patternExtractor(final String key,
      final Function<? super X509CertificateAuthentication, String> fallback) {

    // 正则表达式模式，匹配形如 key=value 的字符串，并将 value 部分捕获到匹配组中
    final Pattern pattern = Pattern.compile(key + "=(.+?)(?:,|$)", Pattern.CASE_INSENSITIVE);

    // 返回一个函数，该函数从 X509CertificateAuthentication 中提取指定的 key 的值
    return authentication -> {

      // 从 X509CertificateAuthentication 中获取 principal
      final Object principal = authentication.getPrincipal();
      // 如果 principal 是 X500Principal 类型，则进行匹配
      if (principal instanceof X500Principal x500Principal) {
        // 使用正则表达式模式匹配 principal 的名称
        final Matcher matcher = pattern.matcher(x500Principal.getName());
        // 如果匹配成功，则返回匹配组中的第一个值
        if (matcher.find()) {
          return matcher.group(1);
        }
      }
      // 如果匹配失败，则返回 fallback 函数的结果
      return fallback.apply(authentication);
    };
  }

  /**
   * X509证书认证用户名提取函数
   */
  private final Function<? super X509CertificateAuthentication, String> usernameExtractor;

  /**
   * 用户详细信息服务
   */
  private final UserDetailsService userDetailsService;

  /**
   * 构造函数
   *
   * @param userDetailsService 用户详细信息服务
   */
  public X509CertificateAuthenticationProvider(final UserDetailsService userDetailsService) {
    this(CN_USERNAME_EXTRACTOR, userDetailsService);
  }

  /**
   * 构造函数
   *
   * @param usernameExtractor  X509证书认证用户名提取函数
   * @param userDetailsService 用户详细信息服务
   */
  public X509CertificateAuthenticationProvider(final Function<? super X509CertificateAuthentication, String> usernameExtractor,
      final UserDetailsService userDetailsService) {
    this.usernameExtractor = usernameExtractor;
    this.userDetailsService = userDetailsService;
  }

  @Override
  public Authentication authenticate(final Authentication authentication) throws AuthenticationException {

    // 如果 authentication 不是 X509CertificateAuthentication 类型，则抛出异常
    if (!(authentication instanceof X509CertificateAuthentication auth)) {
      throw new IllegalArgumentException("不支持的身份验证类型: " + authentication.getClass().getName() + "。 只支持X509证书的身份验证!");
    }

    // 从 X509CertificateAuthentication 中提取用户名
    final String username = this.usernameExtractor.apply(auth);
    // 如果用户名为空，则抛出异常
    if (username == null) {
      throw new UsernameNotFoundException("未提供用户名!");
    }

    // 从用户详细信息服务中加载用户详细信息
    final UserDetails user = this.userDetailsService.loadUserByUsername(username);
    // 如果用户详细信息为空，则抛出异常
    if (user == null) {
      throw new UsernameNotFoundException("指定的用户名: [" + username + "]不存在!");
    }

    // 返回 X509CertificateAuthentication
    return new X509CertificateAuthentication(user, auth.getCredentials(), user.getAuthorities());
  }

  @Override
  public boolean supports(final Class<?> authentication) {
    // 如果 authentication 是 X509CertificateAuthentication 类型，则返回 true
    return X509CertificateAuthentication.class.isAssignableFrom(authentication);
  }
}
