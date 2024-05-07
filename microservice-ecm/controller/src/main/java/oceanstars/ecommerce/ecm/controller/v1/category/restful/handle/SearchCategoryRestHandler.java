package oceanstars.ecommerce.ecm.controller.v1.category.restful.handle;

import com.google.protobuf.GeneratedMessageV3;
import oceanstars.ecommerce.common.cqrs.Bus;
import oceanstars.ecommerce.common.restful.BaseRestHandler;
import oceanstars.ecommerce.common.restful.RestResponseMessage;
import oceanstars.ecommerce.ecm.api.rest.v1.request.category.SearchCategoryRequestMessage;
import org.springframework.stereotype.Component;

/**
 * Restful请求处理: 搜索内容分类
 *
 * @author Clover
 * @version 1.0.0
 * @since 2024/1/15 16:05
 */
@Component
public class SearchCategoryRestHandler extends BaseRestHandler<SearchCategoryRequestMessage> {

  /**
   * CQRS处理总线
   */
  private final Bus cqrsGateway;

  /**
   * 构造函数
   *
   * @param cqrsGateway CQRS处理总线
   */
  public SearchCategoryRestHandler(Bus cqrsGateway) {
    this.cqrsGateway = cqrsGateway;
  }

  @Override
  public GeneratedMessageV3[] parsingRequestMessage(SearchCategoryRequestMessage restRequestMessage) {
    return new GeneratedMessageV3[0];
  }

  @Override
  public GeneratedMessageV3 process(GeneratedMessageV3[] messages) {
    return null;
  }

  @Override
  public RestResponseMessage packageResponseMessage(GeneratedMessageV3 result) {
    return null;
  }
}
