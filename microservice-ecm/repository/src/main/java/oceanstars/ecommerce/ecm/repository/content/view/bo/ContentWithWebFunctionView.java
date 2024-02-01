package oceanstars.ecommerce.ecm.repository.content.view.bo;

import oceanstars.ecommerce.ecm.repository.generate.tables.pojos.EcmContentPojo;
import oceanstars.ecommerce.ecm.repository.generate.tables.pojos.EcmContentWebFunctionPojo;

/**
 * WEB功能的内容视图BO
 *
 * @author Clover
 * @version 1.0.0
 * @since 2024/1/23 22:32
 */
public class ContentWithWebFunctionView {

  private EcmContentPojo meta;

  private EcmContentWebFunctionPojo raw;

  public EcmContentPojo getMeta() {
    return meta;
  }

  public void setMeta(EcmContentPojo meta) {
    this.meta = meta;
  }

  public EcmContentWebFunctionPojo getRaw() {
    return raw;
  }

  public void setRaw(EcmContentWebFunctionPojo raw) {
    this.raw = raw;
  }
}
