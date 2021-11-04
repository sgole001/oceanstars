package oceanstars.ecommerce.infrastructure.jooq.page;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import oceanstars.ecommerce.common.tools.ReflectUtil;
import org.jooq.SelectQuery;
import org.jooq.SortField;
import org.jooq.TableField;
import org.jooq.impl.DefaultDSLContext;
import org.jooq.impl.TableImpl;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.util.CollectionUtils;

/**
 * 分页包装类
 *
 * @author Clover
 * @version 1.0.0
 * @since 2021/11/4 1:52 下午
 */
public class PageQueryWrapper<T> {

  /**
   * SQL执行上下文
   */
  private final DefaultDSLContext dsl;

  /**
   * 排序用表集合
   */
  private final TableImpl<?>[] sortTables;

  /**
   * 构造器
   *
   * @param dsl SQL执行上下文
   */
  public PageQueryWrapper(DefaultDSLContext dsl, TableImpl<?>... sortTables) {
    this.dsl = dsl;
    this.sortTables = sortTables;
  }

  /**
   * 分页查询
   *
   * @param select      查询执行语句对象
   * @param pageable    分页信息
   * @param recordClass 查询记录行类型
   * @return 分页查询结果
   */
  public Page<T> query(final SelectQuery<?> select, final Pageable pageable, final Class<T> recordClass) {

    // 查询数据总量获取
    int total = 0;

    // 判定是否需要分页
    boolean needPage = Integer.MAX_VALUE > pageable.getPageSize();

    if (needPage) {

      // 查询数据总量获取
      total = dsl.fetchCount(select);

      // 分页条件设置
      select.addLimit(pageable.getOffset(), pageable.getPageSize());
    }

    // 排序设置
    select.addOrderBy(this.getSortFields(pageable.getSort(), select));

    // 分页查询
    final List<T> result = select.fetchInto(recordClass);

    if (!needPage) {
      total = result.size();
    }

    // 包装分页数据返回
    return new PageImpl<>(result, pageable, total);
  }

  /**
   * 获取排序信息
   *
   * @param sortSpecification 排序参数
   * @param select            查询语句对象
   * @return 排序信息
   */
  private Collection<SortField<?>> getSortFields(Sort sortSpecification, SelectQuery<?> select) {

    // 获取既有排序信息
    final List<?> existOrderByList = (List<?>) ReflectUtil.getFieldValue(select, "orderBy");

    // 初始化排序信息列表
    Collection<SortField<?>> querySortFieldList = new ArrayList<>();

    if (sortSpecification == null || sortSpecification.isEmpty()) {

      // 排序未指定的情况，给定默认排序（创建时间）
      if (CollectionUtils.isEmpty(existOrderByList)) {
        sortSpecification = Sort.by(Direction.DESC, "create_by");
      } else {
        return querySortFieldList;
      }
    }

    // 清空默认排序
    existOrderByList.clear();

    sortSpecification.forEach(order -> {

      // 获取排序字段名
      String sortFieldName = order.getProperty().toUpperCase(Locale.ROOT);
      // 获取排序方向（降序|升序）
      Sort.Direction sortDirection = order.getDirection();

      // 获取表字段名
      final List<TableField<?, ?>> tableFields = this.getTableFields(sortFieldName);

      // 转换成JOOQ排序字段
      final List<SortField<?>> querySortFields = this.buildSortFields(tableFields, sortDirection);

      if (!CollectionUtils.isEmpty(querySortFields)) {
        querySortFieldList.addAll(querySortFields);
      }
    });

    return querySortFieldList;
  }

  /**
   * 根据指定排序字段名获取表字段列表
   *
   * @param sortFieldName 排序字段名
   * @return 排序用表字段列表
   */
  private List<TableField<?, ?>> getTableFields(String sortFieldName) {

    // 获取排序表集合
    final List<TableImpl<?>> tables = this.getSortTables();

    if (CollectionUtils.isEmpty(tables)) {
      return Collections.emptyList();
    }

    // 初始化排序字段列表
    final List<TableField<?, ?>> tableFields = new ArrayList<>();

    tables.forEach(table -> {

      // 获取排序表中字段
      TableField<?, ?> field = (TableField<?, ?>) ReflectUtil.getFieldValue(table, sortFieldName);

      if (field != null) {
        tableFields.add(field);
      }
    });

    return tableFields;
  }

  /**
   * 构建排序字段列表返回
   *
   * @param tableFields   排序用表字段列表
   * @param sortDirection 排序方向（降序|升序）
   * @return 排序字段列表
   */
  private List<SortField<?>> buildSortFields(List<TableField<?, ?>> tableFields, Sort.Direction sortDirection) {

    if (CollectionUtils.isEmpty(tableFields)) {
      return Collections.emptyList();
    }

    // 构建排序字段列表返回
    return tableFields.stream().map(tableField -> {
      if (sortDirection == Sort.Direction.ASC) {
        return tableField.asc();
      } else {
        return tableField.desc();
      }
    }).toList();
  }

  public List<TableImpl<?>> getSortTables() {
    if (this.sortTables != null && this.sortTables.length > 0) {
      return Arrays.asList(sortTables);
    }
    return Collections.emptyList();
  }
}
