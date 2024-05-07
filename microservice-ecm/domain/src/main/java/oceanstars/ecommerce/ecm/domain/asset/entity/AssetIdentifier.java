package oceanstars.ecommerce.ecm.domain.asset.entity;

import java.io.Serial;
import oceanstars.ecommerce.common.domain.entity.BaseEntityIdentifier;
import oceanstars.ecommerce.common.exception.BusinessException;
import oceanstars.ecommerce.ecm.constant.enums.EcmEnums.AssetType;

/**
 * 资产唯一标识符生成器
 *
 * @author Clover
 * @version 1.0.0
 * @since 2024/4/3 10:32
 */
public class AssetIdentifier extends BaseEntityIdentifier<String> {

  @Serial
  private static final long serialVersionUID = 5756232679613641948L;

  /**
   * 资产类型
   */
  private final AssetType type;

  /**
   * 资产名称
   */
  private final String name;

  /**
   * 构造函数: 初始化资产唯一标识符
   *
   * @param type 资产类型
   * @param name 资产名称
   */
  public AssetIdentifier(AssetType type, String name) {
    super(type.value() + "#" + name.hashCode());
    this.type = type;
    this.name = name;
  }

  public AssetType getType() {
    return type;
  }

  public String getName() {
    return name;
  }

  @Override
  public String generateIdentifier() {
    throw new BusinessException("资产实体唯一标识符非自动生成，资产类型和资产名称组合");
  }
}
