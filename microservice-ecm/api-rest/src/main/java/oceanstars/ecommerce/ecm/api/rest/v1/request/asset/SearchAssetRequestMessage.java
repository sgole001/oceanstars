package oceanstars.ecommerce.ecm.api.rest.v1.request.asset;

import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serial;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import oceanstars.ecommerce.common.restful.RestRequestMessage;

/**
 * 搜索资产接口请求参数
 *
 * @author Clover
 * @version 1.0.0
 * @since 2024/4/17 17:23
 */
@Schema(name = "SearchAssetRequestMessage", description = "搜索资产接口请求参数")
public class SearchAssetRequestMessage extends RestRequestMessage {

  @Serial
  private static final long serialVersionUID = 1942616758253354721L;

  /**
   * 资产ID
   */
  @Schema(description = "资产ID")
  private Long id;

  /**
   * 资产名称
   */
  @Schema(description = "资产名称")
  private String name;

  /**
   * 资产类型集合
   */
  @Schema(description = "资产类型集合")
  private List<Integer> types;

  /**
   * 资产原生信息
   */
  @Schema(description = "资产原生信息")
  private Map<String, String> rawData;

  /**
   * 创建时间起始
   */
  @Schema(description = "创建时间起始")
  private LocalDateTime startCreateTime;

  /**
   * 创建时间结束
   */
  @Schema(description = "创建时间结束")
  private LocalDateTime endCreateTime;

  /**
   * 更新时间起始
   */
  @Schema(description = "更新时间起始")
  private LocalDateTime startUpdateTime;

  /**
   * 更新时间结束
   */
  @Schema(description = "更新时间结束")
  private LocalDateTime endUpdateTime;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public List<Integer> getTypes() {
    return types;
  }

  public void setTypes(List<Integer> types) {
    this.types = types;
  }

  public Map<String, String> getRawData() {
    return rawData;
  }

  public void setRawData(Map<String, String> rawData) {
    this.rawData = rawData;
  }

  public LocalDateTime getStartCreateTime() {
    return startCreateTime;
  }

  public void setStartCreateTime(LocalDateTime startCreateTime) {
    this.startCreateTime = startCreateTime;
  }

  public LocalDateTime getEndCreateTime() {
    return endCreateTime;
  }

  public void setEndCreateTime(LocalDateTime endCreateTime) {
    this.endCreateTime = endCreateTime;
  }

  public LocalDateTime getStartUpdateTime() {
    return startUpdateTime;
  }

  public void setStartUpdateTime(LocalDateTime startUpdateTime) {
    this.startUpdateTime = startUpdateTime;
  }

  public LocalDateTime getEndUpdateTime() {
    return endUpdateTime;
  }

  public void setEndUpdateTime(LocalDateTime endUpdateTime) {
    this.endUpdateTime = endUpdateTime;
  }
}
