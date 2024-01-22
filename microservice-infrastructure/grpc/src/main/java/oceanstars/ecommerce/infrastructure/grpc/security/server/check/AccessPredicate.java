package oceanstars.ecommerce.infrastructure.grpc.security.server.check;

import com.google.common.collect.ImmutableSet;
import io.grpc.Grpc;
import io.grpc.ServerCall;
import io.grpc.inprocess.InProcessSocketAddress;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.Arrays;
import java.util.Collection;
import java.util.Set;
import java.util.function.BiPredicate;
import java.util.function.Predicate;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

/**
 * gRPC访问权限Predicate接口
 *
 * @author Clover
 * @version 1.0.0
 * @since 2024/1/21 11:45
 */
public interface AccessPredicate extends BiPredicate<Authentication, ServerCall<?, ?>> {

  @Override
  boolean test(Authentication authentication, ServerCall<?, ?> serverCall);

  @Override
  default AccessPredicate negate() {
    return (a, c) -> !test(a, c);
  }

  @Override
  default AccessPredicate and(final BiPredicate<? super Authentication, ? super ServerCall<?, ?>> other) {
    return (a, c) -> test(a, c) && other.test(a, c);
  }

  @Override
  default AccessPredicate or(final BiPredicate<? super Authentication, ? super ServerCall<?, ?>> other) {
    return (a, c) -> test(a, c) || other.test(a, c);
  }

  default AccessPredicate and(final Predicate<? super Authentication> other) {
    return (a, c) -> test(a, c) && other.test(a);
  }

  default AccessPredicate or(final Predicate<? super Authentication> other) {
    return (a, c) -> test(a, c) || other.test(a);
  }

  static AccessPredicate authenticated() {
    return (a, c) -> true;
  }

  static AccessPredicate fullyAuthenticated() {
    return (a, c) -> !(a instanceof AnonymousAuthenticationToken);
  }

  static AccessPredicate denyAll() {
    return (a, c) -> false;
  }

  static AccessPredicate hasRole(final String role) {
    return (a, c) -> {
      for (final GrantedAuthority authority : a.getAuthorities()) {
        if (role.equals(authority.getAuthority())) {
          return true;
        }
      }
      return false;
    };
  }

  static AccessPredicate hasAuthority(final GrantedAuthority authority) {
    return (a, c) -> {
      for (final GrantedAuthority val : a.getAuthorities()) {
        if (authority.equals(val)) {
          return true;
        }
      }
      return false;
    };
  }

  static AccessPredicate hasAnyRole(final String... roles) {
    return hasAnyRole(Arrays.asList(roles));
  }

  static AccessPredicate hasAnyRole(final Collection<String> roles) {
    final Set<String> immutableRoles = ImmutableSet.copyOf(roles);
    return (a, c) -> {
      for (final GrantedAuthority authority : a.getAuthorities()) {
        if (immutableRoles.contains(authority.getAuthority())) {
          return true;
        }
      }
      return false;
    };
  }

  static AccessPredicate hasAnyAuthority(final GrantedAuthority... authorities) {
    return hasAnyAuthority(Arrays.asList(authorities));
  }

  static AccessPredicate hasAnyAuthority(final Collection<GrantedAuthority> authorities) {
    final Set<GrantedAuthority> immutableRoles = ImmutableSet.copyOf(authorities);
    return (a, c) -> {
      for (final GrantedAuthority authority : a.getAuthorities()) {
        if (immutableRoles.contains(authority)) {
          return true;
        }
      }
      return false;
    };
  }

  static AccessPredicate hasAllRoles(final String... roles) {
    return hasAnyRole(Arrays.asList(roles));
  }

  static AccessPredicate hasAllRoles(final Collection<String> roles) {
    final Set<String> immutableRoles = ImmutableSet.copyOf(roles);
    return (a, c) -> {
      for (final GrantedAuthority authority : a.getAuthorities()) {
        if (!immutableRoles.contains(authority.getAuthority())) {
          return false;
        }
      }
      return true;
    };
  }

  static AccessPredicate hasAllAuthorities(final GrantedAuthority... authorities) {
    return hasAllAuthorities(Arrays.asList(authorities));
  }

  static AccessPredicate hasAllAuthorities(final Collection<GrantedAuthority> authorities) {
    final Set<GrantedAuthority> immutableRoles = ImmutableSet.copyOf(authorities);
    return (a, c) -> {
      for (final GrantedAuthority authority : a.getAuthorities()) {
        if (!immutableRoles.contains(authority)) {
          return false;
        }
      }
      return true;
    };
  }

  static AccessPredicate fromClientAddress(final Predicate<? super SocketAddress> remoteAddressCheck) {
    return (a, c) -> remoteAddressCheck.test(c.getAttributes().get(Grpc.TRANSPORT_ATTR_REMOTE_ADDR));
  }

  static AccessPredicate toServerAddress(final Predicate<? super SocketAddress> localAddressCheck) {
    return (a, c) -> localAddressCheck.test(c.getAttributes().get(Grpc.TRANSPORT_ATTR_LOCAL_ADDR));
  }

  /**
   * SocketAddress Predicate接口
   *
   * @author Clover
   * @version 1.0.0
   * @since 2024/1/21 11:48
   */
  interface SocketPredicate extends Predicate<SocketAddress> {

    /**
     * 判断SocketAddress是否为指定类型
     *
     * @param type SocketAddress类型
     * @return SocketAddress是否为指定类型的函数
     */
    static SocketPredicate type(final Class<? extends SocketAddress> type) {
      return type::isInstance;
    }

    /**
     * 判断SocketAddress是否为指定类型，并且满足条件
     *
     * @param type      SocketAddress类型
     * @param condition 条件
     * @param <T>       SocketAddress类型
     * @return SocketAddress是否为指定类型的函数
     */
    @SuppressWarnings("unchecked")
    static <T> SocketPredicate typeAnd(final Class<T> type, final Predicate<T> condition) {
      return s -> type.isInstance(s) && condition.test((T) s);
    }

    /**
     * 判断SocketAddress是否为InProcessSocketAddress
     *
     * @return SocketAddress是否为InProcessSocketAddress的函数
     */
    static SocketPredicate inProcess() {
      return type(InProcessSocketAddress.class);
    }

    /**
     * 判断SocketAddress是否为InProcessSocketAddress，并且满足条件
     *
     * @param name 名称
     * @return SocketAddress是否为InProcessSocketAddress的函数
     */
    static SocketPredicate inProcess(final String name) {
      return typeAnd(InProcessSocketAddress.class, s -> name.equals(s.getName()));
    }

    /**
     * 判断SocketAddress是否为InetSocketAddress
     *
     * @return SocketAddress是否为InetSocketAddress的函数
     */
    static SocketPredicate inet() {
      return type(InetSocketAddress.class);
    }
  }
}
