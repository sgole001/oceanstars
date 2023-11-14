package oceanstars.ecommerce.common.tools;


import java.security.AlgorithmParameters;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.text.MessageFormat;
import java.util.Base64;
import java.util.Base64.Decoder;
import java.util.Base64.Encoder;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import oceanstars.ecommerce.common.beans.AES256;
import oceanstars.ecommerce.common.beans.BaseCryptoBean;
import oceanstars.ecommerce.common.exception.SystemException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.context.properties.bind.Bindable;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * 对称加解密公共处理类
 *
 * @author Clover
 * @version 1.0.0
 * @since 2021/11/3 5:19 下午
 */
@Configuration
public class CryptoUtil {

  /**
   * 对称加解密算法信息Bean
   */
  private BaseCryptoBean cryptoBean;

  /**
   * 字符串可用字符范围
   */
  private static final String POSSIBLE_CHARS = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

  @Bean(name = "cryptoUtilForAes256")
  public CryptoUtil cryptoUtilForAes256() {
    this.cryptoBean = PropertyUtil.BINDER.bind(AES256.PREFIX, Bindable.of(AES256.class)).get();
    return this;
  }

  @Bean(name = "passwordEncoder")
  public BCryptPasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  /**
   * BASE64Encoder 加密
   *
   * @param data 要加密的数据
   * @return 加密后的字符串
   */
  public static String encryptBase64(byte[] data) {
    Encoder encoder = Base64.getEncoder();
    return encoder.encodeToString(data);
  }

  /**
   * BASE64Decoder 解密
   *
   * @param data 要解密的字符串
   * @return 解密后的byte[]
   */
  public static String decryptBase64(byte[] data) {
    Decoder decoder = Base64.getDecoder();
    byte[] buffer = decoder.decode(data);
    return new String(buffer);
  }

  /**
   * 加密处理
   *
   * @param target         加密对象
   * @param encodingFormat 编码格式
   * @param iv             CBC补充模式初始向量
   * @param keySpec        对称密钥
   * @return 加密后字符串
   * @throws Exception 处理异常
   */
  public String encrypt(String target, String encodingFormat, AlgorithmParameters iv, SecretKeySpec keySpec) throws Exception {

    Cipher cipher = Cipher.getInstance(this.buildCipher());
    cipher.init(Cipher.ENCRYPT_MODE, keySpec, iv);

    byte[] encrypted = cipher.doFinal(target.getBytes(encodingFormat));
    // 此处使用BASE64做转码。
    return encryptBase64(encrypted);
  }

  /**
   * 解密处理
   *
   * @param target         解密对象
   * @param encodingFormat 编码格式
   * @param iv             CBC补充模式初始向量
   * @param keySpec        对称密钥
   * @return 解密后字符串
   * @throws Exception 处理异常
   */
  public String decrypt(String target, String encodingFormat, AlgorithmParameters iv, SecretKeySpec keySpec) throws Exception {

    Cipher cipher = Cipher.getInstance(this.buildCipher());
    cipher.init(Cipher.DECRYPT_MODE, keySpec, iv);

    // 先用base64解密
    byte[] decrypted = cipher.doFinal(decryptBase64(target.getBytes()).getBytes());

    return new String(decrypted, encodingFormat);
  }

  /**
   * 密钥生成处理
   *
   * @param key 密钥生成参数
   * @return 对称密钥
   * @throws NoSuchAlgorithmException 算法缺失异常
   */
  public SecretKeySpec generateKey(String key) throws NoSuchAlgorithmException {

    // "AES"：请求的密钥算法的标准名称
    KeyGenerator keyGenerator = KeyGenerator.getInstance(this.cryptoBean.getAlgorithm());

    // 256：密钥生成参数；secure random：密钥生成器的随机源
    SecureRandom securerandom = new SecureRandom(this.digestKey(key));

    keyGenerator.init(this.cryptoBean.getKeyDigit(), securerandom);

    // 生成秘密（对称）密钥
    SecretKey secretKey = keyGenerator.generateKey();

    // 返回基本编码格式的密钥
    byte[] enCodeFormat = secretKey.getEncoded();

    // 根据给定的字节数组构造一个密钥。enCodeFormat：密钥内容；"AES"：与给定的密钥内容相关联的密钥算法的名称
    return new SecretKeySpec(enCodeFormat, this.cryptoBean.getAlgorithm());
  }

  /**
   * 初始向量生成处理
   *
   * @return 初始向量
   * @throws Exception 处理异常
   */
  public AlgorithmParameters generateIv() throws Exception {

    // AES加密参数（初始向量）
    AlgorithmParameters params = AlgorithmParameters.getInstance(this.cryptoBean.getAlgorithm());

    params.init(new IvParameterSpec(this.generateRandomString(this.cryptoBean.getIvDigit())));

    return params;
  }

  /**
   * 对称加密密钥摘要处理
   *
   * @param key 对称加密密钥
   * @return 对称加密密钥摘要
   */
  public byte[] digestKey(String key) {
    try {
      // 创建消息摘要算法
      MessageDigest digester = MessageDigest.getInstance(this.cryptoBean.getDigest());
      // 数据摘要处理
      digester.update(key.getBytes());

      return digester.digest();
    } catch (NoSuchAlgorithmException e) {
      throw new SystemException(e.getMessage());
    }
  }

  /**
   * 指定长度随机字符串生成处理
   *
   * @param length 字符串长度
   * @return 指定长度随机字符串
   */
  private byte[] generateRandomString(Integer length) {
    StringBuilder randomStr = new StringBuilder(length);
    SecureRandom random = new SecureRandom();
    for (int i = 0; i < length; i++) {
      randomStr.append(POSSIBLE_CHARS.charAt(random.nextInt(POSSIBLE_CHARS.length())));
    }
    return randomStr.toString().getBytes();
  }

  /**
   * 构建加解密算法模式（算法/模式/填充）
   *
   * @return 算法模式
   */
  private String buildCipher() {

    // 对称加解密算法
    String algorithm = this.cryptoBean.getAlgorithm();
    // 对称加解密模式
    String mode = this.cryptoBean.getMode();
    // 对称加解密填充
    String padding = this.cryptoBean.getPadding();

    if (StringUtils.isNotEmpty(algorithm) && StringUtils.isNotEmpty(mode) && StringUtils
        .isNotEmpty(padding)) {
      return MessageFormat.format("{0}/{1}/{2}", algorithm, mode, padding);
    }

    return algorithm;
  }
}
