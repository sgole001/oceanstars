package oceanstars.ecommerce.ecm.repository.content.view.bo;

import oceanstars.ecommerce.ecm.repository.generate.tables.pojos.EcmContentPojo;

/**
 * 内容数据视图BO
 *
 * @author Clover
 * @version 1.0.0
 * @since 2024/4/9 17:33
 */
public class ContentView<T> {

  /**
   * 内容元数据
   */
  private final EcmContentPojo meta;

  /**
   * 内容原生数据
   */
  private final T raw;

  /**
   * 构造函数
   *
   * @param meta 内容元数据
   * @param raw  内容原生数据
   */
  private ContentView(EcmContentPojo meta, T raw) {
    this.meta = meta;
    this.raw = raw;
  }

  /**
   * 创建内容数据视图
   *
   * @param meta 内容元数据
   * @param raw  内容原生数据
   * @param <T>  内容原生数据对象类型
   * @return 内容数据视图
   */
  public static <T> ContentView<T> newView(EcmContentPojo meta, T raw) {
    return new ContentView<>(meta, raw);
  }

  public EcmContentPojo getMeta() {
    return meta;
  }

  public T getRaw() {
    return raw;
  }
}
