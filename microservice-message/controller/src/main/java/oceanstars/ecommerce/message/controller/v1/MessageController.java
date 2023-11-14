package oceanstars.ecommerce.message.controller.v1;

import oceanstars.ecommerce.common.restful.RestApiController;
import oceanstars.ecommerce.infrastructure.grpc.service.consumer.GrpcClient;
import oceanstars.ecommerce.message.api.rest.controller.IMessageController;
import oceanstars.ecommerce.message.api.rest.request.FetchMessageLogsRequestMessage;
import oceanstars.ecommerce.message.api.rest.response.FetchMessageLogsResponseMessage;
import oceanstars.ecommerce.message.api.rpc.v1.service.MessageAppServiceGrpc;
import org.springframework.web.bind.annotation.RestController;

/**
 * 消息对外服务API实现类
 *
 * @author Clover
 * @version 1.0.0
 * @since 2021/12/16 5:02 下午
 */
@RestController
public class MessageController extends RestApiController implements IMessageController {

  /**
   * 消息应用服务接口
   */
  @GrpcClient(value = "grpc-message-app-service")
  private MessageAppServiceGrpc.MessageAppServiceBlockingStub messageAppService;

  @Override
  public FetchMessageLogsResponseMessage fetchMessageLogs(String msgId, FetchMessageLogsRequestMessage requestMessage) {
    return null;
  }
}
