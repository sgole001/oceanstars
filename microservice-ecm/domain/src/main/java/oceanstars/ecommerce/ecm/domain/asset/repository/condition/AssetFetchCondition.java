package oceanstars.ecommerce.ecm.domain.asset.repository.condition;

import oceanstars.ecommerce.common.domain.repository.condition.BaseCondition;
import oceanstars.ecommerce.ecm.constant.enums.EcmEnums.AssetStatus;
import oceanstars.ecommerce.ecm.constant.enums.EcmEnums.AssetType;

/**
 * 资产共用字段查询条件
 *
 * @author Clover
 * @version 1.0.0
 * @since 2024/4/24 10:46
 */
public class AssetFetchCondition extends BaseCondition {

  /**
   * 资产名称
   */
  private final String name;

  /**
   * 资产类型
   */
  private final AssetType type;

  /**
   * 资产状态
   */
  private final AssetStatus status;

  /**
   * 构造函数
   *
   * @param builder 构造器
   */
  protected AssetFetchCondition(Builder builder) {
    super(builder);
    name = builder.name;
    type = builder.type;
    status = builder.status;
  }

  public static Builder newBuilder() {
    return new Builder();
  }

  public String getName() {
    return name;
  }

  public AssetType getType() {
    return type;
  }

  public AssetStatus getStatus() {
    return status;
  }

  /**
   * 构造器
   *
   * @author Clover
   * @version 1.0.0
   * @since 2024/4/24 10:51
   */
  public static class Builder extends BaseCondition.Builder<AssetFetchCondition, Builder> {

    private String name;
    private AssetType type;
    private AssetStatus status;

    public Builder name(String val) {
      name = val;
      return this;
    }

    public Builder name(AssetType val) {
      type = val;
      return this;
    }

    public Builder status(AssetStatus val) {
      status = val;
      return this;
    }
  }
}
