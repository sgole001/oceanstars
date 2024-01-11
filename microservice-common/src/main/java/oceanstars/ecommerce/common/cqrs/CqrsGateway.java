package oceanstars.ecommerce.common.cqrs;

import com.google.protobuf.GeneratedMessageV3;
import java.util.Map;
import org.springframework.beans.factory.ObjectProvider;
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

  /**
   * 命令处理器映射表
   */
  private final Map<Class<? extends GeneratedMessageV3>, ? extends ICommandHandler<?, ?>> commandProvider;

  /**
   * 查询处理器映射表
   */
  private final Map<Class<? extends GeneratedMessageV3>, ? extends IQueryHandler<?, ?>> queryProvider;

  public CqrsGateway(ObjectProvider<Map<Class<? extends GeneratedMessageV3>, ? extends ICommandHandler<?, ?>>> commandProvider,
      ObjectProvider<Map<Class<? extends GeneratedMessageV3>, ? extends IQueryHandler<?, ?>>> queryProvider) {
    this.commandProvider = commandProvider.getIfAvailable();
    this.queryProvider = queryProvider.getIfAvailable();
  }

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
