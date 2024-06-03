package oceanstars.ecommerce.user.controller.v1.account.handle;

import com.google.protobuf.GeneratedMessageV3;
import oceanstars.ecommerce.common.cqrs.Bus;
import oceanstars.ecommerce.common.restful.BaseRestHandler;
import oceanstars.ecommerce.common.restful.BaseRestResponseData;
import oceanstars.ecommerce.common.restful.RestResponseMessage;
import oceanstars.ecommerce.user.api.rest.v1.request.account.CreateAccountRequestMessage;
import oceanstars.ecommerce.user.api.rpc.v1.dto.account.UserCreateAccountCommand;
import oceanstars.ecommerce.user.api.rpc.v1.dto.account.UserCreateAccountResult;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

/**
 * 创建账号的Restful请求处理
 *
 * @author Clover
 * @version 1.0.0
 * @since 2024/5/28 15:55
 */
@Component(value = "createAccountRestHandler")
public class CreateAccountRestHandler extends BaseRestHandler<CreateAccountRequestMessage> {

  /**
   * CQRS处理总线
   */
  private final Bus cqrsGateway;

  /**
   * 构造函数
   *
   * @param cqrsGateway CQRS处理总线
   */
  public CreateAccountRestHandler(Bus cqrsGateway) {
    this.cqrsGateway = cqrsGateway;
  }

  @Override
  public GeneratedMessageV3[] parsingRequestMessage(CreateAccountRequestMessage restRequestMessage) {

    // 获取验证码（图灵测试：完全自动区分计算机和人类）
    final String captcha = restRequestMessage.getCaptcha();
    // 获取验证码（一次性：One-Time Password）
    final String otp = restRequestMessage.getOtp();

    // 构建创建账号命令请求参数
    final UserCreateAccountCommand userCreateAccountCommand = UserCreateAccountCommand.newBuilder()
        // 账号注册源
        .setSource(restRequestMessage.getSource())
        // 账号注册方式
        .setMean(restRequestMessage.getMean())
        // 账号名
        .setAccount(restRequestMessage.getAccount())
        // 账号密码
        .setPassword(restRequestMessage.getPassword())
        // 实施构建
        .build();

    return new GeneratedMessageV3[]{userCreateAccountCommand};
  }

  @Override
  public GeneratedMessageV3 process(GeneratedMessageV3[] messages) {

    // 获取创建账号命令请求参数
    final UserCreateAccountCommand createAccountCommand = (UserCreateAccountCommand) messages[0];

    // 执行命令
    return cqrsGateway.executeCommand(createAccountCommand);
  }

  @Override
  public RestResponseMessage packageResponseMessage(GeneratedMessageV3 result) {

    // 获取创建账号命令处理结果
    final UserCreateAccountResult createAccountResult = (UserCreateAccountResult) result;

    return RestResponseMessage.newBuilder(HttpStatus.OK)
        // 设置响应数据
        .data(BaseRestResponseData.newBuilder().id(String.valueOf(createAccountResult.getId())).build())
        // 执行构建
        .build();
  }
}
