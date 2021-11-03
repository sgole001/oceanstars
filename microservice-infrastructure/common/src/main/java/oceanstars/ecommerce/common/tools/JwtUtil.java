package oceanstars.ecommerce.common.tools;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Date;
import java.util.UUID;
import javax.crypto.spec.SecretKeySpec;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;

/**
 * JWT工具类
 *
 * @author Clover
 * @version 1.0.0
 * @since 2021/11/3 5:59 下午
 */
public class JwtUtil {

  /**
   * 创建JWT
   *
   * @param claims jwt创建条件信息
   * @param key    签名用密钥
   * @return JWT
   */
  public static String createJwt(Claims claims, SecretKeySpec key, Long exp, Long nbf) {

    // 签名加密算法(HMAC-SHA256)
    SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

    // JWT构建对象
    JwtBuilder builder = Jwts.builder();

    // JWT的签发时间
    if (claims.getIssuedAt() == null) {
      claims.setIssuedAt(new Date());

      // JWT的过期时间，这个过期时间必须要大于签发时间
      if (exp != null) {
        claims.setExpiration(DateUtils.addMilliseconds(claims.getIssuedAt(), exp.intValue()));
      }
      // 定义在什么时间之前，该JWT都是不可用的
      if (nbf != null) {
        claims.setNotBefore(DateUtils.addMilliseconds(claims.getIssuedAt(), nbf.intValue()));
      }
    }
    // JWT的唯一身份标识
    if (StringUtils.isBlank(claims.getId())) {
      claims.setId(UUID.randomUUID().toString());
    }

    builder.setClaims(claims);
    // 签名
    builder.signWith(signatureAlgorithm, key);

    return builder.compact();
  }

  /**
   * 解析JWT（带校验）
   *
   * @param jwt JWT
   * @param key 解析密钥
   * @return JWT解析后信息
   */
  public static Claims parseJwt(String jwt, SecretKeySpec key) {
    return Jwts.parser().setSigningKey(key).parseClaimsJws(jwt).getBody();
  }

  /**
   * 解析JWT（非校验）
   *
   * @param jwt JWT
   * @return JWT解析后信息
   */
  public static Claims parseJwt(String jwt) {
    return Jwts.parser().parseClaimsJws(jwt).getBody();
  }
}
