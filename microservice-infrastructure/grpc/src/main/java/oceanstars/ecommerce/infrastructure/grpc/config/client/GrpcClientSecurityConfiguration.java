package oceanstars.ecommerce.infrastructure.grpc.config.client;

import io.grpc.CallCredentials;
import oceanstars.ecommerce.infrastructure.grpc.security.client.CallCredentialsHelper;
import oceanstars.ecommerce.infrastructure.grpc.service.consumer.StubTransformer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnSingleCandidate;
import org.springframework.context.annotation.Bean;

/**
 * gRPC客户端安全配置
 *
 * @author Clover
 * @version 1.0.0
 * @since 2024/1/19 15:36
 */
@AutoConfiguration(before = {GrpcClientConfiguration.class})
public class GrpcClientSecurityConfiguration {

  /**
   * 日志管理器
   */
  private final Logger logger = LogManager.getLogger(GrpcClientSecurityConfiguration.class.getName());

  @ConditionalOnSingleCandidate(CallCredentials.class)
  @ConditionalOnMissingBean
  @Bean
  StubTransformer stubCallCredentialsTransformer(final CallCredentials credentials) {
    logger.info("在上下文中找到单个CallCredentials，自动将其用于所有Stub");
    return CallCredentialsHelper.fixedCredentialsStubTransformer(credentials);
  }
}
