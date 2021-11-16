package oceanstars.ecommerce.infrastructure.grpc.factory.client.channel;

import static java.util.Objects.requireNonNull;

import io.grpc.ManagedChannelBuilder;
import java.util.function.BiConsumer;

/**
 * gRPC客户端连接通道配置函数式接口
 *
 * @author Clover
 * @version 1.0.0
 * @since 2021/11/15 9:53 上午
 */
@FunctionalInterface
public interface GrpcChannelConfigurer extends BiConsumer<ManagedChannelBuilder<?>, String> {

  @Override
  default GrpcChannelConfigurer andThen(final BiConsumer<? super ManagedChannelBuilder<?>, ? super String> after) {
    requireNonNull(after, "after");
    return (l, r) -> {
      accept(l, r);
      after.accept(l, r);
    };
  }
}
