package oceanstars.ecommerce.ecm.application.content.cqrs.strategy;

import com.google.protobuf.Any;
import java.util.Map;
import oceanstars.ecommerce.common.domain.entity.ValueObject;

/**
 * 不同内容形式特有数据处理策略接口
 *
 * @author Clover
 * @version 1.0.0
 * @since 2024/1/17 17:17
 */
public interface ContentRawDataStrategy {


  /**
   * 打包请求消息
   *
   * @param message 请求消息
   * @return 打包后的请求消息
   */
  Any pack(Map<String, String> message);

  /**
   * 解包请求消息
   *
   * @param message 打包后的请求消息
   * @return 解包后的请求消息
   */
  ValueObject unpack(Any message);
}
