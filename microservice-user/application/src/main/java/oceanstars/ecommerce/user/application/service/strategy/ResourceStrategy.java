package oceanstars.ecommerce.user.application.service.strategy;

import java.util.List;
import oceanstars.ecommerce.common.domain.entity.Entity;

/**
 * 权限资源策略接口
 *
 * @author Clover
 * @version 1.0.0
 * @since 2024/1/8 14:52
 */
public interface ResourceStrategy {

  /**
   * 获取资源数据
   *
   * @return 资源数据
   */
  List<Entity<?>> fetchResourceData();
}
