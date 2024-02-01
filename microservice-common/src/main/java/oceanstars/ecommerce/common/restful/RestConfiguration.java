package oceanstars.ecommerce.common.restful;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import oceanstars.ecommerce.common.spring.ApplicationContextProvider;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.DependsOn;
import org.springframework.core.GenericTypeResolver;

/**
 * Restful请求处理配置类
 *
 * @author Clover
 * @version 1.0.0
 * @since 2022/1/17 11:44 AM
 */
@AutoConfiguration
@DependsOn(value = {"applicationContextProvider"})
@SuppressWarnings("unchecked")
public class RestConfiguration {

  @Bean(value = "restProvider")
  <R extends RestResponseMessage, E extends RestRequestMessage, H extends IRestHandler<R, E>> Map<Class<E>, H> restProvider() {

    // 获取所有实现接口RestHandler的Bean的名字
    final String[] restHandlerNames = ApplicationContextProvider.getApplicationContext().getBeanNamesForType(IRestHandler.class);

    if (restHandlerNames.length == 0) {
      return Collections.emptyMap();
    }

    // 初始化创建Restful请求处理映射
    final Map<Class<E>, H> restHandleMap = HashMap.newHashMap(restHandlerNames.length);

    Arrays.stream(restHandlerNames).forEach(name -> {

      // 获取命令处理类型
      final Class<IRestHandler<R, E>> handlerClass = (Class<IRestHandler<R, E>>) ApplicationContextProvider.getApplicationContext().getType(name);
      if (null == handlerClass) {
        return;
      }
      // 获取Restful处理类型中泛型列表
      final Class<?>[] generics = GenericTypeResolver.resolveTypeArguments(handlerClass, IRestHandler.class);
      // 获取命令处理对应的命令类型
      if (null == generics || generics.length < 1) {
        return;
      }
      // 获取Restful请求消息类型
      final Class<E> restReqType = (Class<E>) generics[1];
      // 获取Restful请求处理对象
      final H restHandler = (H) ApplicationContextProvider.getApplicationContext().getBean(handlerClass);

      restHandleMap.put(restReqType, restHandler);
    });

    return restHandleMap;
  }

  @Bean(value = "restGateway")
  public RestBus restGateway(ObjectProvider<Map<Class<? extends RestRequestMessage>, ? extends IRestHandler<?, ?>>> handleProvider) {
    return new RestGateway(handleProvider);
  }
}
