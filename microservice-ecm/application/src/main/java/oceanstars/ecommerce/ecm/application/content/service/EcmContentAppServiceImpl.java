package oceanstars.ecommerce.ecm.application.content.service;

import io.grpc.stub.StreamObserver;
import oceanstars.ecommerce.common.cqrs.Bus;
import oceanstars.ecommerce.ecm.api.rpc.v1.dto.content.EcmCreateContentCommand;
import oceanstars.ecommerce.ecm.api.rpc.v1.dto.content.EcmCreateContentResult;
import oceanstars.ecommerce.ecm.api.rpc.v1.service.content.EcmContentAppServiceGrpc;
import oceanstars.ecommerce.infrastructure.grpc.service.provider.GrpcService;

/**
 * ECM内容管理应用服务实现
 *
 * @author Clover
 * @version 1.0.0
 * @since 2022/1/17 1:49 PM
 */
@GrpcService
public class EcmContentAppServiceImpl extends EcmContentAppServiceGrpc.EcmContentAppServiceImplBase {

  /**
   * CQRS处理总线
   */
  private final Bus cqrsGateway;

  /**
   * 构造函数：初始化成员变量
   *
   * @param cqrsGateway CQRS处理总线
   */
  public EcmContentAppServiceImpl(Bus cqrsGateway) {
    this.cqrsGateway = cqrsGateway;
  }

  /**
   * gRPC服务: 创建内容
   *
   * @param request          创建内容请求参数
   * @param responseObserver 创建内容响应观察者
   */
  @Override
  public void createContent(EcmCreateContentCommand request, StreamObserver<EcmCreateContentResult> responseObserver) {
    // 执行命令处理
    final EcmCreateContentResult createContentResult = cqrsGateway.executeCommand(request);

    responseObserver.onNext(createContentResult);
    responseObserver.onCompleted();
  }
}
