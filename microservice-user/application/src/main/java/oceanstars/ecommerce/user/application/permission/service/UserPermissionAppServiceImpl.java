package oceanstars.ecommerce.user.application.permission.service;

import io.grpc.stub.StreamObserver;
import oceanstars.ecommerce.common.cqrs.Bus;
import oceanstars.ecommerce.infrastructure.grpc.service.provider.GrpcService;
import oceanstars.ecommerce.user.api.rpc.v1.dto.permission.UserCreatePermissionCommand;
import oceanstars.ecommerce.user.api.rpc.v1.dto.permission.UserCreatePermissionResult;
import oceanstars.ecommerce.user.api.rpc.v1.service.permission.UserPermissionAppServiceGrpc;

/**
 * 用户权限应用服务实现
 *
 * @author Clover
 * @version 1.0.0
 * @since 2022/1/17 1:49 PM
 */
@GrpcService
public class UserPermissionAppServiceImpl extends UserPermissionAppServiceGrpc.UserPermissionAppServiceImplBase {

  /**
   * CQRS处理总线
   */
  private final Bus cqrsGateway;

  public UserPermissionAppServiceImpl(Bus cqrsGateway) {
    this.cqrsGateway = cqrsGateway;
  }

  @Override
  public void createPermission(UserCreatePermissionCommand request, StreamObserver<UserCreatePermissionResult> responseObserver) {

    // 执行命令处理
    final UserCreatePermissionResult createPermissionResult = cqrsGateway.executeCommand(request);

    responseObserver.onNext(createPermissionResult);
    responseObserver.onCompleted();
  }
}
