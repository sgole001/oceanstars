package oceanstars.ecommerce.ecm.domain.content.repository;

import oceanstars.ecommerce.common.domain.DomainRepository;
import oceanstars.ecommerce.ecm.domain.content.entity.Content;
import oceanstars.ecommerce.ecm.domain.content.entity.ContentIdentifier;

/**
 * 内容聚合资源库接口
 *
 * @author Clover
 * @version 1.0.0
 * @since 2024/1/23 11:58
 */
public interface ContentRepository extends DomainRepository<ContentIdentifier, Content> {

}
