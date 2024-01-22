package oceanstars.ecommerce.ecm.controller.v1.content.handle.strategy;

import com.google.protobuf.Any;
import java.util.Map;

/**
 * 不同内容形式特有数据处理策略接口
 *
 * @author Clover
 * @version 1.0.0
 * @since 2024/1/17 17:17
 */
public interface ContentRawDataStrategy {

  /**
   * 解析请求消息
   *
   * @param requestMessage 请求消息
   * @return 解析后的请求消息
   */

  Any parsingRequestMessage(Map<String, String> requestMessage);
}
