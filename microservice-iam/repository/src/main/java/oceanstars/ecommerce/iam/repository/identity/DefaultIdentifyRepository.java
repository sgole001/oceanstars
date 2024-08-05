package oceanstars.ecommerce.iam.repository.identity;

import static java.util.Objects.requireNonNull;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import oceanstars.ecommerce.common.domain.repository.condition.ICondition;
import oceanstars.ecommerce.iam.domain.identity.entity.Identity;
import oceanstars.ecommerce.iam.domain.identity.entity.valueobject.Token;
import oceanstars.ecommerce.iam.domain.identity.repository.IdentifyRepository;
import oceanstars.ecommerce.infrastructure.redis.tools.RedisUtil;
import org.springframework.stereotype.Repository;

/**
 * 身份聚合仓储实现类
 *
 * @author Clover
 * @version 1.0.0
 * @since 2024/7/11 12:01
 */
@Repository
public class DefaultIdentifyRepository implements IdentifyRepository {

  @Override
  public Optional<Identity> findOne(ICondition condition) {
    return Optional.empty();
  }

  @Override
  public List<Identity> find(ICondition condition) {
    return List.of();
  }

  @Override
  public void save(Identity identity) {

    // 校验参数
    requireNonNull(identity, "identity");

    // 获取访问令牌
    final Token accessToken = identity.getAccessToken();
    // 获取刷新令牌
    final Token refreshToken = identity.getRefreshToken();

    RedisUtil.executeInTransaction(operations -> {

      // 将访问令牌存储到Redis
      long accessTokenTimeout = ChronoUnit.SECONDS.between(LocalDateTime.now(), accessToken.getExpireAt());
      operations.opsForValue().set(accessToken.getValue(), accessToken, accessTokenTimeout, TimeUnit.SECONDS);

      // 将刷新令牌存储到Redis
      long refreshTokenTimeout = ChronoUnit.SECONDS.between(LocalDateTime.now(), refreshToken.getExpireAt());
      operations.opsForValue().set(refreshToken.getValue(), refreshToken, refreshTokenTimeout, TimeUnit.SECONDS);
    });
  }

  @Override
  public void delete(Identity identity) {

  }
}
