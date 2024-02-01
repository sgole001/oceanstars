package oceanstars.ecommerce.ecm.repository.content.strategy.impl;

import static java.util.Objects.requireNonNull;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import oceanstars.ecommerce.common.domain.Entity;
import oceanstars.ecommerce.common.spring.ApplicationContextProvider;
import oceanstars.ecommerce.ecm.constant.enums.EcmEnums.WebFunctionType;
import oceanstars.ecommerce.ecm.domain.content.entity.WebFunction;
import oceanstars.ecommerce.ecm.repository.content.strategy.ContentRawEntityStrategy;
import oceanstars.ecommerce.ecm.repository.generate.tables.daos.EcmContentWebFunctionDao;
import oceanstars.ecommerce.ecm.repository.generate.tables.pojos.EcmContentWebFunctionPojo;

/**
 * Web功能内容特有数据处理策略
 *
 * @author Clover
 * @version 1.0.0
 * @since 2024/1/17 17:26
 */
public class WebFunctionEntityStrategy implements ContentRawEntityStrategy {

  /**
   * 内容特有数据DAO: Web功能
   */
  private static final EcmContentWebFunctionDao DAO =
      ApplicationContextProvider.getApplicationContext().getBean(EcmContentWebFunctionDao.class);

  @Override
  public Optional<Entity<?>> fetch(Long contentId) {

    // 根据内容ID获取Web功能数据
    final EcmContentWebFunctionPojo webFunctionPojo = DAO.fetchOneByCid(contentId);

    if (null != webFunctionPojo) {
      return Optional.of(WebFunction.newBuilder(contentId)
          // Web功能类型
          .type(WebFunctionType.of(webFunctionPojo.getType().intValue()))
          // 功能跳转
          .href(webFunctionPojo.getHref())
          // 功能图标
          .icon(webFunctionPojo.getIcon())
          // Web功能类型
          .parent(webFunctionPojo.getPid())
          // 构建执行
          .build()
      );
    }

    return Optional.empty();
  }

  @Override
  public Map<Long, Entity<?>> fetch(List<Long> contentIds) {

    // 根据内容ID获取Web功能数据
    final List<EcmContentWebFunctionPojo> webFunctionPojos = DAO.fetchByCid(contentIds.toArray(new Long[0]));

    if (webFunctionPojos.isEmpty()) {
      return null;
    }

    // 初始化创建Web功能实体集合
    final Map<Long, Entity<?>> webFunctions = HashMap.newHashMap(webFunctionPojos.size());

    webFunctionPojos.forEach(webFunctionPojo -> {
      webFunctions.put(webFunctionPojo.getId(), WebFunction.newBuilder(webFunctionPojo.getId())
          // Web功能类型
          .type(WebFunctionType.of(webFunctionPojo.getType().intValue()))
          // 功能跳转
          .href(webFunctionPojo.getHref())
          // 功能图标
          .icon(webFunctionPojo.getIcon())
          // Web功能类型
          .parent(webFunctionPojo.getPid())
          // 构建执行
          .build());
    });

    return webFunctions;
  }

  @Override
  public void save(Entity<?> entity) {

    // 校验参数
    requireNonNull(entity, "entity");

    // 构建Web功能POJO
    final EcmContentWebFunctionPojo webFunctionPojo = buildWebFunctionPojo((WebFunction) entity);

    // 保存Web功能数据
    DAO.insert(webFunctionPojo);
  }

  /**
   * 构建Web功能POJO
   *
   * @param webFunction Web功能实体
   * @return Web功能POJO
   */
  private EcmContentWebFunctionPojo buildWebFunctionPojo(final WebFunction webFunction) {

    // 初始化创建Web功能POJO
    final EcmContentWebFunctionPojo webFunctionPojo = new EcmContentWebFunctionPojo();

    // 功能ID
    webFunctionPojo.setId(webFunction.getDelegator().getId());
    // 功能类型
    webFunctionPojo.setType(webFunction.getType().key().shortValue());
    // 功能跳转
    webFunctionPojo.setHref(webFunction.getHref());
    // 功能图标
    webFunctionPojo.setIcon(webFunction.getIcon());
    // Web功能隶属
    webFunctionPojo.setPid(webFunction.getParent());

    return webFunctionPojo;
  }
}
