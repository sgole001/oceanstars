package oceanstars.ecommerce.message.application.service;

import io.grpc.stub.StreamObserver;
import jakarta.annotation.Resource;
import oceanstars.ecommerce.common.cqrs.CqrsGateway;
import oceanstars.ecommerce.infrastructure.grpc.service.provider.GrpcService;
import oceanstars.ecommerce.message.api.rpc.v1.dto.MessagePublishCommand;
import oceanstars.ecommerce.message.api.rpc.v1.dto.MessagePublishResult;
import oceanstars.ecommerce.message.api.rpc.v1.service.MessageAppServiceGrpc;

/**
 * 消息应用服务
 *
 * @author Clover
 * @version 1.0.0
 * @since 2021/12/9 5:48 下午
 */
@GrpcService
public class MessageAppServiceImpl extends MessageAppServiceGrpc.MessageAppServiceImplBase {

  /**
   * CQRS处理总线
   */
  @Resource(name = "cqrsGateway")
  private CqrsGateway cqrsGateway;

  @Override
  public void publish(MessagePublishCommand request, StreamObserver<MessagePublishResult> responseObserver) {

    // 执行命令处理
    final MessagePublishResult publishResult = cqrsGateway.executeCommand(request);

    responseObserver.onNext(publishResult);
    responseObserver.onCompleted();
  }
}
