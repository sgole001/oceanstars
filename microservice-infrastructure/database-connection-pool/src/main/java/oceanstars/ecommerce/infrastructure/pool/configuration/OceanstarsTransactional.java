package oceanstars.ecommerce.infrastructure.pool.configuration;


import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springframework.aot.hint.annotation.Reflective;
import org.springframework.core.annotation.AliasFor;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Spring原生事务注解扩展，支持多数据源事务
 *
 * @author Clover
 * @version 1.0.0
 * @since 2024/1/29 17:28
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
@Reflective
@Transactional
public @interface OceanstarsTransactional {

  /**
   * 数据源名称
   *
   * @return 数据源名称
   */
  String datasource() default "";

  // 以保留 @Transactional 的其他属性
  @AliasFor(annotation = Transactional.class, attribute = "value")
  String value() default "";

  @AliasFor(annotation = Transactional.class, attribute = "transactionManager")
  String transactionManager() default "";

  @AliasFor(annotation = Transactional.class, attribute = "label")
  String[] label() default {};

  @AliasFor(annotation = Transactional.class, attribute = "propagation")
  Propagation propagation() default Propagation.REQUIRED;

  @AliasFor(annotation = Transactional.class, attribute = "isolation")
  Isolation isolation() default Isolation.DEFAULT;

  @AliasFor(annotation = Transactional.class, attribute = "timeout")
  int timeout() default -1;

  @AliasFor(annotation = Transactional.class, attribute = "timeoutString")
  String timeoutString() default "";

  @AliasFor(annotation = Transactional.class, attribute = "readOnly")
  boolean readOnly() default false;

  @AliasFor(annotation = Transactional.class, attribute = "rollbackFor")
  Class<? extends Throwable>[] rollbackFor() default {};

  @AliasFor(annotation = Transactional.class, attribute = "rollbackForClassName")
  String[] rollbackForClassName() default {};

  @AliasFor(annotation = Transactional.class, attribute = "noRollbackFor")
  Class<? extends Throwable>[] noRollbackFor() default {};

  @AliasFor(annotation = Transactional.class, attribute = "noRollbackForClassName")
  String[] noRollbackForClassName() default {};
}
