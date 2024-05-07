package oceanstars.ecommerce.ecm.repository.content.view.condition;

import oceanstars.ecommerce.ecm.constant.enums.EcmEnums.AuditProcessStatus;

/**
 * 内容数据查询条件
 *
 * @author Clover
 * @version 1.0.0
 * @since 2024/4/12 14:13
 */
public class ContentQueryCondition {

  /**
   * 内容ID
   */
  private Long id;

  /**
   * 内容原始信息ID
   */
  private Long raw;

  /**
   * 内容名称
   */
  private String name;

  /**
   * 内容展示名称
   */
  private String displayName;

  /**
   * 内容描述
   */
  private String description;

  /**
   * 内容访问次数
   */
  private Long visits;

  /**
   * 内容评论数量
   */
  private Long comments;

  /**
   * 内容点赞数量
   */
  private Long likes;

  /**
   * 内容点踩数量
   */
  private Long dislikes;

  /**
   * 内容收藏数量
   */
  private Long favorites;

  /**
   * 内容分享数量
   */
  private Long shares;

  /**
   * 内容下载数量
   */
  private Long downloads;

  /**
   * 审核流程状态
   */
  private AuditProcessStatus status;
}
