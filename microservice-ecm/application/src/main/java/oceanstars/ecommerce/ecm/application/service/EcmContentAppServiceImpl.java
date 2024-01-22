package oceanstars.ecommerce.ecm.application.service;

import io.grpc.stub.StreamObserver;
import jakarta.annotation.Resource;
import oceanstars.ecommerce.common.cqrs.CqrsGateway;
import oceanstars.ecommerce.ecm.api.rpc.v1.dto.EcmCreateContentCategoryCommand;
import oceanstars.ecommerce.ecm.api.rpc.v1.dto.EcmCreateContentCategoryResult;
import oceanstars.ecommerce.ecm.api.rpc.v1.dto.EcmCreateContentCommand;
import oceanstars.ecommerce.ecm.api.rpc.v1.dto.EcmCreateContentResult;
import oceanstars.ecommerce.ecm.api.rpc.v1.dto.EcmGetContentCategoryQuery;
import oceanstars.ecommerce.ecm.api.rpc.v1.dto.EcmGetContentCategoryResult;
import oceanstars.ecommerce.ecm.api.rpc.v1.dto.EcmSearchContentCategoryQuery;
import oceanstars.ecommerce.ecm.api.rpc.v1.dto.EcmSearchContentCategoryResult;
import oceanstars.ecommerce.ecm.api.rpc.v1.service.EcmContentAppServiceGrpc;
import oceanstars.ecommerce.infrastructure.grpc.service.provider.GrpcService;

/**
 * @author Clover
 * @version 1.0.0
 * @since 2022/1/17 1:49 PM
 */
@GrpcService
public class EcmContentAppServiceImpl extends EcmContentAppServiceGrpc.EcmContentAppServiceImplBase {

  /**
   * CQRS处理总线
   */
  @Resource(name = "cqrsGateway")
  private CqrsGateway cqrsGateway;

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

  /**
   * gRPC服务: 创建内容分类
   *
   * @param request          创建内容分类请求参数
   * @param responseObserver 创建内容分类响应观察者
   */
  @Override
  public void createContentCategory(EcmCreateContentCategoryCommand request, StreamObserver<EcmCreateContentCategoryResult> responseObserver) {
    // 执行命令处理
    final EcmCreateContentCategoryResult createContentCategoryResult = cqrsGateway.executeCommand(request);

    responseObserver.onNext(createContentCategoryResult);
    responseObserver.onCompleted();
  }

  /**
   * gRPC服务: 搜索内容分类
   *
   * @param request          搜索内容分类请求参数
   * @param responseObserver 搜索内容分类响应观察者
   */
  @Override
  public void searchContentCategory(EcmSearchContentCategoryQuery request, StreamObserver<EcmSearchContentCategoryResult> responseObserver) {

    // 执行查询处理
    final EcmSearchContentCategoryResult searchContentCategoryResult = cqrsGateway.executeQuery(request);

    responseObserver.onNext(searchContentCategoryResult);
    responseObserver.onCompleted();
  }

  /**
   * gRPC服务: 获取内容分类
   *
   * @param request          获取内容分类请求参数
   * @param responseObserver 获取内容分类响应观察者
   */
  @Override
  public void getContentCategory(EcmGetContentCategoryQuery request, StreamObserver<EcmGetContentCategoryResult> responseObserver) {

    // 执行查询处理
    final EcmGetContentCategoryResult getContentCategoryResult = cqrsGateway.executeQuery(request);

    responseObserver.onNext(getContentCategoryResult);
    responseObserver.onCompleted();
  }
}
