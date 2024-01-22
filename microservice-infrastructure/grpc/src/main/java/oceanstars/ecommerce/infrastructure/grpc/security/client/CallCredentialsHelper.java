package oceanstars.ecommerce.infrastructure.grpc.security.client;

import static java.nio.charset.StandardCharsets.UTF_8;
import static oceanstars.ecommerce.infrastructure.grpc.security.SecurityConstants.AUTHORIZATION_HEADER;
import static oceanstars.ecommerce.infrastructure.grpc.security.SecurityConstants.BASIC_AUTH_PREFIX;
import static oceanstars.ecommerce.infrastructure.grpc.security.SecurityConstants.BEARER_AUTH_PREFIX;

import io.grpc.CallCredentials;
import io.grpc.Metadata;
import io.grpc.SecurityLevel;
import io.grpc.Status;
import io.grpc.stub.AbstractStub;
import jakarta.annotation.Nullable;
import java.util.Base64;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.function.Supplier;
import oceanstars.ecommerce.infrastructure.grpc.service.consumer.StubTransformer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * gRPC客户端调用凭据Helper
 *
 * @author Clover
 * @version 1.0.0
 * @since 2024/1/19 16:04
 */
public class CallCredentialsHelper {

  /**
   * 日志管理器
   */
  private final Logger logger = LogManager.getLogger(CallCredentialsHelper.class.getName());

  private CallCredentialsHelper() {
  }

  /**
   * 创建StubTransformer，用于将CallCredentials应用于所有Stub
   *
   * @param credentials 认证凭据接口
   * @return StubTransformer
   * @see AbstractStub#withCallCredentials(CallCredentials)
   */
  public static StubTransformer fixedCredentialsStubTransformer(final CallCredentials credentials) {
    return (name, stub) -> stub.withCallCredentials(credentials);
  }

  /**
   * 创建一个新的{@link StubTransformer}，将凭据分配给给定的{@link AbstractStub}，如果给定映射不包含给定名称的值，则调用凭据将被省略。
   *
   * @param credentialsByName 凭据映射名称
   * @return StubTransformer
   */
  public static StubTransformer mappedCredentialsStubTransformer(final Map<String, CallCredentials> credentialsByName) {
    return mappedCredentialsStubTransformer(credentialsByName, null);
  }

  /**
   * 创建一个新的{@link StubTransformer}，将凭据分配给给定的{@link AbstractStub}，如果给定映射不包含给定名称的值，则调用凭据将被省略。
   *
   * @param credentialsByName 凭据映射名称
   * @param fallback          默认认证凭据接口
   * @return StubTransformer
   */
  public static StubTransformer mappedCredentialsStubTransformer(final Map<String, CallCredentials> credentialsByName,
      @Nullable final CallCredentials fallback) {

    return (name, stub) -> {
      // 获取认证凭据接口
      final CallCredentials credentials = credentialsByName.getOrDefault(name, fallback);
      if (credentials == null) {
        return stub;
      } else {
        // 将凭据分配给给定的Stub
        return stub.withCallCredentials(credentials);
      }
    };
  }

  /**
   * 使用给定的令牌为承载身份验证创建新的调用凭据
   *
   * <p>
   * 如果您有一个永久令牌，或者在令牌有效时仅对单个呼叫使用呼叫凭据，请使用此方法。
   * <p/>
   *
   * @param token 令牌
   * @return CallCredentials
   */
  public static CallCredentials bearerAuth(final String token) {
    return authorizationHeader(BEARER_AUTH_PREFIX + token);
  }

  /**
   * 使用给定的令牌为承载身份验证创建新的调用凭据
   *
   * <p>
   * 如果您从活动上下文(例如，当前登录的用户)派生令牌或从身份验证服务器动态获取令牌，则使用此方法。
   * <p/>
   *
   * @param tokenSource 令牌源
   * @return CallCredentials
   */
  public static CallCredentials bearerAuth(final Supplier<String> tokenSource) {
    return authorizationHeader(() -> BEARER_AUTH_PREFIX + tokenSource.get());
  }

