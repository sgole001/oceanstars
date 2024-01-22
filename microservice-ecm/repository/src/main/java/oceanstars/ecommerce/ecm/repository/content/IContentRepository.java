package oceanstars.ecommerce.ecm.repository.content;

import oceanstars.ecommerce.ecm.repository.generate.tables.pojos.EcmContentCategoryHierarchyPojo;
import oceanstars.ecommerce.ecm.repository.generate.tables.pojos.EcmContentCategoryPojo;
import oceanstars.ecommerce.ecm.repository.generate.tables.pojos.EcmContentPojo;
import oceanstars.ecommerce.ecm.repository.generate.tables.pojos.EcmContentTagPojo;
import oceanstars.ecommerce.ecm.repository.generate.tables.pojos.RelContentCategoryPojo;
import oceanstars.ecommerce.ecm.repository.generate.tables.pojos.RelContentTagPojo;

/**
 * 内容聚合仓储接口
 *
 * @author Clover
 * @version 1.0.0
 * @since 2024/1/15 12:13
 */
public interface IContentRepository {

  /**
   * 创建内容
   *
   * @param contentPojo 内容数据
   */
  void createContent(EcmContentPojo contentPojo);

  /**
   * 创建内容分类
   *
   * @param contentCategoryPojo 内容分类数据
   */
  void createContentCategory(EcmContentCategoryPojo contentCategoryPojo);

  /**
   * 创建内容分类隶属关系
   *
   * @param hierarchy 内容分类隶属关系数据
   */
  void createContentCategoryHierarchy(EcmContentCategoryHierarchyPojo hierarchy);

  /**
   * 创建内容标签
   *
   * @param contentTagPojo 内容标签数据
   */
  void createContentTag(EcmContentTagPojo contentTagPojo);

  /**
   * 关联内容分类
   *
   * @param rel 内容与分类关联数据
   */
  void associateContentCategory(RelContentCategoryPojo rel);

  /**
   * 关联内容标签
   *
   * @param rel 内容与标签关联数据
   */
  void associateContentTag(RelContentTagPojo rel);
}
