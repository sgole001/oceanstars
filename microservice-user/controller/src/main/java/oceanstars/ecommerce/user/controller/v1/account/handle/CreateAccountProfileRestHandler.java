package oceanstars.ecommerce.user.controller.v1.account.handle;

import com.google.protobuf.GeneratedMessageV3;
import com.google.protobuf.Timestamp;
import java.time.Instant;
import java.time.ZoneId;
import oceanstars.ecommerce.common.cqrs.Bus;
import oceanstars.ecommerce.common.restful.BaseRestHandler;
import oceanstars.ecommerce.common.restful.BaseRestResponseData;
import oceanstars.ecommerce.common.restful.RestResponseMessage;
import oceanstars.ecommerce.user.api.rest.v1.request.account.CreateAccountProfileRequestMessage;
import oceanstars.ecommerce.user.api.rpc.v1.dto.account.UserCreateAccountProfileCommand;
import oceanstars.ecommerce.user.api.rpc.v1.dto.account.UserCreateAccountProfileResult;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

/**
 * 创建账号简况的Restful请求处理
 *
 * @author Clover
 * @version 1.0.0
 * @since 2024/5/28 15:58
 */
@Component(value = "createAccountProfileRestHandler")
public class CreateAccountProfileRestHandler extends BaseRestHandler<CreateAccountProfileRequestMessage> {

  /**
   * CQRS处理总线
   */
  private final Bus cqrsGateway;

  /**
   * 构造函数
   *
   * @param cqrsGateway CQRS处理总线
   */
  public CreateAccountProfileRestHandler(Bus cqrsGateway) {
    this.cqrsGateway = cqrsGateway;
  }

  @Override
  public GeneratedMessageV3[] parsingRequestMessage(CreateAccountProfileRequestMessage restRequestMessage) {

    // 构建创建账号命令请求参数
    final UserCreateAccountProfileCommand.Builder userCreateAccountCommandBuilder = UserCreateAccountProfileCommand.newBuilder()
        // 账号ID
        .setAccount(restRequestMessage.getAccount())
        // 姓
        .setFirstName(restRequestMessage.getFirstName())
        // 名
        .setLastName(restRequestMessage.getLastName())
        // 昵称
        .setNickName(restRequestMessage.getNickName())
        // 头像
        .setAvatar(restRequestMessage.getAvatar());
    // 如果生日不为NULL，日期转化为时间戳设定
    if (null != restRequestMessage.getBirthday()) {
      // 获取时间戳
      final Instant instant = restRequestMessage.getBirthday().atStartOfDay().atZone(ZoneId.systemDefault()).toInstant();
      // 设置生日
      userCreateAccountCommandBuilder.setBirthday(Timestamp.newBuilder()
          .setSeconds(instant.getEpochSecond())
          .setNanos(instant.getNano()).build());
    }

    return new GeneratedMessageV3[]{userCreateAccountCommandBuilder.build()};
  }

  @Override
  public GeneratedMessageV3 process(GeneratedMessageV3[] messages) {

    // 获取创建账号简况命令请求参数
    final UserCreateAccountProfileCommand createAccountProfileCommand = (UserCreateAccountProfileCommand) messages[0];

    // 执行命令
    return cqrsGateway.executeCommand(createAccountProfileCommand);
  }

  @Override
  public RestResponseMessage packageResponseMessage(GeneratedMessageV3 result) {

    // 获取创建账号命令处理结果
    final UserCreateAccountProfileResult createAccountProfileResult = (UserCreateAccountProfileResult) result;

    return RestResponseMessage.newBuilder(HttpStatus.OK)
        // 设置响应数据
        .data(BaseRestResponseData.newBuilder().id(String.valueOf(createAccountProfileResult.getId())).build())
        // 执行构建
        .build();
  }
}