package oceanstars.ecommerce.ecm.repository.content.strategy.impl;

import static java.util.Objects.requireNonNull;

import java.util.List;
import oceanstars.ecommerce.common.domain.repository.condition.ICondition;
import oceanstars.ecommerce.common.domain.repository.condition.NullCondition;
import oceanstars.ecommerce.common.exception.BusinessException;
import oceanstars.ecommerce.common.spring.ApplicationContextProvider;
import oceanstars.ecommerce.common.tools.PkWorker;
import oceanstars.ecommerce.ecm.constant.enums.EcmEnums.AuditProcessStatus;
import oceanstars.ecommerce.ecm.constant.enums.EcmEnums.ContentMenuType;
import oceanstars.ecommerce.ecm.constant.enums.EcmEnums.ContentType;
import oceanstars.ecommerce.ecm.constant.enums.EcmEnums.Message;
import oceanstars.ecommerce.ecm.domain.content.entity.Content;
import oceanstars.ecommerce.ecm.domain.content.entity.ContentIdentifier;
import oceanstars.ecommerce.ecm.domain.content.entity.valueobject.ContentMenuValueObject;
import oceanstars.ecommerce.ecm.domain.content.repository.condition.MenuFetchCondition;
import oceanstars.ecommerce.ecm.repository.generate.tables.EcmContentMenu;
import oceanstars.ecommerce.ecm.repository.generate.tables.daos.EcmContentMenuDao;
import oceanstars.ecommerce.ecm.repository.generate.tables.pojos.EcmContentMenuPojo;
import org.jooq.Condition;
import org.jooq.SelectJoinStep;
import org.jooq.impl.DSL;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

/**
 * Web功能内容特有数据处理策略
 *
 * @author Clover
 * @version 1.0.0
 * @since 2024/1/17 17:26
 */
public class MenuStrategy extends BaseContentRepositoryStrategy {

  /**
   * 内容菜单表对象
   */
  final static EcmContentMenu T_CONTENT = EcmContentMenu.ECM_CONTENT_MENU.as("content");

  /**
   * 内容菜单DAO
   */
  private static final EcmContentMenuDao DAO = ApplicationContextProvider.getApplicationContext().getBean(EcmContentMenuDao.class);

  @Override
  public List<Content> fetch(ICondition condition) {

    // 校验参数
    requireNonNull(condition, "condition");

    // 构建内容实体
    final List<EcmContentMenuPojo> results = this.query(condition);

    // 判断查询结果是否为空
    if (CollectionUtils.isEmpty(results)) {
      return List.of();
    }

    // 构建内容实体并返回
    return results.stream().map(this::buildContentEntity).toList();
  }

  @Override
  public void create(Content content) {

    // 校验参数
    requireNonNull(content, "content");

    // 根据资产唯一识别码查询内容实体
    EcmContentMenuPojo contentPojo = DAO.fetchOneByName(content.getIdentifier().getName());

    // 判断内容实体是否存在, 存在则抛出业务异常
    if (null != contentPojo) {
      // 获取内容唯一识别对象
      final ContentIdentifier identifier = content.getIdentifier();
      // 业务异常：内容创建失败，{0}类型的名称为{1}的内容已经存在！
      throw new BusinessException(Message.MSG_BIZ_00002, identifier.getType().value(), identifier.getName());
    }

    // 构建内容菜单Pojo
    contentPojo = (EcmContentMenuPojo) this.buildContentPojo(content);

    // 构建查询条件：相同层级菜单下最后一个菜单
    final MenuFetchCondition.Builder conditionBuilder = MenuFetchCondition.newBuilder().next(new NullCondition<>(Boolean.TRUE));
    // 判断是否为根菜单
    if (null == contentPojo.getParent()) {
      conditionBuilder.parent(new NullCondition<>(Boolean.TRUE));
    } else {
      conditionBuilder.parent(new NullCondition<>(contentPojo.getParent()));
    }
    // 执行查询并获取相同层级菜单下最后一个菜单
    final EcmContentMenuPojo lastMenu = this.queryOne(conditionBuilder.build());

    if (null != lastMenu) {
      // 相邻（前）菜单ID
      contentPojo.setPrev(lastMenu.getId());
      // 菜单排序
      contentPojo.setSeq(lastMenu.getSeq() + SEQ_STEP);
      // 相邻（后）菜单ID
      lastMenu.setNext(contentPojo.getId());
      // 更新相邻（前）菜单
      DAO.update(lastMenu);
    }
    // 保存内容菜单数据
    DAO.insert(contentPojo);

    // 委托资产实体
    content.delegate(contentPojo);
  }

