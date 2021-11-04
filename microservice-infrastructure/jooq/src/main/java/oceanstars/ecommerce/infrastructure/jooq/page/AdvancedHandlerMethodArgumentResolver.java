package oceanstars.ecommerce.infrastructure.jooq.page;

import org.apache.commons.lang3.StringUtils;
import org.springframework.core.MethodParameter;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.ModelAndViewContainer;

/**
 * 分页参数请求自定义处理
 *
 * @author Clover
 * @version 1.0.0
 * @since 2021/11/4 1:59 下午
 */
public class AdvancedHandlerMethodArgumentResolver extends PageableHandlerMethodArgumentResolver {

  /**
   * PageSize为-1时作为不分页处理
   */
  private static final String NEED_PAGE_PAGE_SITE = "-1";

  @Override
  public boolean supportsParameter(MethodParameter methodParameter) {
    return methodParameter.getParameterType() == Pageable.class;
  }

  @Override
  @NonNull
  public Pageable resolveArgument(@NonNull MethodParameter methodParameter, ModelAndViewContainer modelAndViewContainer,
      NativeWebRequest nativeWebRequest,
      WebDataBinderFactory webDataBinderFactory) {

    // 获取每页数据行数
    String pageSize = nativeWebRequest.getParameter(this.getParameterNameToUse(this.getSizeParameterName(), methodParameter));

    final Pageable pageable = super.resolveArgument(methodParameter, modelAndViewContainer, nativeWebRequest, webDataBinderFactory);

    // 不限制每页显示数量
    if (StringUtils.isNotBlank(pageSize) && NEED_PAGE_PAGE_SITE.equals(pageSize)) {
      return PageRequest.of(pageable.getPageNumber(), Integer.MAX_VALUE, pageable.getSort());
    }

    return pageable;
  }
}
