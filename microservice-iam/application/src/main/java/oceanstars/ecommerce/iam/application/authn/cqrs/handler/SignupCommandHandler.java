package oceanstars.ecommerce.iam.application.authn.cqrs.handler;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.UUID;
import javax.crypto.spec.SecretKeySpec;
import oceanstars.ecommerce.common.cqrs.ICommandHandler;
import oceanstars.ecommerce.common.domain.event.EventBus;
import oceanstars.ecommerce.common.exception.BusinessException;
import oceanstars.ecommerce.common.security.JwtProperties;
import oceanstars.ecommerce.common.tools.CryptoUtil;
import oceanstars.ecommerce.common.tools.JwtUtil;
import oceanstars.ecommerce.iam.api.rpc.v1.dto.authn.IamSignupCommand;
import oceanstars.ecommerce.iam.api.rpc.v1.dto.authn.IamSignupResult;
import oceanstars.ecommerce.iam.constant.enums.IamEnums.IdentityType;
import oceanstars.ecommerce.iam.constant.enums.IamEnums.Message;
import oceanstars.ecommerce.iam.domain.identity.entity.Identity;
import oceanstars.ecommerce.iam.domain.identity.entity.valueobject.Token;
import oceanstars.ecommerce.iam.domain.identity.repository.IdentifyRepository;
import oceanstars.ecommerce.infrastructure.grpc.service.consumer.GrpcClient;
import oceanstars.ecommerce.user.api.rpc.v1.dto.account.UserAccountExistQuery;
import oceanstars.ecommerce.user.api.rpc.v1.dto.account.UserAccountExistResult;
import oceanstars.ecommerce.user.api.rpc.v1.service.account.UserAccountAppServiceGrpc;
import org.springframework.stereotype.Component;
import org.springframework.util.DigestUtils;

/**
 * 注册命令处理类
 *
 * @author Clover
 * @version 1.0.0
 * @since 2024/7/8 18:44
 */
@Component(value = "signupCommandHandler")
public class SignupCommandHandler implements ICommandHandler<IamSignupResult, IamSignupCommand> {

  /**
   * 事件总线
   */
  private final EventBus eventGateway;

  /**
   * 身份资源仓储
   */
  private final IdentifyRepository identifyRepository;

  /**
   * JWT配置属性
   */
  private final JwtProperties accessTokenProp;

  /**
   * 用户账号应用服务
   */
  @GrpcClient("user-account-app-service")
  private UserAccountAppServiceGrpc.UserAccountAppServiceBlockingStub userAccountAppService;

  /**
   * 构造函数
   *
   * @param eventGateway       事件总线
   * @param identifyRepository 账号资源仓储
   */
  public SignupCommandHandler(EventBus eventGateway, IdentifyRepository identifyRepository, JwtProperties accessTokenProp) {
    this.eventGateway = eventGateway;
    this.identifyRepository = identifyRepository;
    this.accessTokenProp = accessTokenProp;
  }

  @Override
  public IamSignupResult handle(IamSignupCommand command) {

    // 构建账号是否存在查询
    final UserAccountExistQuery existQuery = UserAccountExistQuery.newBuilder()
        // 账号域
        .setDomain(command.getDomain())
        // 账号名称
        .setUserName(command.getUserName())
        // 账号访问认证方式
        .setAccess(command.getAccess())
        // 账号访问认证方式类型
        .setAccessType(command.getAccessType())
        // 实施构建
        .build();

    // 查询账号是否存在
    final UserAccountExistResult existResult = this.userAccountAppService.existAccount(existQuery);

    // 如果账号存在则抛出异常
    if (existResult.getExist()) {
      // 异常：申请注册的账号已经存在！
      throw new BusinessException(Message.MSG_BIZ_00000);
    }

    // 生成访问令牌
    final Token accessToken = this.generateAccessToken(command);
    // 生成刷新令牌
    final Token refreshToken = this.generateRefreshToken(command, accessToken.getExpireAt());

    // 构建注册用身份实体
    final Identity identity = Identity.newBuilder(UUID.randomUUID().toString(), IdentityType.USER)
        // 访问令牌
        .accessToken(accessToken)
        // 刷新令牌
        .refreshToken(refreshToken)
        // 实施构建
        .build();

    // 保存身份实体
    this.identifyRepository.save(identity);

    // 发布身份注册事件
//    this.eventGateway.publish(new IdentitySignup(identity,
//        IdentitySignupPayload.newBuilder()
//            // 账号域
//            .domain(command.getDomain())
//            // 账号名称
//            .userName(command.getUserName())
//            // 账号访问认证方式
//            .access(command.getAccess())
//            // 账号访问认证方式类型
//            .accessType(command.getAccessType())
//            // 实施构建
//            .build()));

    return IamSignupResult.newBuilder().setToken(accessToken.getValue()).build();
  }

