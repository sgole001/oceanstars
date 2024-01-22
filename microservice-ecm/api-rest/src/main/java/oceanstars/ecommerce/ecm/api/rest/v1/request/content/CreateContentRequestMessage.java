package oceanstars.ecommerce.ecm.api.rest.v1.request.content;

import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serial;
import java.util.List;
import java.util.Map;
import oceanstars.ecommerce.common.restful.RestRequestMessage;

/**
 * 创建内容接口请求参数
 *
 * @author Clover
 * @version 1.0.0
 * @since 2024/1/17 15:27
 */
@Schema(name = "CreateContentRequestMessage", description = "创建内容接口请求参数")
public class CreateContentRequestMessage extends RestRequestMessage {

  @Serial
  private static final long serialVersionUID = -7452551592352900855L;

  /**
   * 内容名称
   */
  private String name;

  /**
   * 内容类型
   */
  private Short type;


  /**
   * 内容展示名称
   */
  private String displayName;

  /**
   * 内容描述
   */
  private String description;

  /**
   * 内容标签
   */
  private List<Long> tags;

  /**
   * 内容分类
   */
  private List<Long> categories;

  /**
   * 不同内容类型特有数据
   */
  private Map<String, String> rawData;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Short getType() {
    return type;
  }

  public void setType(Short type) {
    this.type = type;
  }

  public String getDisplayName() {
    return displayName;
  }

  public void setDisplayName(String displayName) {
    this.displayName = displayName;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public List<Long> getTags() {
    return tags;
  }

  public void setTags(List<Long> tags) {
    this.tags = tags;
  }

  public List<Long> getCategories() {
    return categories;
  }

  public void setCategories(List<Long> categories) {
    this.categories = categories;
  }

  public Map<String, String> getRawData() {
    return rawData;
  }

  public void setRawData(Map<String, String> rawData) {
    this.rawData = rawData;
  }
}
