package oceanstars.ecommerce.ecm.constant.enums;

import oceanstars.ecommerce.common.constant.IEnum;
import oceanstars.ecommerce.common.tools.MessageUtil;

/**
 * ECM服务枚举类
 *
 * @author Clover
 * @version 1.0.0
 * @since 2024/1/11 10:49
 */

public class EcmEnums {

  /**
   * 资产类型枚举
   *
   * @author Clover
   * @version 1.0.0
   * @since 2024/4/3 10:51
   */
  public enum AssetType implements IEnum<Integer, String, AssetType> {

    // 多媒体: 图片
    MEDIA_IMAGE(0, "MEDIA - IMAGE"),

    // 多媒体: 视频
    MEDIA_VIDEO(1, "MEDIA - VIDEO"),

    // 多媒体: 音频
    MEDIA_AUDIO(2, "MEDIA - AUDIO"),

    // 软件: 文档
    SOFTWARE_DOCUMENT(3, "SOFTWARE - DOCUMENT"),

    // 软件: 源代码
    SOFTWARE_SOURCE_CODE(4, "SOFTWARE - SOURCE CODE"),

    // 数据
    DATA(5, "DATA"),

    // 知识产权: 功能列表
    INTELLECTUAL_PROPERTY_FUNCTION(6, "INTELLECTUAL PROPERTY - FUNCTION"),
    ;

    /**
     * 枚举编号
     */
    private final Integer code;

    /**
     * 枚举显示名
     */
    private final String name;

    /**
     * 构造函数
     *
     * @param code 枚举编号
     * @param name 枚举显示名
     */
    AssetType(Integer code, String name) {
      this.code = code;
      this.name = name;
    }

    /**
     * 根据枚举KEY获取枚举对象
     *
     * @param key 枚举项KEY
     * @return 枚举对象
     */
    public static AssetType of(Integer key) {
      for (AssetType item : AssetType.values()) {
        if (item.code.equals(key)) {
          return item;
        }
      }
      return null;
    }

    @Override
    public AssetType get() {
      return this;
    }

    @Override
    public Integer key() {
      return this.code;
    }

    @Override
    public String value() {
      return this.name;
    }
  }

  /**
   * 分类类型枚举
   *
   * @author Clover
   * @version 1.0.0
   * @since 2024/4/17 16:26
   */
  public enum CategoryType implements IEnum<Integer, String, CategoryType> {

    // 默认分类
    DEFAULT(0, "Default"),

    // 内容空间分类
    CONTENT_SPACE(1, "Content Space"),
    ;

    /**
     * 枚举编号
     */
    private final Integer code;

    /**
     * 枚举显示名
     */
    private final String name;

    /**
     * 构造函数
     *
     * @param code 枚举编号
     * @param name 枚举显示名
     */
    CategoryType(Integer code, String name) {
      this.code = code;
      this.name = name;
    }

    /**
     * 根据枚举KEY获取枚举对象
     *
     * @param key 枚举项KEY
     * @return 枚举对象
     */
    public static CategoryType of(Integer key) {
      for (CategoryType item : CategoryType.values()) {
        if (item.code.equals(key)) {
          return item;
        }
      }
      return null;
    }

    @Override
    public CategoryType get() {
      return this;
    }

    @Override
    public Integer key() {
      return this.code;
    }

    @Override
    public String value() {
      return this.name;
    }
  }

  /**
   * 内容类型枚举
   *
   * @author Clover
   * @version 1.0.0
   * @since 2024/4/3 10:51
   */
  public enum ContentType implements IEnum<Integer, String, ContentType> {

    // 功能菜单
    FUNCTION_MENU(0, "FUNCTION MENU"),
    ;

    /**
     * 枚举编号
     */
    private final Integer code;

    /**
     * 枚举显示名
     */
    private final String name;

    /**
     * 构造函数
     *
     * @param code 枚举编号
     * @param name 枚举显示名
     */
    ContentType(Integer code, String name) {
      this.code = code;
      this.name = name;
    }

    /**
     * 根据枚举KEY获取枚举对象
     *
     * @param key 枚举项KEY
     * @return 枚举对象
     */
    public static ContentType of(Integer key) {
      for (ContentType item : ContentType.values()) {
        if (item.code.equals(key)) {
          return item;
        }
      }
      return null;
    }

    @Override
    public ContentType get() {
      return this;
    }

    @Override
    public Integer key() {
      return this.code;
    }

