package oceanstars.ecommerce.ecm.domain.tag.repository;

import oceanstars.ecommerce.common.domain.DomainRepository;
import oceanstars.ecommerce.ecm.domain.tag.entity.Tag;
import oceanstars.ecommerce.ecm.domain.tag.entity.TagIdentifier;

/**
 * 分类聚合资源库接口
 *
 * @author Clover
 * @version 1.0.0
 * @since 2024/1/25 13:57
 */
public interface TagRepository extends DomainRepository<TagIdentifier, Tag> {

}
