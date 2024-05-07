package oceanstars.ecommerce.ecm.repository.content.view;

import jakarta.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import oceanstars.ecommerce.common.domain.entity.EntityDelegator;
import oceanstars.ecommerce.ecm.constant.enums.EcmEnums.ContentType;
import oceanstars.ecommerce.ecm.domain.content.entity.ContentIdentifier;
import oceanstars.ecommerce.ecm.repository.content.strategy.impl.ContentRawStrategyContext;
import oceanstars.ecommerce.ecm.repository.content.view.bo.ContentView;
import oceanstars.ecommerce.ecm.repository.content.view.condition.ContentQueryCondition;
import oceanstars.ecommerce.ecm.repository.generate.tables.EcmContent;
import oceanstars.ecommerce.ecm.repository.generate.tables.EcmContentMirror;
import oceanstars.ecommerce.ecm.repository.generate.tables.daos.EcmContentDao;
import oceanstars.ecommerce.ecm.repository.generate.tables.pojos.EcmContentPojo;
import org.jooq.Condition;
import org.jooq.Field;
import org.jooq.OrderField;
import org.jooq.Result;
import org.jooq.SelectConditionStep;
import org.jooq.SelectSeekStepN;
import org.jooq.impl.DSL;
import org.jooq.impl.DefaultDSLContext;
import org.jooq.impl.TableImpl;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

/**
 * 内容视图DAO
 *
 * @author Clover
 * @version 1.0.0
 * @since 2024/1/23 22:23
 */
@Repository
public class ContentViewDao {

  /**
   * 内容元数据表对象
   */
  final static EcmContent T_CONTENT = EcmContent.ECM_CONTENT.as("meta");

  /**
   * 内容镜像表对象
   */
  final static EcmContentMirror T_CONTENT_MIRROR = EcmContentMirror.ECM_CONTENT_MIRROR.as("mirror");

  @Resource
  private EcmContentDao contentDao;

  @Resource
  private DefaultDSLContext dsl;

  /**
   * 根据内容委托者查询内容视图
   *
   * @param delegators 内容委托者列表
   * @return 内容视图集合
   */
  public List<ContentView<?>> fetchByDelegator(final EntityDelegator... delegators) {

    // 如果内容委托者为空则返回空
    if (delegators.length == 0) {
      return null;
    }

    // 获取内容元数据
    final List<EcmContentPojo> contentMetas = contentDao.fetchById(Arrays.stream(delegators).map(EntityDelegator::getId).toArray(Long[]::new));

    // 如果内容元数据为空则返回空
    if (CollectionUtils.isEmpty(contentMetas)) {
      return null;
    }

    // 获取内容类型（只针对一种内容类型进行查询，故默认选择首个数据确认）
    final ContentType type = ContentType.of(contentMetas.getFirst().getType().intValue());
    // 如果内容类型为空则返回空
    if (null == type) {
      return null;
    }

    // 构建查询条件: 内容委托者ID集合
    final Condition rawIdConditions = T_CONTENT.ID.in(contentMetas.stream().map(EcmContentPojo::getId).toArray(Long[]::new));

    // 构建查询条件: 内容类型
    final Condition typeCondition = T_CONTENT.TYPE.eq(type.key().shortValue());

    // 查询内容并返回
    return this.fetchContentView(type, typeCondition.and(rawIdConditions));
  }

  /**
   * 根据内容标识符查询内容视图
   *
   * @param identifiers 内容标识符列表
   * @return 内容视图集合
   */
  public List<ContentView<?>> fetchByIdentifier(final ContentIdentifier... identifiers) {

    // 如果内容标识符为空则返回空
    if (identifiers.length == 0) {
      return null;
    }

    // 获取内容类型（只针对一种内容类型进行查询，故默认选择首个数据确认）
    final ContentType type = identifiers[0].getType();

    // 构建查询条件: 内容名称集合
    final Condition nameConditions = T_CONTENT.NAME.in(
        Arrays.stream(identifiers).map(ContentIdentifier::getName).toArray(String[]::new));
    // 构建查询条件: 内容类型
    final Condition typeCondition = T_CONTENT.TYPE.eq(type.key().shortValue());

    // 查询内容并返回
    return this.fetchContentView(type, typeCondition.and(nameConditions));
  }

