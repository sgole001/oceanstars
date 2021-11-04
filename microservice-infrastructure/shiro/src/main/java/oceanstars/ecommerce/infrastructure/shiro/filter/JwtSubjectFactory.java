package oceanstars.ecommerce.infrastructure.shiro.filter;

import org.apache.shiro.mgt.DefaultSessionStorageEvaluator;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.subject.SubjectContext;
import org.apache.shiro.web.mgt.DefaultWebSubjectFactory;

/**
 * 对于无状态的TOKEN不创建session 这里都不使用session
 *
 * @author Clover
 * @version 1.0.0
 * @since 2021/11/4 11:45 上午
 */
public class JwtSubjectFactory extends DefaultWebSubjectFactory {

  private final DefaultSessionStorageEvaluator sessionStorageEvaluator;

  public JwtSubjectFactory(DefaultSessionStorageEvaluator sessionStorageEvaluator) {
    this.sessionStorageEvaluator = sessionStorageEvaluator;
  }

  @Override
  public Subject createSubject(SubjectContext context) {
    // 这里都不创建session
    context.setSessionCreationEnabled(Boolean.FALSE);
    this.sessionStorageEvaluator.setSessionStorageEnabled(Boolean.FALSE);
    return super.createSubject(context);
  }
}
