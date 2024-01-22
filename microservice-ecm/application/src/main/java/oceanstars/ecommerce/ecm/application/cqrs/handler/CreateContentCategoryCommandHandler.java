package oceanstars.ecommerce.ecm.application.cqrs.handler;

import jakarta.annotation.Resource;
import oceanstars.ecommerce.common.cqrs.ICommandHandler;
import oceanstars.ecommerce.common.domain.EventGateway;
import oceanstars.ecommerce.ecm.api.rpc.v1.dto.EcmCreateContentCategoryCommand;
import oceanstars.ecommerce.ecm.api.rpc.v1.dto.EcmCreateContentCategoryResult;
import org.springframework.stereotype.Component;

/**
 * 创建内容分类命令处理器
 *
 * @author Clover
 * @version 1.0.0
 * @since 2024/1/16 09:56
 */
@Component
public class CreateContentCategoryCommandHandler implements ICommandHandler<EcmCreateContentCategoryResult, EcmCreateContentCategoryCommand> {

  @Resource(name = "eventGateway")
  private EventGateway eventGateway;

  @Override
  public EcmCreateContentCategoryResult handle(EcmCreateContentCategoryCommand command) {
    return null;
  }
}
