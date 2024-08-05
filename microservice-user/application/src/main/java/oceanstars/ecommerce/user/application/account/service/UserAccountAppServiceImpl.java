package oceanstars.ecommerce.user.application.account.service;

import io.grpc.stub.StreamObserver;
import oceanstars.ecommerce.common.cqrs.Bus;
import oceanstars.ecommerce.infrastructure.grpc.service.provider.GrpcService;
import oceanstars.ecommerce.user.api.rpc.v1.dto.account.UserAccountExistQuery;
import oceanstars.ecommerce.user.api.rpc.v1.dto.account.UserAccountExistResult;
import oceanstars.ecommerce.user.api.rpc.v1.service.account.UserAccountAppServiceGrpc;

/**
 * 用户账号应用服务实现
 *
 * @author Clover
 * @version 1.0.0
 * @since 2024/7/16 13:57
 */
@GrpcService
public class UserAccountAppServiceImpl extends UserAccountAppServiceGrpc.UserAccountAppServiceImplBase {

  /**
   * CQRS处理总线
   */
  private final Bus cqrsGateway;

  public UserAccountAppServiceImpl(Bus cqrsGateway) {
    this.cqrsGateway = cqrsGateway;
  }

  @Override
  public void existAccount(UserAccountExistQuery request, StreamObserver<UserAccountExistResult> responseObserver) {
    // 执行查询处理
    final UserAccountExistResult existAccountResult = cqrsGateway.executeQuery(request);

    responseObserver.onNext(existAccountResult);
    responseObserver.onCompleted();
  }
}
