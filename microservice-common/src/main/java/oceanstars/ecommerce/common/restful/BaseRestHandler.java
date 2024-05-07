package oceanstars.ecommerce.common.restful;

import com.google.protobuf.GeneratedMessageV3;

/**
 * Restful API程序处理基础类
 *
 * @author Clover
 * @version 1.0.0
 * @since 2022/1/14 12:13 PM
 */
public abstract class BaseRestHandler<E extends RestRequestMessage> implements IRestHandler<RestResponseMessage, E> {

  @Override
  public RestResponseMessage handle(final E restRequestMessage) {

    // 解析请求参数
    final GeneratedMessageV3[] messages = this.parsingRequestMessage(restRequestMessage);

    // 业务处理
    final GeneratedMessageV3 result = this.process(messages);

    // 封装响应参数
    return this.packageResponseMessage(result);
  }

  /**
   * 解析请求参数
   *
   * @param restRequestMessage 请求参数
   * @return 业务处理所需数据集
   */
  public abstract GeneratedMessageV3[] parsingRequestMessage(final E restRequestMessage);

  /**
   * 业务处理
   *
   * @param messages 业务处理所需数据集
   * @return 业务处理结果数据
   */
  public abstract GeneratedMessageV3 process(final GeneratedMessageV3[] messages);

  /**
   * 封装响应参数
   *
   * @param result 业务处理结果数据
   * @return 响应参数
   */
  public abstract RestResponseMessage packageResponseMessage(final GeneratedMessageV3 result);
}
