package oceanstars.ecommerce.ecm.repository.content.view;

import jakarta.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import oceanstars.ecommerce.ecm.constant.enums.EcmEnums.ContentStatisticsType;
import oceanstars.ecommerce.ecm.constant.enums.EcmEnums.ContentType;
import oceanstars.ecommerce.ecm.repository.generate.tables.EcmContentStatistics;
import org.jooq.Condition;
import org.jooq.impl.DSL;
import org.jooq.impl.DefaultDSLContext;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

/**
 * 内容统计视图DAO
 *
 * @author Clover
 * @version 1.0.0
 * @since 2024/5/9 16:55
 */
@Repository
public class ContentStatisticsViewDao {

  /**
   * 内容统计表
   */
  final static EcmContentStatistics T_CONTENT_STATS = EcmContentStatistics.ECM_CONTENT_STATISTICS.as("stats");

  /**
   * Jooq数据库操作对象
   */
  @Resource
  private DefaultDSLContext dsl;

  /**
   * 根据查询条件查询内容统计信息
   *
   * @param contents   查询条件：内容ID集合
   * @param type       查询条件：内容类型
   * @param statistics 查询条件：内容统计类型
   * @return 内容统计映射信息
   */
  public Map<Short, Map<Long, Map<Short, Long>>> aggregate(final Set<Long> contents, final ContentType type,
      Set<ContentStatisticsType> statistics) {

    // 初始化查询条件
    final Condition searchCondition = DSL.trueCondition();

    // 内容ID
    if (!CollectionUtils.isEmpty(contents)) {
      searchCondition.and(T_CONTENT_STATS.CONTENT.in(contents));
    }
    // 内容类型
    if (null != type) {
      searchCondition.and(T_CONTENT_STATS.TYPE.eq(type.key().shortValue()));
    }
    // 统计类型
    if (!CollectionUtils.isEmpty(statistics)) {
      searchCondition.and(T_CONTENT_STATS.STATS.in(statistics.stream().map(ContentStatisticsType::key).toList()));
    }

    // 初始化内容类型集合
    final Map<Short, Map<Long, Map<Short, Long>>> result = new HashMap<>();

    // 查询内容统计信息
    dsl.select(T_CONTENT_STATS.CONTENT, T_CONTENT_STATS.TYPE, T_CONTENT_STATS.STATS, DSL.sum(T_CONTENT_STATS.VALUE).as("total"))
        .from(T_CONTENT_STATS)
        .where(searchCondition)
        .groupBy(T_CONTENT_STATS.CONTENT, T_CONTENT_STATS.TYPE, T_CONTENT_STATS.STATS)
        .orderBy(T_CONTENT_STATS.TYPE, T_CONTENT_STATS.CONTENT, T_CONTENT_STATS.STATS)
        .fetch().forEach(record -> {
          // 内容ID
          final Long content = record.get(T_CONTENT_STATS.CONTENT);
          // 内容类型
          final Short contentType = record.get(T_CONTENT_STATS.TYPE);
          // 统计类型
          final Short stats = record.get(T_CONTENT_STATS.STATS);
          // 统计值
          final Long value = record.get(T_CONTENT_STATS.field("total", Long.class));

          // 初始化内容集合
          if (!result.containsKey(contentType)) {
            result.put(contentType, new HashMap<>());
          }

          // 获取指定类型的内容集合
          final Map<Long, Map<Short, Long>> contentMap = result.get(contentType);
          // 初始化内容统计集合
          if (!contentMap.containsKey(content)) {
            contentMap.put(content, new HashMap<>());
          }
          contentMap.get(content).put(stats, value);
        });

    return result;
  }
}
