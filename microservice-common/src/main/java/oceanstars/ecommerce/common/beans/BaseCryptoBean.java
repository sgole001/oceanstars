package oceanstars.ecommerce.common.beans;

/**
 * 对称加解密算法信息Bean
 *
 * @author Clover
 * @version 1.0.0
 * @since 2021/11/3 3:26 下午
 */
public abstract class BaseCryptoBean {

  /**
   * 对称加密-算法
   */
  private String algorithm;

  /**
   * 对称加密-模式
   */
  private String mode;

  /**
   * 对称加密-填充
   */
  private String padding;

  /**
   * 摘要算法
   */
  private String digest;

  /**
   * 密钥位数
   */
  private int keyDigit;

  /**
   * 初始向量位数
   */
  private int ivDigit;

  public String getAlgorithm() {
    return algorithm;
  }

  public void setAlgorithm(String algorithm) {
    this.algorithm = algorithm;
  }

  public String getMode() {
    return mode;
  }

  public void setMode(String mode) {
    this.mode = mode;
  }

  public String getPadding() {
    return padding;
  }

  public void setPadding(String padding) {
    this.padding = padding;
  }

  public String getDigest() {
    return digest;
  }

  public void setDigest(String digest) {
    this.digest = digest;
  }

  public int getKeyDigit() {
    return keyDigit;
  }

  public void setKeyDigit(int keyDigit) {
    this.keyDigit = keyDigit;
  }

  public int getIvDigit() {
    return ivDigit;
  }

  public void setIvDigit(int ivDigit) {
    this.ivDigit = ivDigit;
  }
}
