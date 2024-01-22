package oceanstars.ecommerce.infrastructure.grpc.security.server.authentication;

import io.grpc.Grpc;
import io.grpc.Metadata;
import io.grpc.ServerCall;
import jakarta.annotation.Nullable;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import javax.net.ssl.SSLPeerUnverifiedException;
import javax.net.ssl.SSLSession;
import org.springframework.security.core.Authentication;

/**
 * SSL上下文gRPC认证读取器
 *
 * @author Clover
 * @version 1.0.0
 * @since 2024/1/21 11:00
 */
public class SSLContextGrpcAuthenticationReader implements GrpcAuthenticationReader {

  @Override
  public Authentication readAuthentication(final ServerCall<?, ?> call, final Metadata metadata) {

    // 从gRPC调用中获取SSL会话
    final SSLSession sslSession = call.getAttributes().get(Grpc.TRANSPORT_ATTR_SSL_SESSION);
    // 如果SSL会话为空，则返回空
    if (sslSession == null) {
      return null;
    }

    // 从SSL会话中获取证书
    Certificate[] certs;
    try {
      certs = sslSession.getPeerCertificates();
    } catch (final SSLPeerUnverifiedException e) {
      return null;
    }

    // 从证书中获取认证信息
    return fromCertificate(certs[certs.length - 1]);
  }

  /**
   * 从证书中获取认证信息
   *
   * @param cert 证书
   * @return 认证信息
   */
  @Nullable
  protected Authentication fromCertificate(final Certificate cert) {

    // 如果证书是X509证书，则创建X509证书认证
    if (cert instanceof X509Certificate) {
      return new X509CertificateAuthentication((X509Certificate) cert);
    } else {
      return null;
    }
  }
}
