package oceanstars.ecommerce.common.security;

import java.time.LocalDateTime;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * JWT配置信息
 *
 * @author Clover
 * @version 1.0.0
 * @since 2024/7/22 14:46
 */
public class JwtProperties extends TokenProperties {

  /**
   * JWT签发者
   */
  private String iss;

  /**
   * JWT所面向的用户
   */
  private String sub;

  /**
   * 接收JWT的一方
   */
  private String aud;

  /**
   * 定义在什么时间之前，该JWT都是不可用的
   */
  @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private LocalDateTime nbf;

  /**
   * JWT的签发时间
   */
  @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private LocalDateTime iat;

  /**
   * JWT的唯一身份标识(主要用来作为一次性token,从而回避重放攻击)
   */
  private String id;

  /**
   * JWT签名密钥
   */
  private String sign;

  /**
   * JWT加密密钥
   */
  private String encrypt;

  public String getIss() {
    return iss;
  }

  public void setIss(String iss) {
    this.iss = iss;
  }

  public String getSub() {
    return sub;
  }

  public void setSub(String sub) {
    this.sub = sub;
  }

  public String getAud() {
    return aud;
  }

  public void setAud(String aud) {
    this.aud = aud;
  }

  public LocalDateTime getNbf() {
    return nbf;
  }

  public void setNbf(LocalDateTime nbf) {
    this.nbf = nbf;
  }

  public LocalDateTime getIat() {
    return iat;
  }

  public void setIat(LocalDateTime iat) {
    this.iat = iat;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getSign() {
    return sign;
  }

  public void setSign(String sign) {
    this.sign = sign;
  }

  public String getEncrypt() {
    return encrypt;
  }

  public void setEncrypt(String encrypt) {
    this.encrypt = encrypt;
  }
}