  /**
   * 生成访问令牌
   *
   * @param command 注册命令
   * @return 访问令牌
   */
  private Token generateAccessToken(final IamSignupCommand command) {

    try {

      // 生成对称密钥
      final SecretKeySpec signSecret = new SecretKeySpec(CryptoUtil.digestKey(accessTokenProp.getSign()), "HmacSHA256");
      // 生成JWT负载
      final Claims payload = Jwts.claims()
          // 标准的JWT负载
          .add(this.buildStandardClaims(command))
          // 账号域
          .add("domain", command.getDomain())
          // 账号访问认证方式
          .add("access", command.getAccess())
          // 账号访问认证方式类型
          .add("accessType", command.getAccessType())
          // 账号名称
          .add("userName", command.getUserName())
          // 是否同意协议和隐私政策
          .add("agree", command.getAgree())
          // 实施构建
          .build();

      // 生成访问令牌
      final String token = JwtUtil.createJws(null, payload, signSecret);

      // 构建访问令牌
      final Token.Builder builder = Token.newBuilder(token).keySpec(signSecret);
      // 如果配置了过期时间Duration，则使用Duration配置计算过期时间
      if (null != accessTokenProp.getExp()) {
        // 计算过期时间
        final Instant expireAt = payload.getIssuedAt().toInstant().plusMillis(accessTokenProp.getExp().toMillis());
        builder.expireAt(LocalDateTime.ofInstant(expireAt, ZoneId.systemDefault()));
      }

      return builder.build();
    } catch (Exception e) {
      // 异常：生成访问令牌失败！
      throw new BusinessException(Message.MSG_BIZ_00001, e);
    }
  }

  /**
   * 生成刷新令牌
   *
   * @param command  注册命令
   * @param expireAt 过期时间
   * @return 刷新令牌
   */
  private Token generateRefreshToken(final IamSignupCommand command, LocalDateTime expireAt) {

    // 生成带盐值的令牌值
    final String tokenValue = accessTokenProp.getSalt() + command.getPassword() + accessTokenProp.getSalt();

    return Token.newBuilder(DigestUtils.md5DigestAsHex(tokenValue.getBytes())).expireAt(expireAt).build();
  }

  /**
   * 构建标准的JWT负载
   *
   * @param command 注册命令
   * @return 标准的JWT负载
   */
  private Claims buildStandardClaims(final IamSignupCommand command) {

    // 获取当前时间
    final Date nowDate = new Date();

    // 计算过期时间
    Date expDate = null;
    // 如果配置了过期时间Duration，则使用Duration配置计算过期时间
    if (null != accessTokenProp.getExp()) {
      expDate = Date.from(nowDate.toInstant().plusMillis(accessTokenProp.getExp().toMillis()));
    }

    // 计算在什么时间之前，该JWT都是不可用的
    Date nbfDate = null;
    // 如果配置了在什么时间之前，该JWT都是不可用的，则使用配置的时间
    if (null != accessTokenProp.getNbf()) {
      nbfDate = Date.from(accessTokenProp.getNbf().atZone(ZoneId.systemDefault()).toInstant());
    }

    return Jwts.claims()
        // JWT的签发者
        .issuer(accessTokenProp.getIss())
        // JWT所面向的用户
        .subject(command.getUserName())
        // 接收JWT的一方
        .audience().add(accessTokenProp.getAud()).and()
        // JWT的过期时间，这个过期时间必须要大于签发时间
        .expiration(expDate)
        // 定义在什么时间之前，该JWT都是不可用的
        .notBefore(nbfDate)
        // JWT的签发时间
        .issuedAt(nowDate)
        // JWT的唯一身份标识(主要用来作为一次性token,从而回避重放攻击)
        // 针对重访攻击，可以使用nonstr，每次请求中添加UUID，在token有效期中缓存所有nonstr，如果有重复，则视为重放攻击
        .id(UUID.randomUUID().toString())
        // 实施构建
        .build();
  }
}
