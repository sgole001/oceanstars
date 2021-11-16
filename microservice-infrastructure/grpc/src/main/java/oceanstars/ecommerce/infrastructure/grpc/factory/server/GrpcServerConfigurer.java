package oceanstars.ecommerce.infrastructure.grpc.factory.server;

import static java.util.Objects.requireNonNull;

import io.grpc.ServerBuilder;
import java.util.function.Consumer;
import org.springframework.lang.NonNull;

/**
 * gRPC服务器配置函数式接口
 *
 * @author Clover
 * @version 1.0.0
 * @since 2021/11/9 3:21 下午
 */
@FunctionalInterface
public interface GrpcServerConfigurer extends Consumer<ServerBuilder<?>> {

  /**
   * 构建gRPC服务器配置
   *
   * @param after gRPC服务器构建实例
   * @return gRPC服务器配置
   */
  @Override
  default GrpcServerConfigurer andThen(@NonNull final Consumer<? super ServerBuilder<?>> after) {
    requireNonNull(after);
    return t -> {
      accept(t);
      after.accept(t);
    };
  }
}
