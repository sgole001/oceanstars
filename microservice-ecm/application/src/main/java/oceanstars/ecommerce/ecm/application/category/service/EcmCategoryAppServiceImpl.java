package oceanstars.ecommerce.ecm.application.category.service;

import io.grpc.stub.StreamObserver;
import oceanstars.ecommerce.common.cqrs.Bus;
import oceanstars.ecommerce.ecm.api.rpc.v1.dto.category.EcmCreateCategoryCommand;
import oceanstars.ecommerce.ecm.api.rpc.v1.dto.category.EcmCreateCategoryResult;
import oceanstars.ecommerce.ecm.api.rpc.v1.dto.category.EcmGetCategoryQuery;
import oceanstars.ecommerce.ecm.api.rpc.v1.dto.category.EcmGetCategoryResult;
import oceanstars.ecommerce.ecm.api.rpc.v1.dto.category.EcmSearchCategoryQuery;
import oceanstars.ecommerce.ecm.api.rpc.v1.dto.category.EcmSearchCategoryResult;
import oceanstars.ecommerce.ecm.api.rpc.v1.service.category.EcmCategoryAppServiceGrpc;
import oceanstars.ecommerce.infrastructure.grpc.service.provider.GrpcService;

/**
 * ECM分类管理应用服务实现
 *
 * @author Clover
 * @version 1.0.0
 * @since 2022/1/17 1:49 PM
 */
@GrpcService
public class EcmCategoryAppServiceImpl extends EcmCategoryAppServiceGrpc.EcmCategoryAppServiceImplBase {

  /**
   * CQRS处理总线
   */
  private final Bus cqrsGateway;

  /**
   * 构造函数：初始化成员变量
   *
   * @param cqrsGateway CQRS处理总线
   */
  public EcmCategoryAppServiceImpl(Bus cqrsGateway) {
    this.cqrsGateway = cqrsGateway;
  }

  /**
   * gRPC服务: 创建分类
   *
   * @param request          创建分类请求参数
   * @param responseObserver 创建分类响应观察者
   */
  @Override
  public void createCategory(EcmCreateCategoryCommand request, StreamObserver<EcmCreateCategoryResult> responseObserver) {
    // 执行命令处理
    final EcmCreateCategoryResult createCategoryResult = cqrsGateway.executeCommand(request);

    responseObserver.onNext(createCategoryResult);
    responseObserver.onCompleted();
  }

  /**
   * gRPC服务: 搜索分类
   *
   * @param request          搜索分类请求参数
   * @param responseObserver 搜索分类响应观察者
   */
  @Override
  public void searchCategory(EcmSearchCategoryQuery request, StreamObserver<EcmSearchCategoryResult> responseObserver) {

    // 执行查询处理
    final EcmSearchCategoryResult searchContentCategoryResult = cqrsGateway.executeQuery(request);

    responseObserver.onNext(searchContentCategoryResult);
    responseObserver.onCompleted();
  }

  /**
   * gRPC服务: 获取分类
   *
   * @param request          获取分类请求参数
   * @param responseObserver 获取分类响应观察者
   */
  @Override
  public void getCategory(EcmGetCategoryQuery request, StreamObserver<EcmGetCategoryResult> responseObserver) {

    // 执行查询处理
    final EcmGetCategoryResult getContentCategoryResult = cqrsGateway.executeQuery(request);

    responseObserver.onNext(getContentCategoryResult);
    responseObserver.onCompleted();
  }
}
