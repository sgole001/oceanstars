package oceanstars.ecommerce.common.tools;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.MacAlgorithm;
import java.util.Date;
import java.util.UUID;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import oceanstars.ecommerce.common.constant.CommonMessageConstant;
import oceanstars.ecommerce.common.exception.SystemException;
import org.apache.commons.lang3.StringUtils;

/**
 * JWT工具类
 *
 * @author Clover
 * @version 1.0.0
 * @since 2021/11/3 5:59 下午
 */
public class JwtUtil {

  /**
   * 创建JWS(Signed JWT)
   *
   * @param claims jwt创建条件信息
   * @param key    签名用密钥
   * @return JWS
   */
  public static String createJws(Header header, Claims claims, SecretKey key) {

    // 签名加密算法(HMAC-SHA256)
    final MacAlgorithm signatureAlgorithm = Jwts.SIG.HS256;

    // JWT构建对象
    JwtBuilder builder = Jwts.builder();

    // JWT Header数据设定
    if (null != header) {
      builder = builder.header().add(header).and();
    }

    // JWT Payload数据设定
    builder = builder.claims().add(claims).and();

    // 获取JWT签发时间
    Date iat = claims.getIssuedAt();
    // 签发时间没有设置时，设置当前时间
    if (null == iat) {
      iat = new Date();
      builder = builder.issuedAt(iat);
    }
    // JWT的过期时间，这个过期时间必须要大于签发时间
    if (null != claims.getExpiration() && iat.after(claims.getExpiration())) {
      throw new SystemException(CommonMessageConstant.MSG_COM_00008, claims.getExpiration(), iat);
    }
    // JWT的唯一身份标识
    if (StringUtils.isBlank(claims.getId())) {
      builder = builder.id(UUID.randomUUID().toString());
    }

    // 签名
    builder.signWith(key, signatureAlgorithm);

    return builder.compact();
  }

  /**
   * 解析JWS（带校验）
   *
   * @param jws JWS
   * @param key 解析密钥
   * @return JWT解析后信息
   */
  public static Jws<Claims> parseJws(String jws, SecretKeySpec key) {
    return Jwts.parser().verifyWith(key).build().parseSignedClaims(jws);
  }
}
