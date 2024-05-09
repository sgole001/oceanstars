package oceanstars.ecommerce.ecm.controller.v1.content.restful.strategy;

import com.google.protobuf.Message;
import oceanstars.ecommerce.common.restful.RestRequestMessage;
import oceanstars.ecommerce.ecm.api.rest.v1.request.content.CreateContentRequestMessage;

/**
 * 内容Restful请求消息处理策略
 *
 * @author Clover
 * @version 1.0.0
 * @since 2024/5/7 17:11
 */
public interface ContentRequestMessageStrategy {

  CreateContentRequestMessage<? extends Message> create(RestRequestMessage message);
}
