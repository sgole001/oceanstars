package oceanstars.ecommerce.ecm.domain.content.entity.valueobject;

import java.io.Serial;
import oceanstars.ecommerce.common.domain.ValueObject;

/**
 * 内容统计值对象
 *
 * @author Clover
 * @version 1.0.0
 * @since 2024/1/11 15:35
 */
public class ContentStatisticsValueObject extends ValueObject {

  @Serial
  private static final long serialVersionUID = 476203061321870505L;

  /**
   * 内容访问次数
   */
  private final Long visits;

  /**
   * 内容评论数量
   */
  private final Long comments;

  /**
   * 内容点赞数量
   */
  private final Long likes;

  /**
   * 内容点踩数量
   */
  private final Long dislikes;

  /**
   * 内容收藏数量
   */
  private final Long favorites;

  /**
   * 内容分享数量
   */
  private final Long shares;

  /**
   * 内容下载数量
   */
  private final Long downloads;

  /**
   * 构造函数：初始化成员变量
   *
   * @param builder 构建器
   */
  private ContentStatisticsValueObject(Builder builder) {
    visits = builder.visits;
    comments = builder.comments;
    likes = builder.likes;
    dislikes = builder.dislikes;
    favorites = builder.favorites;
    shares = builder.shares;
    downloads = builder.downloads;
  }

  /**
   * 创建内容统计值对象构建器
   *
   * @return 内容统计值对象构建器
   */
  public static Builder newBuilder() {
    return new Builder();
  }

  public Long getVisits() {
    return visits;
  }

  public Long getComments() {
    return comments;
  }

  public Long getLikes() {
    return likes;
  }

  public Long getDislikes() {
    return dislikes;
  }

  public Long getFavorites() {
    return favorites;
  }

  public Long getShares() {
    return shares;
  }

  public Long getDownloads() {
    return downloads;
  }

  /**
   * 内容统计值对象构建器
   *
   * @author Clover
   * @version 1.0.0
   * @since 2024/1/11 15:52
   */
  public static final class Builder {

    private Long visits = 0L;
    private Long comments = 0L;
    private Long likes = 0L;
    private Long dislikes = 0L;
    private Long favorites = 0L;
    private Long shares = 0L;
    private Long downloads = 0L;

    public Builder() {
    }

    public Builder visits(Long val) {
      visits = val;
      return this;
    }

    public Builder comments(Long val) {
      comments = val;
      return this;
    }

    public Builder likes(Long val) {
      likes = val;
      return this;
    }

    public Builder dislikes(Long val) {
      dislikes = val;
      return this;
    }

    public Builder favorites(Long val) {
      favorites = val;
      return this;
    }

    public Builder shares(Long val) {
      shares = val;
      return this;
    }

    public Builder downloads(Long val) {
      downloads = val;
      return this;
    }

    public ContentStatisticsValueObject build() {
      return new ContentStatisticsValueObject(this);
    }
  }
}
