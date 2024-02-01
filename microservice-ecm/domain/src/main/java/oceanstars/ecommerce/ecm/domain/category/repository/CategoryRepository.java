package oceanstars.ecommerce.ecm.domain.category.repository;

import oceanstars.ecommerce.common.domain.DomainRepository;
import oceanstars.ecommerce.ecm.domain.category.entity.Category;
import oceanstars.ecommerce.ecm.domain.category.entity.CategoryIdentifier;

/**
 * 分类聚合资源库接口
 *
 * @author Clover
 * @version 1.0.0
 * @since 2024/1/25 13:57
 */
public interface CategoryRepository extends DomainRepository<CategoryIdentifier, Category> {

}
