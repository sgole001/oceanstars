package oceanstars.ecommerce.ecm.api.rest.v1.request.asset;

import com.google.protobuf.Message;
import java.io.Serial;
import oceanstars.ecommerce.common.restful.RestRequestMessage;

/**
 * 创建资产接口请求参数
 *
 * @author Clover
 * @version 1.0.0
 * @since 2024/4/17 17:19
 */
public class CreateAssetRequestMessage<T extends Message> extends RestRequestMessage {

  @Serial
  private static final long serialVersionUID = -374232265203659681L;

  /**
   * 资产名称
   */
  private String name;

  /**
   * 资产类型
   */
  private Integer type;

  /**
   * 资产描述
   */
  private String description;

  /**
   * 资产原生信息
   */
  private T rawData;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Integer getType() {
    return type;
  }

  public void setType(Integer type) {
    this.type = type;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public T getRawData() {
    return rawData;
  }

  public void setRawData(T rawData) {
    this.rawData = rawData;
  }
}
