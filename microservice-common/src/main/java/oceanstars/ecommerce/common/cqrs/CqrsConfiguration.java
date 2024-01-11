package oceanstars.ecommerce.common.cqrs;

import com.google.protobuf.GeneratedMessageV3;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import oceanstars.ecommerce.common.spring.ApplicationContextProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.core.GenericTypeResolver;

/**
 * CQRS配置类
 *
 * @author Clover
 * @version 1.0.0
 * @since 2021/12/8 2:21 下午
 */
@Configuration(proxyBeanMethods = false)
@DependsOn("applicationContextProvider")
@SuppressWarnings("unchecked")
public class CqrsConfiguration {

  /**
   * 命令处理集合Bean
   *
   * @param <R> 命令处理返回结果类型
   * @param <C> 命令类型
   * @param <H> 命令处理类型
   * @return 命令处理集合
   */
  @Bean(value = "commandProvider")
  <R, C extends GeneratedMessageV3, H extends ICommandHandler<R, C>> Map<Class<C>, H> commandProvider() {
    // 获取所有实现接口CommandHandler的Bean的名字
    final String[] commandHandlerNames = ApplicationContextProvider.getApplicationContext().getBeanNamesForType(ICommandHandler.class);

    if (commandHandlerNames.length == 0) {
      return Collections.emptyMap();
    }

    // 初始化创建命令处理映射
    final Map<Class<C>, H> commandHandleMap = HashMap.newHashMap(commandHandlerNames.length);

    Arrays.stream(commandHandlerNames).forEach(name -> {

      // 获取命令处理类型
      final Class<ICommandHandler<R, C>> handlerClass = (Class<ICommandHandler<R, C>>) ApplicationContextProvider.getApplicationContext()
          .getType(name);
      if (null == handlerClass) {
        return;
      }
      // 获取命令处理类型中泛型列表
      final Class<?>[] generics = GenericTypeResolver.resolveTypeArguments(handlerClass, ICommandHandler.class);
      // 获取命令处理对应的命令类型
      if (null == generics || generics.length < 2) {
        return;
      }
      final Class<C> commandType = (Class<C>) generics[1];
      // 获取命令处理对象
      final H commandHandler = (H) ApplicationContextProvider.getApplicationContext().getBean(handlerClass);

      commandHandleMap.put(commandType, commandHandler);
    });

    return commandHandleMap;
  }

  /**
   * 查询处理集合Bean
   *
   * @param <R> 查询处理返回结果类型
   * @param <Q> 查询类型
   * @param <H> 查询处理类型
   * @return 查询处理集合
   */
  @Bean(value = "queryProvider")
  <R, Q extends GeneratedMessageV3, H extends IQueryHandler<R, Q>> Map<Class<Q>, H> queryProvider() {

    // 获取所有实现接口QueryHandler的Bean的名字
    final String[] queryHandlerNames = ApplicationContextProvider.getApplicationContext().getBeanNamesForType(IQueryHandler.class);

    if (queryHandlerNames.length == 0) {
      return Collections.emptyMap();
    }

    // 初始化创建查询处理映射
    final Map<Class<Q>, H> queryHandleMap = HashMap.newHashMap(queryHandlerNames.length);

    Arrays.stream(queryHandlerNames).forEach(name -> {

      // 获取查询处理类型
      final Class<IQueryHandler<R, Q>> handlerClass = (Class<IQueryHandler<R, Q>>) ApplicationContextProvider.getApplicationContext().getType(name);
      if (null == handlerClass) {
        return;
      }
      // 获取查询处理类型中泛型列表
      final Class<?>[] generics = GenericTypeResolver.resolveTypeArguments(handlerClass, IQueryHandler.class);
      // 获取查询处理对应的查询类型
      if (null == generics || generics.length < 2) {
        return;
      }
      final Class<Q> queryType = (Class<Q>) generics[1];
      // 获取命令处理对象
      final H commandHandler = (H) ApplicationContextProvider.getApplicationContext().getBean(handlerClass);

      queryHandleMap.put(queryType, commandHandler);
    });

    return queryHandleMap;
  }
}
