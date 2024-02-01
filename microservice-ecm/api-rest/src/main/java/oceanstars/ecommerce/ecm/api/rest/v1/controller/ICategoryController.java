package oceanstars.ecommerce.ecm.api.rest.v1.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import oceanstars.ecommerce.common.exception.BaseException;
import oceanstars.ecommerce.common.restful.RestApi;
import oceanstars.ecommerce.common.restful.RestResponseMessage;
import oceanstars.ecommerce.ecm.api.rest.v1.request.category.CreateCategoryRequestMessage;
import oceanstars.ecommerce.ecm.api.rest.v1.request.category.SearchCategoryRequestMessage;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * ECM分类管理外部API接口
 *
 * @author Clover
 * @version 1.0.0
 * @since 2024/1/25 11:51
 */
@Tag(name = "Category", description = "ECM分类管理外部API接口")
public interface ICategoryController extends RestApi {

  /**
   * 创建分类
   *
   * @param requestMessage 创建分类请求参数
   * @return 创建分类响应参数
   * @throws BaseException 处理异常信息抛出
   */
  @Operation(summary = "创建分类", description = "创建分类", tags = {"Category"})
  @PostMapping(value = "/categories", produces = "application/vnd.api.v1.0.0+json")
  RestResponseMessage createCategory(@RequestBody CreateCategoryRequestMessage requestMessage) throws BaseException;

  /**
   * 搜索分类
   *
   * @param requestMessage 搜索分类请求参数
   * @return 搜索分类响应参数
   * @throws BaseException 处理异常信息抛出
   */
  @Operation(summary = "搜索分类", description = "搜索分类", tags = {"Category"})
  @PostMapping(value = "/categories/queries", produces = "application/vnd.api.v1.0.0+json")
  RestResponseMessage searchCategory(@RequestBody SearchCategoryRequestMessage requestMessage) throws BaseException;

  /**
   * 根据指定分类ID获取分类
   *
   * @param id 分类ID
   * @return 分类响应参数
   * @throws BaseException 处理异常信息抛出
   */
  @Operation(summary = "根据指定分类ID获取分类", description = "根据指定分类ID获取分类", tags = {"Category"})
  @GetMapping(value = "/categories/{id}", produces = "application/vnd.api.v1.0.0+json")
  RestResponseMessage getCategory(@PathVariable("id") Long id) throws BaseException;
}
