package oceanstars.ecommerce.user.application.service;

import io.grpc.stub.StreamObserver;
import jakarta.annotation.Resource;
import oceanstars.ecommerce.common.cqrs.CqrsGateway;
import oceanstars.ecommerce.infrastructure.grpc.service.provider.GrpcService;
import oceanstars.ecommerce.user.api.rpc.v1.dto.CreateResourceTypeCommand;
import oceanstars.ecommerce.user.api.rpc.v1.dto.CreateResourceTypeResult;
import oceanstars.ecommerce.user.api.rpc.v1.service.UserAppServiceGrpc;

/**
 * <此类的功能说明>
 *
 * @author Clover
 * @version 1.0.0
 * @since 2022/1/17 1:49 PM
 */
@GrpcService
public class UserAppServiceImpl extends UserAppServiceGrpc.UserAppServiceImplBase {

  /**
   * CQRS处理总线
   */
  @Resource(name = "cqrsGateway")
  private CqrsGateway cqrsGateway;

  @Override
  public void createResourceType(CreateResourceTypeCommand request, StreamObserver<CreateResourceTypeResult> responseObserver) {

    // 执行命令处理
    final CreateResourceTypeResult createResourceTypeResult = cqrsGateway.executeCommand(request);

    responseObserver.onNext(createResourceTypeResult);
    responseObserver.onCompleted();
  }
}
