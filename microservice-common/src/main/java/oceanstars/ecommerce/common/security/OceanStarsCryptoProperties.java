package oceanstars.ecommerce.common.security;

import java.util.Map;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 对称加解密算法信息Bean
 *
 * @author Clover
 * @version 1.0.0
 * @since 2021/11/3 3:26 下午
 */
@ConfigurationProperties("oceanstars.security")
public class OceanStarsCryptoProperties {

  public OceanStarsCryptoProperties() {
    // ...
  }

  /**
   * 加解密算法信息
   */
  private Map<String, CryptoBean> cryptos;

  public Map<String, CryptoBean> getCryptos() {
    return cryptos;
  }

  public void setCryptos(Map<String, CryptoBean> cryptos) {
    this.cryptos = cryptos;
  }
}
