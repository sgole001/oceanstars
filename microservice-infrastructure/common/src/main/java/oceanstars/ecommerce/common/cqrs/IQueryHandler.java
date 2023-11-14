package oceanstars.ecommerce.common.cqrs;

import com.google.protobuf.GeneratedMessageV3;

/**
 * CQRS:查询处理接口
 *
 * @author Clover
 * @version 1.0.0
 * @since 2021/12/8 1:54 下午
 */
public interface IQueryHandler<R, C extends GeneratedMessageV3> {

  /**
   * 查询处理
   *
   * @param query 查询信息
   * @return 处理结果
   */
  R handle(C query);
}
