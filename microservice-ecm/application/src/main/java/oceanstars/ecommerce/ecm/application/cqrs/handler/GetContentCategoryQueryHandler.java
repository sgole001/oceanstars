package oceanstars.ecommerce.ecm.application.cqrs.handler;

import oceanstars.ecommerce.common.cqrs.IQueryHandler;
import oceanstars.ecommerce.ecm.api.rpc.v1.dto.EcmGetContentCategoryQuery;
import oceanstars.ecommerce.ecm.api.rpc.v1.dto.EcmGetContentCategoryResult;
import org.springframework.stereotype.Component;

/**
 * 获取内容分类查询处理器
 *
 * @author Clover
 * @version 1.0.0
 * @since 2024/1/16 10:02
 */
@Component
public class GetContentCategoryQueryHandler implements IQueryHandler<EcmGetContentCategoryResult, EcmGetContentCategoryQuery> {

  @Override
  public EcmGetContentCategoryResult handle(EcmGetContentCategoryQuery query) {
    return null;
  }
}
