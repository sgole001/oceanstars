package oceanstars.ecommerce.user.domain.constraint.entity;

import java.io.Serial;
import oceanstars.ecommerce.common.domain.entity.BaseEntityIdentifier;
import oceanstars.ecommerce.common.exception.BusinessException;

/**
 * 约束实体唯一标识符生成器
 *
 * @author Clover
 * @version 1.0.0
 * @since 2024/6/4 14:47
 */
public class ConstraintIdentifier extends BaseEntityIdentifier<String> {

  @Serial
  private static final long serialVersionUID = -5460618791214789388L;

  // 约束名
  private final String name;

  public ConstraintIdentifier(String name) {
    super(name);
    this.name = name;
  }

  public String getName() {
    return name;
  }

  @Override
  public String generateIdentifier() {
    throw new BusinessException("约束唯一标识符非自动生成，约束名称即为唯一标识符");
  }
}
