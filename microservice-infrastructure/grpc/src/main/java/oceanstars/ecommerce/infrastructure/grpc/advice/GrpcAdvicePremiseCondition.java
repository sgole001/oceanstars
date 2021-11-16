package oceanstars.ecommerce.infrastructure.grpc.advice;

import static java.util.Objects.requireNonNull;

import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.context.annotation.ConfigurationCondition;
import org.springframework.core.type.AnnotatedTypeMetadata;
import org.springframework.lang.NonNull;

/**
 * Grpc Advice相关内容有效的前提条件
 *
 * @author Clover
 * @version 1.0.0
 * @since 2021/11/10 1:27 上午
 */
public class GrpcAdvicePremiseCondition implements ConfigurationCondition {

  @NonNull
  @Override
  public ConfigurationPhase getConfigurationPhase() {
    return ConfigurationPhase.REGISTER_BEAN;
  }

  @Override
  public boolean matches(ConditionContext context, @NonNull AnnotatedTypeMetadata metadata) {
    final ConfigurableListableBeanFactory safeBeanFactory = requireNonNull(context.getBeanFactory(), "ConfigurableListableBeanFactory为null");
    return safeBeanFactory.getBeanNamesForAnnotation(GrpcAdvice.class).length != 0;
  }
}
