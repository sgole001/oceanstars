package oceanstars.ecommerce.user.application.service.strategy.impl;

import java.util.List;
import oceanstars.ecommerce.common.domain.entity.Entity;
import oceanstars.ecommerce.user.application.service.strategy.ResourceStrategy;

/**
 * 数据库资源数据策略
 *
 * @author Clover
 * @version 1.0.0
 * @since 2024/1/8 14:56
 */
public class DataBaseResourceStrategy implements ResourceStrategy {

  @Override
  public List<Entity<?>> fetchResourceData() {
    return null;
  }
}
