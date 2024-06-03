package oceanstars.ecommerce.common.security;

import java.util.Date;

/**
 * JWT信息Bean
 *
 * @author Clover
 * @version 1.0.0
 * @since 2021/11/3 3:29 下午
 */
public class JwtPayload {

  /**
   * 配置前缀常量
   */
  public interface Prefix {

    /**
     * 资源访问令牌配置前缀
     */
    String ACCESS_TOKEN = "jwt.access";

    /**
     * 资源访问令牌刷新用令牌配置前缀
     */
    String REFRESH_TOKEN = "jwt.refresh";

  }

  /**
   * 额外自定义信息Key
   */
  public interface CustomClaims {

    /**
     * 用户ID
     */
    String CLAIM_USER_ID = "userId";

    /**
     * 桌号ID
     */
    String CLAIM_TABLE_ID = "tableId";

    /**
     * 房号ID
     */
    String CLAIM_ROOM_ID = "roomId";
  }

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
   * JWT的过期时间，这个过期时间必须要大于签发时间
   */
  private Long exp;

  /**
   * 定义在什么时间之前，该JWT都是不可用的
   */
  private Long nbf;

  /**
   * JWT的签发时间
   */
  private Date iat;

  /**
   * JWT的唯一身份标识(主要用来作为一次性token,从而回避重放攻击)
   */
  private String id;

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

  public Long getExp() {
    return exp;
  }

  public void setExp(Long exp) {
    this.exp = exp;
  }

  public Long getNbf() {
    return nbf;
  }

  public void setNbf(Long nbf) {
    this.nbf = nbf;
  }

  public Date getIat() {
    return iat;
  }

  public void setIat(Date iat) {
    this.iat = iat;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }
}