  public List<ContentView<?>> queryContentView(ContentQueryCondition condition) {

    // 初始化内容原始数据处理策略上下文
    final ContentRawStrategyContext strategyContext = new ContentRawStrategyContext(ContentType.FUNCTION_MENU);

    // 获取内容原生表对象
    final TableImpl<?> rawTable = strategyContext.executeDetermineRawTable();

    // 构建查询字段集合
    final List<Field<?>> selectFields = this.buildQueryFields(rawTable);

    // 构建查询条件集合
    final List<Condition> queryConditions = this.buildQueryConditions(condition);

    // 构建排序字段集合
    final List<OrderField<?>> orderFields = this.buildOrderFields();

    // 构建查询
    final SelectSeekStepN<?> query =
        dsl.select(selectFields)
            // 内容主表
            .from(T_CONTENT)
            // 内容原始数据表
            .join(rawTable)
            // (1:1)
            .on(T_CONTENT.RAW_ID.eq(rawTable.field("id", Long.class)))
            // 查询条件
            .where(queryConditions)
            // 排序条件
            .orderBy(orderFields);

    // 执行查询
    final Map<EcmContentPojo, ? extends Result<?>> resultMap = query.fetchGroups(EcmContentPojo.class);

    List<ContentView<?>> contents = new ArrayList<>(resultMap.size());

//    resultMap.forEach((meta, result) -> result.intoGroups(EcmContentFunctionMenuPojo.class, EcmContentSpacePojo.class)
//        .forEach((raw, spaces) -> contents.add(ContentView.newView(meta, raw, spaces))));

    return contents;
  }

  private List<ContentView<?>> fetchContentView(final ContentType type, final Condition fetchCondition) {

    // 初始化内容原始数据处理策略上下文
    final ContentRawStrategyContext strategyContext = new ContentRawStrategyContext(type);

    // 获取内容原生表对象
    final TableImpl<?> rawTable = strategyContext.executeDetermineRawTable();

    // 构建查询字段集合
    final List<Field<?>> selectFields = this.buildQueryFields(rawTable);

    // 构建查询
    final SelectConditionStep<?> query =
        dsl.select(selectFields)
            // 内容主表
            .from(T_CONTENT)
            // 内容原始数据表
            .join(rawTable)
            // (1:1)
            .on(T_CONTENT.RAW_ID.eq(rawTable.field("id", Long.class)))
            // 查询条件
            .where(fetchCondition);

    // 执行查询并返回结果
    final Result<?> result = query.fetch();

    // 初始化内容视图集合
    final List<ContentView<?>> contents = new ArrayList<>(result.size());

    result.forEach(record -> {
      // 查询结果映射内容元数据POJO
      final EcmContentPojo contentPojo = record.into(EcmContentPojo.class);
      // 查询结果映射内容原始数据POJO
      final Object contentRawPojo = strategyContext.executeRecordIntoPojo(record);

      contents.add(ContentView.newView(contentPojo, contentRawPojo));
    });

    return contents;
  }

  /**
   * 构建查询字段集合
   *
   * @param rawTable 内容原始数据表对象
   * @return 查询字段集合
   */
  private List<Field<?>> buildQueryFields(final TableImpl<?> rawTable) {

    // 初始化内容查询字段集合
    final List<Field<?>> selectFields = new ArrayList<>(Arrays.asList(T_CONTENT.fields()));
    // 根据查询内容类型添加内容原始数据字段
    selectFields.addAll(Arrays.asList(rawTable.fields()));

    return selectFields;
  }

  /**
   * 构建查询字段集合 (包含内容空间字段)
   *
   * @param rawTable 内容原始数据表对象
   * @return 查询字段集合
   */
  private List<Field<?>> buildQueryFieldsWithSpace(final TableImpl<?> rawTable) {

    // 初始化内容查询字段集合
    final List<Field<?>> selectFields = Arrays.asList(T_CONTENT.fields());
    // 根据查询内容类型添加内容原始数据字段
    selectFields.addAll(Arrays.asList(rawTable.fields()));
    // 内容查询字段集合添加内容空间字段 (1:n)

    return selectFields;
  }

  /**
   * 构建查询条件集合
   *
   * @param condition 查询条件
   * @return 查询条件集合
   */
  private List<Condition> buildQueryConditions(ContentQueryCondition condition) {

    // 初始化查询条件
    final List<Condition> queryConditions = new ArrayList<>();
    // 适配无条件查询条件
    queryConditions.add(DSL.trueCondition().and(T_CONTENT.COMMENTS.between(1L, 100L)));
    queryConditions.add(DSL.condition("MATCH(`x`) AGAINST ('abc')"));

    T_CONTENT.DESCRIPTION.contains("测试").and("MATCH(`x`) AGAINST ('abc')");

    return queryConditions;
  }

  /**
   * 构建排序字段集合
   *
   * @return 排序字段集合
   */
  private List<OrderField<?>> buildOrderFields() {
    return Arrays.asList(
        T_CONTENT.ID.asc()
    );
  }
}
