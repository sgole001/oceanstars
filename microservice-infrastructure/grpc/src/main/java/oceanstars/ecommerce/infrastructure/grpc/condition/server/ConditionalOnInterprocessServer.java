package oceanstars.ecommerce.infrastructure.grpc.condition.server;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.NoneNestedConditions;

/**
 * 条件匹配规则：是否配置{@code grpc.server.port}没有-1值
 *
 * @author Clover
 * @version 1.0.0
 * @since 2021/11/11 5:23 下午
 */
public class ConditionalOnInterprocessServer extends NoneNestedConditions {

  ConditionalOnInterprocessServer() {
    super(ConfigurationPhase.REGISTER_BEAN);
  }

  @ConditionalOnProperty(name = "grpc.server.port", havingValue = "-1")
  static class NoServerPortCondition {

  }
}
