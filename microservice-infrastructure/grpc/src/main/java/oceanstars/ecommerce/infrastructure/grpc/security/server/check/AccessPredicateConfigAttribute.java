package oceanstars.ecommerce.infrastructure.grpc.security.server.check;

import java.io.Serial;
import java.util.Objects;
import org.springframework.security.access.ConfigAttribute;

/**
 * 自定义ConfigAttribute：gRPC访问权限Predicate接口，配合AccessPredicateVoter实施访问权限控制
 *
 * @param accessPredicate gRPC访问权限Predicate接口
 * @author Clover
 * @version 1.0.0
 * @since 2024/1/21 12:02
 */
public record AccessPredicateConfigAttribute(AccessPredicate accessPredicate) implements ConfigAttribute {

  @Serial
  private static final long serialVersionUID = 9150741407552037120L;

  /**
   * 构造函数
   *
   * @param accessPredicate gRPC访问权限Predicate接口
   */
  public AccessPredicateConfigAttribute {
  }

  @Override
  public String getAttribute() {
    return null;
  }

  @Override
  public boolean equals(final Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    final AccessPredicateConfigAttribute other = (AccessPredicateConfigAttribute) obj;
    return Objects.equals(this.accessPredicate, other.accessPredicate);
  }

  @Override
  public String toString() {
    return "AccessPredicateConfigAttribute [" + this.accessPredicate + "]";
  }
}
