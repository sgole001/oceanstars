package oceanstars.ecommerce.common.controller;

import oceanstars.ecommerce.common.api.RestResponseMessage;
import org.springframework.http.HttpStatus;

/**
 * RESTAPI基层控制类
 *
 * @author Clover
 * @version 1.0.0
 * @since 2021/11/3 3:35 下午
 */
public class RestApiController {

  /**
   * RestfulAPI处理状态设定
   *
   * @return 响应数据接口
   */
  protected <E, T extends RestResponseMessage<E>> T processResult(final Class<T> rescls, E data) throws Exception {

    // 响应数据接口
    final T responseDate = rescls.getDeclaredConstructor().newInstance();

    // HTTP响应返回状态
    responseDate.setHttpStatus(HttpStatus.OK.toString());
    // 响应返回主体数据信息
    responseDate.setData(data);

    return responseDate;
  }
}
