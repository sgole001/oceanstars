package oceanstars.ecommerce.ecm.application.category.cqrs.handler;

import oceanstars.ecommerce.common.cqrs.IQueryHandler;
import oceanstars.ecommerce.ecm.api.rpc.v1.dto.category.EcmGetCategoryQuery;
import oceanstars.ecommerce.ecm.api.rpc.v1.dto.category.EcmGetCategoryResult;
import org.springframework.stereotype.Component;

/**
 * 获取内容分类查询处理器
 *
 * @author Clover
 * @version 1.0.0
 * @since 2024/1/16 10:02
 */
@Component
public class GetCategoryQueryHandler implements IQueryHandler<EcmGetCategoryResult, EcmGetCategoryQuery> {

  @Override
  public EcmGetCategoryResult handle(EcmGetCategoryQuery query) {
    return null;
  }
}
