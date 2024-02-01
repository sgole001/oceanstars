package oceanstars.ecommerce.ecm.application.cqrs.handler;

import oceanstars.ecommerce.common.cqrs.IQueryHandler;
import oceanstars.ecommerce.ecm.api.rpc.v1.dto.category.EcmSearchCategoryQuery;
import oceanstars.ecommerce.ecm.api.rpc.v1.dto.category.EcmSearchCategoryResult;
import org.springframework.stereotype.Component;

/**
 * 搜索内容分类查询处理器
 *
 * @author Clover
 * @version 1.0.0
 * @since 2024/1/16 09:58
 */
@Component
public class SearchCategoryQueryHandler implements IQueryHandler<EcmSearchCategoryResult, EcmSearchCategoryQuery> {

  @Override
  public EcmSearchCategoryResult handle(EcmSearchCategoryQuery query) {
    return null;
  }
}
