package oceanstars.ecommerce.common.tools;

import com.google.common.collect.ImmutableMap;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.util.Map;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.TrustManagerFactory;
import org.springframework.core.io.Resource;

/**
 * KeyStore工具类
 *
 * @author Clover
 * @version 1.0.0
 * @since 2024/1/19 15:02
 */
public class KeyStoreUtil {

  /**
   * 自动检测keystore格式。
   */
  public static final String FORMAT_AUTODETECT = "AUTODETECT";

  /**
   * 默认keystore格式。
   */
  public static final String FORMAT_FALLBACK = KeyStore.getDefaultType();

  /**
   * keystore格式映射
   */
  private static final Map<String, String> FORMAT_MAPPING = ImmutableMap.<String, String>builder()
      .put("jks", "JKS")
      .put("p12", "PKCS12")
      .put("pfx", "PKCS12")
      .build();

  /**
   * 构造函数
   */
  private KeyStoreUtil() {
  }

  /**
   * 检测keystore格式
   *
   * @param format keystore格式
   * @param name   keystore文件名
   * @return keystore格式
   */
  public static String detectFormat(final String format, final String name) {
    if (FORMAT_AUTODETECT.equals(format)) {
      if (name == null) {
        return FORMAT_FALLBACK;
      }
      final int index = name.lastIndexOf('.');
      if (index == -1) {
        return FORMAT_FALLBACK;
      } else {
        final String ending = name.substring(index + 1).toLowerCase();
        return FORMAT_MAPPING.getOrDefault(ending, FORMAT_FALLBACK);
      }
    } else {
      return format;
    }
  }

  /**
   * 加载密钥管理工厂
   *
   * @param keyStoreFormat   keystore格式
   * @param keyStore         keystore文件
   * @param keyStorePassword keystore密码
   * @return keystore密码
   * @throws KeyStoreException         keystore异常
   * @throws IOException               IO异常
   * @throws NoSuchAlgorithmException  未知算法异常
   * @throws CertificateException      证书异常
   * @throws UnrecoverableKeyException 不可恢复的密钥异常
   */
  public static KeyManagerFactory loadKeyManagerFactory(final String keyStoreFormat, final Resource keyStore, final String keyStorePassword)
      throws KeyStoreException, IOException, NoSuchAlgorithmException, CertificateException, UnrecoverableKeyException {

    // 加载keystore
    final KeyStore ks = loadKeyStore(keyStoreFormat, keyStore, keyStorePassword);
    // 获取密钥管理工厂加密算法
    final String keyManagerAlgorithm = KeyManagerFactory.getDefaultAlgorithm();
    // 实例化密钥管理工厂
    final KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance(keyManagerAlgorithm);
    // 初始化密钥管理工厂
    keyManagerFactory.init(ks, keystorePassword(keyStorePassword));

    return keyManagerFactory;
  }

  /**
   * 加载信任证书管理工厂
   *
   * @param trustStoreFormat   信任证书格式
   * @param trustStore         信任证书文件
   * @param trustStorePassword 信任证书密码
   * @return 信任证书管理工厂
   * @throws KeyStoreException        keystore异常
   * @throws IOException              IO异常
   * @throws NoSuchAlgorithmException 未知算法异常
   * @throws CertificateException     证书异常
   */
  public static TrustManagerFactory loadTrustManagerFactory(final String trustStoreFormat, final Resource trustStore, final String trustStorePassword)
      throws KeyStoreException, IOException, NoSuchAlgorithmException, CertificateException {

    // 加载keystore
    final KeyStore keyStore = loadKeyStore(trustStoreFormat, trustStore, trustStorePassword);
    // 获取信任证书管理工厂加密算法
    final String trustManagerAlgorithm = TrustManagerFactory.getDefaultAlgorithm();
    // 实例化信任证书管理工厂
    final TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(trustManagerAlgorithm);
    // 初始化信任证书管理工厂
    trustManagerFactory.init(keyStore);

    return trustManagerFactory;
  }

  /**
   * 加载keystore
   *
   * @param keyStoreFormat   keystore格式
   * @param keyStore         keystore文件
   * @param keyStorePassword keystore密码
   * @return keystore
   * @throws KeyStoreException        keystore异常
   * @throws IOException              IO异常
   * @throws NoSuchAlgorithmException 未知算法异常
   * @throws CertificateException     证书异常
   */
  private static KeyStore loadKeyStore(final String keyStoreFormat, final Resource keyStore, final String keyStorePassword)
      throws KeyStoreException, IOException, NoSuchAlgorithmException, CertificateException {

    // 检测keystore格式
    final String detectedFormat = detectFormat(keyStoreFormat, keyStore.getFilename());
    // 获取keystore实例
    final KeyStore ks = KeyStore.getInstance(detectedFormat);
    // 加载keystore
    try (InputStream stream = keyStore.getInputStream()) {
      ks.load(stream, keystorePassword(keyStorePassword));
    }

    return ks;
  }

  /**
   * 序列化keystore密码
   *
   * @param password keystore密码
   * @return 序列化后keystore密码
   */
  private static char[] keystorePassword(final String password) {
    if (password == null) {
      return new char[0];
    } else {
      return password.toCharArray();
    }
  }
}