  @Override
  public void modify(Content content) {

  }

  @Override
  public Content buildContentEntity(Object contentPojo) {

    // 转换内容Pojo
    final EcmContentMenuPojo menuPojo = (EcmContentMenuPojo) contentPojo;

    // 构建内容原生信息值对象
    final ContentMenuValueObject raw = ContentMenuValueObject.newBuilder()
        // 菜单类型
        .type(ContentMenuType.of(menuPojo.getType().intValue()))
        // 菜单对应的功能ID
        .func(menuPojo.getFunc())
        // 菜单动作：点击菜单后的执行脚本
        .action(menuPojo.getAction())
        // 菜单图标
        .icon(menuPojo.getIcon())
        // 菜单隶属
        .parent(menuPojo.getParent())
        // 菜单是否可见
        .visible(menuPojo.getVisible())
        // 实施构建
        .build();

    // 构建内容菜单值对象
    return Content.newBuilder(ContentType.MENU, menuPojo.getName())
        // 菜单展示名称
        .displayName(menuPojo.getDisplayName())
        // 菜单描述
        .description(menuPojo.getDescription())
        // 菜单状态
        .status(AuditProcessStatus.of(menuPojo.getStatus().intValue()))
        // 菜单原生信息
        .raw(raw)
        // 实施构建
        .build();
  }

  @Override
  public Object buildContentPojo(Content content) {

    // 初始化创建内容菜单Pojo
    final EcmContentMenuPojo menuPojo = new EcmContentMenuPojo();

    // 菜单ID
    menuPojo.setId(PkWorker.build().nextId());
    // 菜单名称
    menuPojo.setName(content.getIdentifier().getName());
    // 菜单展示名称
    menuPojo.setDisplayName(content.getDisplayName());
    // 菜单描述
    menuPojo.setDescription(content.getDescription());
    // 菜单状态
    menuPojo.setStatus(content.getStatus().key().shortValue());

    // 转换菜单原生信息
    final ContentMenuValueObject raw = (ContentMenuValueObject) content.getRaw();

    // 菜单类型
    menuPojo.setType(raw.getType().key().shortValue());
    // 菜单对应的功能ID
    menuPojo.setFunc(raw.getFunc());
    // 菜单动作：点击菜单后的执行脚本
    menuPojo.setAction(raw.getAction());
    // 菜单图标
    menuPojo.setIcon(raw.getIcon());
    // 菜单隶属
    menuPojo.setParent(raw.getParent());
    // 菜单是否可见
    menuPojo.setVisible(raw.getVisible());

    return menuPojo;
  }

  /**
   * 根据查询条件查询内容菜单信息(唯一)
   *
   * @param condition 查询条件
   * @return 内容菜单信息(唯一)
   */
  private EcmContentMenuPojo queryOne(final ICondition condition) {

    // 根据查询条件查询内容菜单信息
    final List<EcmContentMenuPojo> results = this.query(condition);

    // 判断查询结果是否为空
    if (CollectionUtils.isEmpty(results)) {
      return null;
    }

    // 判断查询结果是否为多个
    if (results.size() != 1) {
      // 业务异常：内容查询失败，查询结果不唯一！
      throw new BusinessException(Message.MSG_BIZ_00004);
    }

    return results.getFirst();
  }

