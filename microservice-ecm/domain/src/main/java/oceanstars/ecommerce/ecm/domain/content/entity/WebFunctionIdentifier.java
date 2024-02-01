package oceanstars.ecommerce.ecm.domain.content.entity;

import java.io.Serial;
import oceanstars.ecommerce.common.domain.BaseEntityIdentifier;
import oceanstars.ecommerce.common.exception.BusinessException;

/**
 * 站点功能唯一标识符生成器
 *
 * @author Clover
 * @version 1.0.0
 * @since 2024/1/16 16:41
 */
public class WebFunctionIdentifier extends BaseEntityIdentifier<Long> {

  @Serial
  private static final long serialVersionUID = -137581853277226038L;

  public WebFunctionIdentifier(Long delegator) {
    super(delegator);
  }

  @Override
  public Long generateIdentifier() {
    throw new BusinessException("Web功能实体唯一标识符非自动生成，需初始化关联内容实体数据委托者ID");
  }
}
