package oceanstars.ecommerce.common.cqrs;

import com.google.protobuf.GeneratedMessageV3;

/**
 * CQRS；处理总线接口
 *
 * @author Clover
 * @version 1.0.0
 * @since 2021/12/8 1:57 下午
 */
public interface Bus {

  /**
   * 执行指定命令处理
   *
   * @param command 命令信息
   * @param <R>     处理结果类型
   * @param <C>     命令信息类型
   * @return 处理结果
   */
  <R, C extends GeneratedMessageV3> R executeCommand(C command);

  /**
   * 执行指定查询处理
   *
   * @param query 查询信息
   * @param <R>   处理结果类型
   * @param <Q>   查询信息类型
   * @return 处理结果
   */
  <R, Q extends GeneratedMessageV3> R executeQuery(Q query);
}
