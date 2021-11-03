package oceanstars.ecommerce.common.controller;

import java.io.Serial;

/**
 * API响应解析类
 *
 * @author Clover
 * @version 1.0.0
 * @since 2021/11/3 3:34 下午
 */
public class ResponseParsing extends Parsing {

  @Serial
  private static final long serialVersionUID = 7911430090851802901L;

  public ResponseParsing(Object... objs) {
    if (null != objs && objs.length > 0) {

      for (Object obj : objs) {
        this.put(String.valueOf(obj.hashCode()), obj);
      }
    }
  }
}
