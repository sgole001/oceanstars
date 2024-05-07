package oceanstars.ecommerce.ecm.domain.asset.entity;

import oceanstars.ecommerce.common.domain.entity.AggregateRoot;
import oceanstars.ecommerce.common.domain.entity.ValueObject;
import oceanstars.ecommerce.ecm.constant.enums.EcmEnums.AssetStatus;
import oceanstars.ecommerce.ecm.constant.enums.EcmEnums.AssetType;

/**
 * 资产聚合根
 *
 * @author Clover
 * @version 1.0.0
 * @since 2024/4/2 15:31
 */
public class Asset extends AggregateRoot<AssetIdentifier> {

  /**
   * 资产描述
   */
  private String description;

  /**
   * 资产状态
   */
  private AssetStatus status;

  /**
   * 资产原始信息
   */
  private ValueObject raw;

  /**
   * 构造函数: 初始化领域实体唯一标识符
   *
   * @param builder 资产构建器
   */
  private Asset(Builder builder) {
    super(new AssetIdentifier(builder.type, builder.name));
    description = builder.description;
    status = builder.status;
    raw = builder.raw;
  }

  /**
   * 创建资产实体构建器
   *
   * @param type 资产类型
   * @param name 资产名称
   * @return 资产实体构建器
   */
  public static Builder newBuilder(AssetType type, String name) {
    return new Builder(type, name);
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public AssetStatus getStatus() {
    return status;
  }

  public void setStatus(AssetStatus status) {
    this.status = status;
  }

  public ValueObject getRaw() {
    return raw;
  }

  public void setRaw(ValueObject raw) {
    this.raw = raw;
  }

  /**
   * 创建资产实体构建器
   *
   * @author Clover
   * @version 1.0.0
   * @since 2024/4/3 17:24
   */
  public static final class Builder {

    private final AssetType type;
    private final String name;
    private String description;
    private AssetStatus status;
    private ValueObject raw;


    public Builder(AssetType type, String name) {
      this.type = type;
      this.name = name;
    }

    public Builder description(String val) {
      description = val;
      return this;
    }

    public Builder status(AssetStatus val) {
      status = val;
      return this;
    }

    public Builder raw(ValueObject val) {
      raw = val;
      return this;
    }

    public Asset build() {
      return new Asset(this);
    }
  }
}