  /**
   * 根据查询条件查询内容菜单信息
   *
   * @param condition 查询条件
   * @return 内容菜单信息
   */
  private List<EcmContentMenuPojo> query(final ICondition condition) {

    // 转换查询条件为菜单查询条件
    final MenuFetchCondition fetchCondition = (MenuFetchCondition) condition;

    // 初始化查询条件
    Condition searchCondition = DSL.trueCondition();
    // 菜单ID
    if (null != fetchCondition.getId()) {
      searchCondition = searchCondition.and(T_CONTENT.ID.eq(fetchCondition.getId()));
    }
    // 菜单名称
    if (StringUtils.hasText(fetchCondition.getName())) {
      searchCondition = searchCondition.and(T_CONTENT.NAME.likeIgnoreCase(fetchCondition.getName().trim()));
    }
    // 菜单展示名称
    if (StringUtils.hasText(fetchCondition.getDisplayName())) {
      searchCondition = searchCondition.and(T_CONTENT.DISPLAY_NAME.likeIgnoreCase(fetchCondition.getDisplayName().trim()));
    }
    // 菜单状态
    if (null != fetchCondition.getStatus()) {
      searchCondition = searchCondition.and(T_CONTENT.STATUS.eq(fetchCondition.getStatus().key().shortValue()));
    }
    // 菜单类型
    if (null != fetchCondition.getMenuType()) {
      searchCondition = searchCondition.and(T_CONTENT.TYPE.eq(fetchCondition.getMenuType().key().shortValue()));
    }
    // 菜单对应的功能ID
    if (!CollectionUtils.isEmpty(fetchCondition.getFunctions())) {
      if (fetchCondition.getFunctions().size() == 1) {
        searchCondition = searchCondition.and(T_CONTENT.FUNC.eq(fetchCondition.getFunctions().stream().findFirst().orElseThrow()));
      } else {
        searchCondition = searchCondition.and(T_CONTENT.FUNC.in(fetchCondition.getFunctions()));
      }
    }
    // 菜单隶属
    if (null != fetchCondition.getParent()) {
      if (fetchCondition.getParent().checkNull()) {
        searchCondition = searchCondition.and(T_CONTENT.PARENT.isNull());
      } else {
        searchCondition = searchCondition.and(T_CONTENT.PARENT.eq(fetchCondition.getParent().getValue()));
      }
    }
    // 菜单是否可见
    if (null != fetchCondition.getVisible()) {
      searchCondition = searchCondition.and(T_CONTENT.VISIBLE.eq(fetchCondition.getVisible()));
    }
    // 相邻（前）菜单ID
    if (null != fetchCondition.getPrev()) {
      if (fetchCondition.getPrev().checkNull()) {
        searchCondition = searchCondition.and(T_CONTENT.PREV.isNull());
      } else {
        searchCondition = searchCondition.and(T_CONTENT.PREV.eq(fetchCondition.getPrev().getValue()));
      }

    }
    // 相邻（后）菜单ID
    if (null != fetchCondition.getNext()) {
      if (fetchCondition.getNext().checkNull()) {
        searchCondition = searchCondition.and(T_CONTENT.NEXT.isNull());
      } else {
        searchCondition = searchCondition.and(T_CONTENT.NEXT.eq(fetchCondition.getNext().getValue()));
      }
    }
    // 菜单创建开始时间
    if (null != fetchCondition.getCreateStartTime()) {
      searchCondition = searchCondition.and(T_CONTENT.CREATE_AT.ge(fetchCondition.getCreateStartTime()));
    }
    // 菜单创建结束时间
    if (null != fetchCondition.getCreateEndTime()) {
      searchCondition = searchCondition.and(T_CONTENT.CREATE_AT.le(fetchCondition.getCreateEndTime()));
    }
    // 菜单更新开始时间
    if (null != fetchCondition.getUpdateStartTime()) {
      searchCondition = searchCondition.and(T_CONTENT.UPDATE_AT.ge(fetchCondition.getUpdateStartTime()));
    }
    // 菜单更新结束时间
    if (null != fetchCondition.getUpdateEndTime()) {
      searchCondition = searchCondition.and(T_CONTENT.UPDATE_AT.le(fetchCondition.getUpdateEndTime()));
    }

    // 构建内容主表查询
    SelectJoinStep<?> query = DSL_CONTEXT
        // 内容主表字段
        .selectDistinct(T_CONTENT.fields())
        // 内容主表
        .from(T_CONTENT);

    // 菜单标签
    if (!CollectionUtils.isEmpty(fetchCondition.getTags())) {
      query = query.join(REL_CONTENT_TAG).on(REL_CONTENT_TAG.CONTENT.eq(T_CONTENT.ID)
          .and(REL_CONTENT_TAG.TAG.in(fetchCondition.getTags())));
    }
    // 菜单分类
    if (!CollectionUtils.isEmpty(fetchCondition.getCategories())) {
      query = query.join(REL_CONTENT_CATEGORY).on(REL_CONTENT_CATEGORY.CONTENT.eq(T_CONTENT.ID)
          .and(REL_CONTENT_CATEGORY.CATEGORY.in(fetchCondition.getCategories())));
    }

    // 构建内容实体
    return query
        // 查询条件
        .where(searchCondition)
        // 排序
        .orderBy(T_CONTENT.PARENT, T_CONTENT.SEQ.asc()).fetchInto(EcmContentMenuPojo.class);
  }
}
