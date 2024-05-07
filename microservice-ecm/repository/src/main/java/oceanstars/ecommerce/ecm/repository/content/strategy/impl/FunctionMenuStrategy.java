package oceanstars.ecommerce.ecm.repository.content.strategy.impl;

import static java.util.Objects.requireNonNull;

import oceanstars.ecommerce.common.domain.entity.ValueObject;
import oceanstars.ecommerce.common.spring.ApplicationContextProvider;
import oceanstars.ecommerce.ecm.domain.content.entity.valueobject.ContentFunctionMenuValueObject;
import oceanstars.ecommerce.ecm.repository.content.strategy.ContentRawStrategy;
import oceanstars.ecommerce.ecm.repository.generate.tables.EcmContentFunctionMenu;
import oceanstars.ecommerce.ecm.repository.generate.tables.daos.EcmContentFunctionMenuDao;
import oceanstars.ecommerce.ecm.repository.generate.tables.pojos.EcmContentFunctionMenuPojo;
import org.jooq.Record;
import org.jooq.impl.TableImpl;

/**
 * Web功能内容特有数据处理策略
 *
 * @author Clover
 * @version 1.0.0
 * @since 2024/1/17 17:26
 */
public class FunctionMenuStrategy implements ContentRawStrategy {

  /**
   * 内容特有数据DAO:
   */
  private static final EcmContentFunctionMenuDao DAO =
      ApplicationContextProvider.getApplicationContext().getBean(EcmContentFunctionMenuDao.class);

  @Override
  public void save(ValueObject raw) {

    // 校验参数
    requireNonNull(raw, "raw");

    // 转换内容原始信息为内容功能菜单值对象
    final ContentFunctionMenuValueObject functionMenuValueObject = (ContentFunctionMenuValueObject) raw;

    // 构建功能菜单POJO
    final EcmContentFunctionMenuPojo functionMenuPojo = buildFunctionMenuPojo(functionMenuValueObject);

    // 保存功能菜单数据
    DAO.insert(functionMenuPojo);

    // 分配委托者
    raw.delegate(functionMenuPojo);
  }

  @Override
  public ValueObject buildRawValueObject(Object raw) {

    // 转换内容原始信息为内容功能菜单值对象
    final EcmContentFunctionMenuPojo functionMenuPojo = (EcmContentFunctionMenuPojo) raw;

    // 构建功能菜单值对象
    return ContentFunctionMenuValueObject.newBuilder()
        // 功能菜单对应的功能ID
        .func(functionMenuPojo.getFunc())
        // 功能菜单动作：点击菜单后的执行脚本
        .action(functionMenuPojo.getAction())
        // 功能菜单图标
        .icon(functionMenuPojo.getIcon())
        // 功能菜单隶属 - 通过内容ID关联隶属关系
        .parent(functionMenuPojo.getParent())
        // 实施构建
        .build();
  }

  @Override
  public TableImpl<?> determineRawTable() {
    return EcmContentFunctionMenu.ECM_CONTENT_FUNCTION_MENU.as("raw");
  }

  @Override
  public Object recordIntoPojo(Record record) {
    return record.into(EcmContentFunctionMenuPojo.class);
  }

  /**
   * 构建功能菜单POJO
   *
   * @param raw 内容原始信息
   * @return Web功能POJO
   */
  private EcmContentFunctionMenuPojo buildFunctionMenuPojo(final ContentFunctionMenuValueObject raw) {

    // 初始化创建Web功能POJO
    final EcmContentFunctionMenuPojo functionMenuPojo = new EcmContentFunctionMenuPojo();

    // 功能菜单图标
    functionMenuPojo.setIcon(raw.getIcon());
    // 功能菜单动作：点击菜单后的执行脚本
    functionMenuPojo.setAction(raw.getAction());
    // 功能菜单对应的功能ID
    functionMenuPojo.setFunc(raw.getFunc());
    // 功能菜单隶属
    functionMenuPojo.setParent(raw.getParent());

    return functionMenuPojo;
  }
}
