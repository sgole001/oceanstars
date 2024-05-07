package oceanstars.ecommerce.ecm.application.tag.service;

import io.grpc.stub.StreamObserver;
import oceanstars.ecommerce.common.cqrs.Bus;
import oceanstars.ecommerce.ecm.api.rpc.v1.dto.tag.EcmCreateTagCommand;
import oceanstars.ecommerce.ecm.api.rpc.v1.dto.tag.EcmCreateTagResult;
import oceanstars.ecommerce.ecm.api.rpc.v1.service.tag.EcmTagAppServiceGrpc;
import oceanstars.ecommerce.infrastructure.grpc.service.provider.GrpcService;

/**
 * ECM标签管理应用服务实现
 *
 * @author Clover
 * @version 1.0.0
 * @since 2024/1/25 12:46
 */
@GrpcService
public class EcmTagAppServiceImpl extends EcmTagAppServiceGrpc.EcmTagAppServiceImplBase {

  /**
   * CQRS处理总线
   */
  private final Bus cqrsGateway;

  /**
   * 构造函数：初始化成员变量
   *
   * @param cqrsGateway CQRS处理总线
   */
  public EcmTagAppServiceImpl(Bus cqrsGateway) {
    this.cqrsGateway = cqrsGateway;
  }

  /**
   * gRPC服务: 创建标签
   *
   * @param request          创建标签请求参数
   * @param responseObserver 创建标签响应观察者
   */
  @Override
  public void createTag(EcmCreateTagCommand request, StreamObserver<EcmCreateTagResult> responseObserver) {

    // 执行命令处理
    final EcmCreateTagResult createTagResult = cqrsGateway.executeCommand(request);

    responseObserver.onNext(createTagResult);
    responseObserver.onCompleted();
  }
}
