package oceanstars.ecommerce.ecm.domain.content.repository.condition;

import java.util.Set;
import oceanstars.ecommerce.common.domain.repository.condition.NullCondition;
import oceanstars.ecommerce.ecm.constant.enums.EcmEnums.ContentMenuType;

/**
 * 内容查询条件：菜单
 *
 * @author Clover
 * @version 1.0.0
 * @since 2024/5/8 10:26
 */
public class MenuFetchCondition extends ContentFetchCondition {

  /**
   * 菜单类型
   */
  private final ContentMenuType menuType;

  /**
   * 菜单对应的功能ID集合
   */
  private final Set<Long> functions;

  /**
   * 菜单隶属
   */
  private final NullCondition<Long> parent;

  /**
   * 菜单是否可见
   */
  private final Boolean visible;

  /**
   * 相邻（前）菜单ID
   */
  private final NullCondition<Long> prev;

  /**
   * 相邻（后）菜单ID
   */
  private final NullCondition<Long> next;

  /**
   * 构造函数
   *
   * @param builder 构造器
   */
  private MenuFetchCondition(Builder builder) {
    super(builder);
    this.menuType = builder.menuType;
    this.functions = builder.functions;
    this.parent = builder.parent;
    this.visible = builder.visible;
    this.prev = builder.prev;
    this.next = builder.next;
  }

  /**
   * 创建构造器
   *
   * @return 构造器
   */
  public static Builder newBuilder() {
    return new Builder();
  }

  public ContentMenuType getMenuType() {
    return menuType;
  }

  public Set<Long> getFunctions() {
    return functions;
  }

  public NullCondition<Long> getParent() {
    return parent;
  }

  public Boolean getVisible() {
    return visible;
  }

  public NullCondition<Long> getPrev() {
    return prev;
  }

  public NullCondition<Long> getNext() {
    return next;
  }

  /**
   * 构建器
   *
   * @author Clover
   * @version 1.0.0
   * @since 2024/5/8 11:12
   */
  public static final class Builder extends ContentFetchCondition.Builder {

    private ContentMenuType menuType;
    private Set<Long> functions;
    private NullCondition<Long> parent;
    private Boolean visible;
    private NullCondition<Long> prev;
    private NullCondition<Long> next;

    public Builder() {
    }

    public Builder menuType(ContentMenuType val) {
      menuType = val;
      return this;
    }

    public Builder functions(Set<Long> val) {
      functions = val;
      return this;
    }

    public Builder parent(NullCondition<Long> val) {
      parent = val;
      return this;
    }

    public Builder visible(Boolean val) {
      visible = val;
      return this;
    }

    public Builder prev(NullCondition<Long> val) {
      prev = val;
      return this;
    }

    public Builder next(NullCondition<Long> val) {
      next = val;
      return this;
    }

    @Override
    public MenuFetchCondition build() {
      return new MenuFetchCondition(this);
    }
  }
}
