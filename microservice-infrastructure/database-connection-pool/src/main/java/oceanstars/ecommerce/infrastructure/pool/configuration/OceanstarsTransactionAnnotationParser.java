package oceanstars.ecommerce.infrastructure.pool.configuration;

import java.io.Serial;
import java.lang.reflect.AnnotatedElement;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.lang.Nullable;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.SpringTransactionAnnotationParser;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.NoRollbackRuleAttribute;
import org.springframework.transaction.interceptor.RollbackRuleAttribute;
import org.springframework.transaction.interceptor.TransactionAttribute;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

/**
 * 扩展Spring事务注解解析器，支持多数据源事务
 *
 * @author Clover
 * @version 1.0.0
 * @since 2024/1/30 16:02
 */
public class OceanstarsTransactionAnnotationParser extends SpringTransactionAnnotationParser {

  @Serial
  private static final long serialVersionUID = -7382054704802515573L;

  @Override
  @Nullable
  public TransactionAttribute parseTransactionAnnotation(AnnotatedElement element) {
    AnnotationAttributes attributes = AnnotatedElementUtils.findMergedAnnotationAttributes(element, OceanstarsTransactional.class, false, false);
    return attributes != null ? this.parseTransactionAnnotation(attributes) : null;
  }

  @Override
  public TransactionAttribute parseTransactionAnnotation(Transactional ann) {
    return this.parseTransactionAnnotation(AnnotationUtils.getAnnotationAttributes(ann, false, false));
  }

  @Override
  protected TransactionAttribute parseTransactionAnnotation(AnnotationAttributes attributes) {

    // 构建Jooq事务属性
    OceanstarsTransactionAttribute transactionAttribute = new OceanstarsTransactionAttribute();

    // 设置数据源名称
    transactionAttribute.setDataSource(attributes.getString("datasource"));

    // 设定事务传播行为
    final Propagation propagation = attributes.getEnum("propagation");
    transactionAttribute.setPropagationBehavior(propagation.value());

    // 设定事务隔离级别
    Isolation isolation = attributes.getEnum("isolation");
    transactionAttribute.setIsolationLevel(isolation.value());

    // 设定事务超时时间
    transactionAttribute.setTimeout(attributes.getNumber("timeout").intValue());
    // 设定事务超时时间字符串
    String timeoutString = attributes.getString("timeoutString");
    Assert.isTrue(!StringUtils.hasText(timeoutString) || transactionAttribute.getTimeout() < 0,
        "参数[timeout]和[timeoutString]只能设定其中之一！");
    transactionAttribute.setTimeoutString(timeoutString);

    // 设定事务是否只读
    transactionAttribute.setReadOnly(attributes.getBoolean("readOnly"));

    // 设定事务限定符（事务管理Bean）
    transactionAttribute.setQualifier(attributes.getString("value"));

    // 设定事务标签
    transactionAttribute.setLabels(Set.of(attributes.getStringArray("label")));

    // 初始化创建事务回滚规则
    List<RollbackRuleAttribute> rollbackRules = new ArrayList<>();
    // 获取事务回滚规则(类对象)
    Class<?>[] rollbackFor = attributes.getClassArray("rollbackFor");
    for (Class<?> rbRule : rollbackFor) {
      rollbackRules.add(new RollbackRuleAttribute(rbRule));
    }
    // 获取事务回滚规则(类名称)
    String[] rollbackForClassName = attributes.getStringArray("rollbackForClassName");
    for (String rbRule : rollbackForClassName) {
      rollbackRules.add(new RollbackRuleAttribute(rbRule));
    }
    // 获取事务不回滚规则(类对象)
    Class<?>[] noRollbackFor = attributes.getClassArray("noRollbackFor");
    for (Class<?> rbRule : noRollbackFor) {
      rollbackRules.add(new NoRollbackRuleAttribute(rbRule));
    }
    // 获取事务不回滚规则(类名称)
    String[] noRollbackForClassName = attributes.getStringArray("noRollbackForClassName");
    for (String rbRule : noRollbackForClassName) {
      rollbackRules.add(new NoRollbackRuleAttribute(rbRule));
    }
    // 设置事务回滚规则
    transactionAttribute.setRollbackRules(rollbackRules);

    return transactionAttribute;
  }

  @Override
  public boolean equals(@Nullable Object other) {
    return other instanceof OceanstarsTransactionAnnotationParser;
  }

  @Override
  public int hashCode() {
    return OceanstarsTransactionAnnotationParser.class.hashCode();
  }
}
