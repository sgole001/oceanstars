package oceanstars.ecommerce.ecm.application.cqrs.handler;

import oceanstars.ecommerce.common.cqrs.IQueryHandler;
import oceanstars.ecommerce.ecm.api.rpc.v1.dto.EcmSearchContentCategoryQuery;
import oceanstars.ecommerce.ecm.api.rpc.v1.dto.EcmSearchContentCategoryResult;
import org.springframework.stereotype.Component;

/**
 * 搜索内容分类查询处理器
 *
 * @author Clover
 * @version 1.0.0
 * @since 2024/1/16 09:58
 */
@Component
public class SearchContentCategoryQueryHandler implements IQueryHandler<EcmSearchContentCategoryResult, EcmSearchContentCategoryQuery> {

  @Override
  public EcmSearchContentCategoryResult handle(EcmSearchContentCategoryQuery query) {
    return null;
  }
}
