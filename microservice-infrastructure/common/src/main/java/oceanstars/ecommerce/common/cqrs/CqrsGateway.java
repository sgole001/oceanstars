package oceanstars.ecommerce.common.cqrs;

import com.google.protobuf.GeneratedMessageV3;
import jakarta.annotation.Resource;
import java.util.Map;
import org.springframework.stereotype.Component;

/**
 * CQRS网关：路由命令和查询处理
 *
 * @author Clover
 * @version 1.0.0
 * @since 2021/12/8 3:55 下午
 */
@Component(value = "cqrsGateway")
@SuppressWarnings("unchecked")
public class CqrsGateway implements Bus {

  @Resource(name = "commandProvider")
  private Map<Class<? extends GeneratedMessageV3>, ? extends ICommandHandler<?, ?>> commandProvider;

  @Resource(name = "queryProvider")
  private Map<Class<? extends GeneratedMessageV3>, ? extends IQueryHandler<?, ?>> queryProvider;

  @Override
  public <R, C extends GeneratedMessageV3> R executeCommand(C command) {
    ICommandHandler<R, C> commandHandler = (ICommandHandler<R, C>) commandProvider.get(command.getClass());
    return commandHandler.handle(command);
  }

  @Override
  public <R, Q extends GeneratedMessageV3> R executeQuery(Q query) {
    IQueryHandler<R, Q> queryHandler = (IQueryHandler<R, Q>) queryProvider.get(query.getClass());
    return queryHandler.handle(query);
  }
}