    @Override
    public String value() {
      return this.name;
    }
  }

  /**
   * 审核流程状态枚举
   *
   * @author Clover
   * @version 1.0.0
   * @since 2024/4/3 10:52
   */
  public enum AuditProcessStatus implements IEnum<Integer, String, AuditProcessStatus> {

    // 草稿
    DRAFT(0, "DRAFT"),

    // 待审核
    PENDING(1, "PENDING"),

    // 审核通过
    PASS(2, "PASS"),

    // 审核不通过
    REJECT(3, "REJECT"),

    // 已发布
    PUBLISHED(4, "PUBLISHED"),

    // 已下架
    OFFLINE(5, "OFFLINE"),

    // 已删除
    DELETED(6, "DELETED"),
    ;

    /**
     * 枚举编号
     */
    private final Integer code;

    /**
     * 枚举显示名
     */
    private final String name;

    /**
     * 构造函数
     *
     * @param code 枚举编号
     * @param name 枚举显示名
     */
    AuditProcessStatus(Integer code, String name) {
      this.code = code;
      this.name = name;
    }

    /**
     * 根据枚举KEY获取枚举对象
     *
     * @param key 枚举项KEY
     * @return 枚举对象
     */
    public static AuditProcessStatus of(Integer key) {
      for (AuditProcessStatus item : AuditProcessStatus.values()) {
        if (item.code.equals(key)) {
          return item;
        }
      }
      return null;
    }

    @Override
    public AuditProcessStatus get() {
      return this;
    }

    @Override
    public Integer key() {
      return this.code;
    }

    @Override
    public String value() {
      return this.name;
    }
  }

  /**
   * 资产状态枚举
   *
   * @author Clover
   * @version 1.0.0
   * @since 2024/4/3 17:06
   */
  public enum AssetStatus implements IEnum<Integer, String, AssetStatus> {

    // 已删除
    DELETED(0, "DELETED"),

    // 已发布
    PUBLISHED(1, "PUBLISHED"),
    ;


    /**
     * 枚举编号
     */
    private final Integer code;

    /**
     * 枚举显示名
     */
    private final String name;

    /**
     * 构造函数
     *
     * @param code 枚举编号
     * @param name 枚举显示名
     */
    AssetStatus(Integer code, String name) {
      this.code = code;
      this.name = name;
    }

    /**
     * 根据枚举KEY获取枚举对象
     *
     * @param key 枚举项KEY
     * @return 枚举对象
     */
    public static AssetStatus of(Integer key) {
      for (AssetStatus item : AssetStatus.values()) {
        if (item.code.equals(key)) {
          return item;
        }
      }
      return null;
    }

    @Override
    public AssetStatus get() {
      return this;
    }

    @Override
    public Integer key() {
      return this.code;
    }

    @Override
    public String value() {
      return this.name;
    }
  }

  /**
   * 消息枚举
   *
   * @author Clover
   * @version 1.0.0
   * @since 2024/1/11 10:49
   */
  public enum Message implements IEnum<String, String, Message> {

    /**
     * 分类创建失败，名称为{0}的分类已经存在！
     */
    MSG_BIZ_00000("MSG_BIZ_00000"),
    /**
     * 标签创建失败，名称为{0}的标签已经存在！
     */
    MSG_BIZ_00001("MSG_BIZ_00001"),
    /**
     * 内容创建失败，{0}类型的名称为{1}的内容已经存在！
     */
    MSG_BIZ_00002("MSG_BIZ_00002"),
    /**
     * 指定的排序位置{0}不存在，无法进行排序！
     */
    MSG_BIZ_00003("MSG_BIZ_00003"),
    /**
     * 指定的排序对象{0}不存在，无法进行排序！
     */
    MSG_BIZ_00004("MSG_BIZ_00004"),
    /**
     * 指定的排序对象{0}中{1}不存在，无法进行排序！
     */
    MSG_BIZ_00005("MSG_BIZ_0005"),
    ;

    /**
     * 枚举编号
     */
    private final String code;

    /**
     * 构造函数
     *
     * @param code 枚举编号
     */
    Message(String code) {
      this.code = code;
    }

    @Override
    public Message get() {
      return this;
    }

    @Override
    public String key() {
      return this.code;
    }

    @Override
    public String value() {
      return MessageUtil.ACCESSOR.getMessage(this.code);
    }

    public String value(Object[] arg) {
      return MessageUtil.ACCESSOR.getMessage(this.code, arg);
    }
  }
}
