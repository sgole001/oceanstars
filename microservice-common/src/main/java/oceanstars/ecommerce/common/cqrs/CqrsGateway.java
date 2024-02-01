package oceanstars.ecommerce.common.cqrs;

import com.google.protobuf.GeneratedMessageV3;
import java.util.Map;
import org.springframework.beans.factory.ObjectProvider;

/**
 * CQRS网关：路由命令和查询处理
 *
 * @author Clover
 * @version 1.0.0
 * @since 2021/12/8 3:55 下午
 */
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

  /**
   * 构造函数
   *
   * @param commandProvider 命令处理器映射表
   * @param queryProvider   查询处理器映射表
   */
  public CqrsGateway(Map<Class<? extends GeneratedMessageV3>, ? extends ICommandHandler<?, ?>> commandProvider,
      Map<Class<? extends GeneratedMessageV3>, ? extends IQueryHandler<?, ?>> queryProvider) {
    this.commandProvider = commandProvider;
    this.queryProvider = queryProvider;
  }

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
