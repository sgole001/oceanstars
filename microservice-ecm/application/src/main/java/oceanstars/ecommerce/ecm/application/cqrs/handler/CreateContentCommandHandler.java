package oceanstars.ecommerce.ecm.application.cqrs.handler;

import oceanstars.ecommerce.common.cqrs.ICommandHandler;
import oceanstars.ecommerce.ecm.api.rpc.v1.dto.EcmCreateContentCommand;
import oceanstars.ecommerce.ecm.api.rpc.v1.dto.EcmCreateContentResult;
import org.springframework.stereotype.Component;

/**
 * 创建内容命令处理器
 *
 * @author Clover
 * @version 1.0.0
 * @since 2024/1/17 17:00
 */
@Component
public class CreateContentCommandHandler implements ICommandHandler<EcmCreateContentResult, EcmCreateContentCommand> {

  @Override
  public EcmCreateContentResult handle(EcmCreateContentCommand command) {
    return null;
  }
}
