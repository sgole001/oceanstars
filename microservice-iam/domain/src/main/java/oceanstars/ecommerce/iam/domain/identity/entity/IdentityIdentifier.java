package oceanstars.ecommerce.iam.domain.identity.entity;

import java.io.Serial;
import java.text.MessageFormat;
import oceanstars.ecommerce.common.domain.entity.BaseEntityIdentifier;
import oceanstars.ecommerce.common.exception.BusinessException;
import oceanstars.ecommerce.iam.constant.enums.IamEnums.IdentityType;

/**
 * 身份实体唯一识别符生成器
 *
 * @author Clover
 * @version 1.0.0
 * @since 2024/7/8 10:09
 */
public class IdentityIdentifier extends BaseEntityIdentifier<String> {

  @Serial
  private static final long serialVersionUID = 4027127613005989687L;

  /**
   * 身份主体
   */
  private final String principal;

  /**
   * 身份类型
   */
  private final IdentityType type;

  public IdentityIdentifier(String principal, IdentityType type) {
    super(MessageFormat.format("{0}:{1}", type.name(), principal));
    this.principal = principal;
    this.type = type;
  }

  public String getPrincipal() {
    return principal;
  }

  public IdentityType getType() {
    return type;
  }

  @Override
  public String generateIdentifier() {
    throw new BusinessException("身份实体唯一标识符非自动生成，身份主体和身份类型确认唯一标识符。");
  }
}
