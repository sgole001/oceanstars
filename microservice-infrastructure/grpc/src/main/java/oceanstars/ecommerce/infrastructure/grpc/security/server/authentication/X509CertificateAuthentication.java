package oceanstars.ecommerce.infrastructure.grpc.security.server.authentication;

import java.io.Serial;
import java.security.cert.X509Certificate;
import java.util.Collection;
import java.util.Collections;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

/**
 * X509证书认证
 *
 * @author Clover
 * @version 1.0.0
 * @since 2024/1/21 11:04
 */
public class X509CertificateAuthentication extends AbstractAuthenticationToken {

  @Serial
  private static final long serialVersionUID = -5413233096738172820L;

  /**
   * 用户代表
   */
  private final Object principal;

  /**
   * X509证书
   */
  private X509Certificate certificate;

  /**
   * 构造函数
   *
   * @param certificate X509证书
   */
  public X509CertificateAuthentication(final X509Certificate certificate) {
    super(Collections.emptyList());
    this.principal = certificate.getSubjectX500Principal();
    this.certificate = certificate;
    setAuthenticated(false);
  }

  /**
   * 构造函数
   *
   * @param principal   用户代表
   * @param certificate X509证书
   * @param authorities 权限列表集合
   */
  public X509CertificateAuthentication(final Object principal, final X509Certificate certificate,
      final Collection<? extends GrantedAuthority> authorities) {
    super(authorities);
    this.principal = principal;
    this.certificate = certificate;
    super.setAuthenticated(true);
  }

  @Override
  public Object getPrincipal() {
    return this.principal;
  }

  @Override
  public X509Certificate getCredentials() {
    return this.certificate;
  }

  @Override
  public void eraseCredentials() {
    this.certificate = null;
    super.eraseCredentials();
  }

  @Override
  public void setAuthenticated(final boolean authenticated) {
    if (authenticated) {
      throw new IllegalArgumentException("无法将此令牌设置为受信任 - 请使用包含授权的构造函数!");
    }
    super.setAuthenticated(false);
  }
}
