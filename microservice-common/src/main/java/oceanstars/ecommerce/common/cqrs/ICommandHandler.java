package oceanstars.ecommerce.common.cqrs;

import com.google.protobuf.GeneratedMessageV3;

/**
 * CQRS:命令对应处理接口
 *
 * @author Clover
 * @version 1.0.0
 * @since 2021/12/8 1:51 下午
 */
public interface ICommandHandler<R, C extends GeneratedMessageV3> {

  /**
   * 命令处理
   *
   * @param command 命令信息
   * @return 处理结果
   */
  R handle(C command);
}