  /**
   * 使用给定的账号名和密码为基本身份验证创建新的调用凭据
   *
   * @param username 账号名
   * @param password 账号密码
   * @return CallCredentials
   */
  public static CallCredentials basicAuth(final String username, final String password) {
    return authorizationHeader(encodeBasicAuth(username, password));
  }

  /**
   * 将给定的账号名和密码进行Base64编码后最为基础认证。
   *
   * @param username 账号名
   * @param password 账号密码
   * @return Base64编码
   */
  public static String encodeBasicAuth(final String username, final String password) {
    // 构建原始基础认证
    final String auth = username + ':' + password;
    try {
      // 构建基础认证Token
      return BASIC_AUTH_PREFIX + new String(Base64.getEncoder().encode(auth.getBytes(UTF_8)), UTF_8);
    } catch (final IllegalArgumentException e) {
      throw new IllegalArgumentException("基础认证Token编码失败！", e);
    }
  }

  /**
   * 请求头设置授权信息
   *
   * @param authorization 完整授权信息（Basic授权Token | Bearer授权Token）
   * @return CallCredentials
   */
  public static CallCredentials authorizationHeader(final String authorization) {
    final Metadata extraHeaders = new Metadata();
    extraHeaders.put(AUTHORIZATION_HEADER, authorization);
    return authorizationHeaders(extraHeaders);
  }

  /**
   * 请求头设置授权信息
   *
   * @param authorizationSource 完整授权信息源（Basic授权Token | Bearer授权Token）
   * @return CallCredentials
   */
  public static CallCredentials authorizationHeader(final Supplier<String> authorizationSource) {
    return authorizationHeaders(() -> {
      final Metadata extraHeaders = new Metadata();
      extraHeaders.put(AUTHORIZATION_HEADER, authorizationSource.get());
      return extraHeaders;
    });
  }

  /**
   * 请求头设置授权信息
   *
   * @param authorizationHeaders 请求头授权元数据
   * @return CallCredentials
   */
  public static CallCredentials authorizationHeaders(final Metadata authorizationHeaders) {
    return new StaticSecurityHeaderCallCredentials(authorizationHeaders);
  }

  /**
   * 静态安全头调用凭据
   *
   * @author Clover
   * @version 1.0.0
   * @since 2024/1/19 19:12
   */
  private static final class StaticSecurityHeaderCallCredentials extends CallCredentials {

    private final Metadata extraHeaders;

    StaticSecurityHeaderCallCredentials(final Metadata authorizationHeaders) {
      this.extraHeaders = authorizationHeaders;
    }

    @Override
    public void applyRequestMetadata(final RequestInfo requestInfo, final Executor appExecutor,
        final MetadataApplier applier) {
      applier.apply(this.extraHeaders);
    }

    @Override
    public String toString() {
      return "StaticSecurityHeaderCallCredentials [extraHeaders.keys=" + this.extraHeaders.keys() + "]";
    }
  }

  /**
   * 请求头设置授权信息
   *
   * @param authorizationHeadersSupplier 请求头授权元数据源
   * @return CallCredentials
   */
  public static CallCredentials authorizationHeaders(final Supplier<Metadata> authorizationHeadersSupplier) {
    return new DynamicSecurityHeaderCallCredentials(authorizationHeadersSupplier);
  }

  /**
   * 动态安全头调用凭据
   *
   * @author Clover
   * @version 1.0.0
   * @since 2024/1/19 19:14
   */
  private static final class DynamicSecurityHeaderCallCredentials extends CallCredentials {

    private final Supplier<Metadata> extraHeadersSupplier;

    DynamicSecurityHeaderCallCredentials(final Supplier<Metadata> authorizationHeadersSupplier) {
      this.extraHeadersSupplier = authorizationHeadersSupplier;
    }

    @Override
    public void applyRequestMetadata(final RequestInfo requestInfo, final Executor appExecutor,
        final MetadataApplier applier) {
      applier.apply(this.extraHeadersSupplier.get());
    }

    @Override
    public String toString() {
      return "DynamicSecurityHeaderCallCredentials [extraHeadersSupplier=" + this.extraHeadersSupplier + "]";
    }
  }

