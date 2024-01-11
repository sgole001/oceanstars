package oceanstars.ecommerce.user.application.service.strategy.impl;

import java.util.List;
import oceanstars.ecommerce.common.domain.Entity;
import oceanstars.ecommerce.user.application.service.strategy.ResourceStrategy;
import oceanstars.ecommerce.user.constant.enums.UserEnums.PermissionType;

/**
 * 权限资源策略上下文
 *
 * @author Clover
 * @version 1.0.0
 * @since 2024/1/8 14:58
 */
public class ResourceStrategyContext {

  /**
   * 资源策略
   */
  private ResourceStrategy strategy;

  public ResourceStrategyContext(PermissionType type) {
    switch (type) {
      case FUNCTION:
        this.strategy = new FunctionResourceStrategy();
        break;
      case MEDIA:
        this.strategy = new MediaResourceStrategy();
        break;
      case DATA:
        this.strategy = new DataBaseResourceStrategy();
        break;
      default:
        break;
    }
  }

  /**
   * 执行资源策略
   *
   * @return 资源数据
   */
  public List<Entity<?>> execute() {
    return this.strategy.fetchResourceData();
  }
}
