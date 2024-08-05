package oceanstars.ecommerce.iam.api.rest.v1.request.authn;

import java.io.Serial;
import oceanstars.ecommerce.common.restful.RestRequestMessage;

/**
 * 认证请求消息基类
 *
 * @author Clover
 * @version 1.0.0
 * @since 2024/7/15 16:34
 */
public class AuthnRequestMessage extends RestRequestMessage {

  @Serial
  private static final long serialVersionUID = -4209994481645547112L;

  /**
   * 身份域
   */
  private String domain;

  public String getDomain() {
    return domain;
  }

  public void setDomain(String domain) {
    this.domain = domain;
  }
}
