package oceanstars.ecommerce.iam.controller.v1.authn.handle;

import com.google.protobuf.GeneratedMessageV3;
import oceanstars.ecommerce.common.cqrs.Bus;
import oceanstars.ecommerce.common.restful.BaseRestHandler;
import oceanstars.ecommerce.common.restful.BaseRestResponseData;
import oceanstars.ecommerce.common.restful.RestResponseMessage;
import oceanstars.ecommerce.iam.api.rest.v1.request.authn.SignupRequestMessage;
import oceanstars.ecommerce.iam.api.rpc.v1.dto.authn.IamSignupCommand;
import oceanstars.ecommerce.iam.api.rpc.v1.dto.authn.IamSignupResult;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

/**
 * <此类的功能说明>
 *
 * @author Clover
 * @version 1.0.0
 * @since 2024/7/8 17:18
 */
@Component(value = "registerRestHandler")
public class SignupRestHandler extends BaseRestHandler<SignupRequestMessage> {

  /**
   * CQRS处理总线
   */
  private final Bus cqrsGateway;

  /**
   * 构造函数
   *
   * @param cqrsGateway CQRS处理总线
   */
  public SignupRestHandler(Bus cqrsGateway) {
    this.cqrsGateway = cqrsGateway;
  }

  @Override
  public GeneratedMessageV3[] parsingRequestMessage(SignupRequestMessage restRequestMessage) {

    // 构建注册命令请求参数
    final IamSignupCommand iamSignupCommand = IamSignupCommand.newBuilder()
        // 账号域
        .setDomain(Integer.parseInt(restRequestMessage.getDomain()))
        // 账号访问认证方式
        .setAccess(restRequestMessage.getAccess())
        // 账号访问认证方式类型
        .setAccessType(restRequestMessage.getAccessType())
        // 密码
        .setPassword(restRequestMessage.getPassword())
        // 用户名
        .setUserName(restRequestMessage.getUserName())
        // 是否同意协议和隐私政策
        .setAgree(restRequestMessage.getAgree())
        // 实施构建
        .build();

    return new GeneratedMessageV3[]{iamSignupCommand};
  }

  @Override
  public GeneratedMessageV3 process(GeneratedMessageV3[] messages) {

    // 获取注册命令请求参数
    final IamSignupCommand iamSignupCommand = (IamSignupCommand) messages[0];

    return cqrsGateway.executeCommand(iamSignupCommand);
  }

  @Override
  public RestResponseMessage packageResponseMessage(GeneratedMessageV3 result) {

    // 获取注册命令处理结果
    final IamSignupResult iamSignupResult = (IamSignupResult) result;

    return RestResponseMessage.newBuilder(HttpStatus.OK)
        // 设置响应数据
        .data(BaseRestResponseData.newBuilder().id(iamSignupResult.getToken()).build())
        // 执行构建
        .build();
  }
}