  /**
   * 检查给定的安全级别是否为连接上发送的所有数据提供隐私。
   *
   * @param securityLevel 安全级别
   * @return 检查结果 true:满足机密性&完整性
   */
  public static boolean isPrivacyGuaranteed(final SecurityLevel securityLevel) {
    return SecurityLevel.PRIVACY_AND_INTEGRITY == securityLevel;
  }

  /**
   * 创建一个新的调用凭据，它将给定的凭据应用于安全级别为PRIVACY_AND_INTEGRITY的连接。
   * <p>
   * 如果连接不是安全的，它将导致失败。
   * <p/>
   *
   * @param callCredentials 调用凭据
   * @return CallCredentials
   */
  public static CallCredentials requirePrivacy(final CallCredentials callCredentials) {
    return new RequirePrivacyCallCredentials(callCredentials);
  }

  /**
   * 增加了安全性要求的调用凭据实现。
   *
   * <p>它确保凭据和请求不是通过不安全的连接发送的。此包装器对底层实现的安全性没有任何其他影响。<p/>
   *
   * @author Clover
   * @version 1.0.0
   * @since 2024/1/19 19:20
   */
  private static final class RequirePrivacyCallCredentials extends CallCredentials {

    private static final Status STATUS_LACKING_PRIVACY = Status.UNAUTHENTICATED.withDescription("连接安全级别不确保凭证隐私");

    private final CallCredentials callCredentials;

    RequirePrivacyCallCredentials(final CallCredentials callCredentials) {
      this.callCredentials = callCredentials;
    }

    /**
     * gRPC 发送请求之前被调用，用于向请求头中添加元数据
     *
     * @param requestInfo 请求信息
     * @param appExecutor 应用执行器
     * @param applier     元数据应用器
     */
    @Override
    public void applyRequestMetadata(final RequestInfo requestInfo, final Executor appExecutor,
        final MetadataApplier applier) {
      // 当gRPC的请求安全界别为 PRIVACY_AND_INTEGRITY 时，才会将凭据应用于请求头中
      if (isPrivacyGuaranteed(requestInfo.getSecurityLevel())) {
        this.callCredentials.applyRequestMetadata(requestInfo, appExecutor, applier);
      } else {
        applier.fail(STATUS_LACKING_PRIVACY);
      }
    }

    @Override
    public String toString() {
      return "RequirePrivacyCallCredentials [callCredentials=" + this.callCredentials + "]";
    }
  }

  /**
   * 创建一个新的调用凭据，它将给定的凭据应用于安全级别为PRIVACY_AND_INTEGRITY的连接。
   * <p>
   * 如果连接不是安全的，它将忽略凭据。
   * <p/>
   *
   * @param callCredentials 调用凭据
   * @return CallCredentials
   */
  public static CallCredentials includeWhenPrivate(final CallCredentials callCredentials) {
    return new IncludeWhenPrivateCallCredentials(callCredentials);
  }

  /**
   * 增加了安全性要求的调用凭据实现。
   *
   * <p>它确保凭据不是通过不安全的连接发送的。但是，它不阻止通过不安全连接的请求。此包装器对底层实现的安全性没有任何其他影响。<p/>
   *
   * @author Clover
   * @version 1.0.0
   * @since 2024/1/19 19:24
   */
  private static final class IncludeWhenPrivateCallCredentials extends CallCredentials {

    private final CallCredentials callCredentials;

    IncludeWhenPrivateCallCredentials(final CallCredentials callCredentials) {
      this.callCredentials = callCredentials;
    }

    /**
     * gRPC 发送请求之前被调用，用于向请求头中添加元数据
     *
     * @param requestInfo 请求信息
     * @param appExecutor 应用执行器
     * @param applier     元数据应用器
     */
    @Override
    public void applyRequestMetadata(final RequestInfo requestInfo, final Executor appExecutor,
        final MetadataApplier applier) {
      // 当gRPC的请求安全界别为 PRIVACY_AND_INTEGRITY 时，才会将凭据应用于请求头中
      if (isPrivacyGuaranteed(requestInfo.getSecurityLevel())) {
        this.callCredentials.applyRequestMetadata(requestInfo, appExecutor, applier);
      }
    }

    @Override
    public String toString() {
      return "IncludeWhenPrivateCallCredentials [callCredentials=" + this.callCredentials + "]";
    }
  }
}
