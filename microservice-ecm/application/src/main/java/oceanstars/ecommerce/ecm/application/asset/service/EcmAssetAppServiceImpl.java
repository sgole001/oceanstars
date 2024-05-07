package oceanstars.ecommerce.ecm.application.asset.service;

import io.grpc.stub.StreamObserver;
import oceanstars.ecommerce.common.cqrs.Bus;
import oceanstars.ecommerce.ecm.api.rpc.v1.dto.asset.EcmCreateAssetCommand;
import oceanstars.ecommerce.ecm.api.rpc.v1.dto.asset.EcmCreateAssetResult;
import oceanstars.ecommerce.ecm.api.rpc.v1.service.asset.EcmAssetAppServiceGrpc;
import oceanstars.ecommerce.infrastructure.grpc.service.provider.GrpcService;

/**
 * ECM资产管理应用服务实现
 *
 * @author Clover
 * @version 1.0.0
 * @since 2024/4/18 10:20
 */
@GrpcService
public class EcmAssetAppServiceImpl extends EcmAssetAppServiceGrpc.EcmAssetAppServiceImplBase {

  /**
   * CQRS处理总线
   */
  private final Bus cqrsGateway;

  /**
   * 构造函数：初始化成员变量
   *
   * @param cqrsGateway CQRS处理总线
   */
  public EcmAssetAppServiceImpl(Bus cqrsGateway) {
    this.cqrsGateway = cqrsGateway;
  }

  /**
   * gRPC服务: 创建资产
   *
   * @param request          创建资产请求参数
   * @param responseObserver 创建资产响应观察者
   */
  @Override
  public void createAsset(EcmCreateAssetCommand request, StreamObserver<EcmCreateAssetResult> responseObserver) {

    // 执行命令处理
    final EcmCreateAssetResult createAssetResult = cqrsGateway.executeCommand(request);

    responseObserver.onNext(createAssetResult);
    responseObserver.onCompleted();
  }
}
