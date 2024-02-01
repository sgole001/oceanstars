package oceanstars.ecommerce.ecm.repository.content.view;

import jakarta.annotation.Resource;
import java.util.List;
import oceanstars.ecommerce.ecm.repository.content.view.bo.ContentWithWebFunctionView;
import oceanstars.ecommerce.ecm.repository.generate.tables.EcmContent;
import oceanstars.ecommerce.ecm.repository.generate.tables.EcmContentWebFunction;
import oceanstars.ecommerce.ecm.repository.generate.tables.pojos.EcmContentPojo;
import org.jooq.SelectWhereStep;
import org.jooq.impl.DefaultDSLContext;
import org.springframework.stereotype.Repository;

/**
 * 内容视图DAO
 *
 * @author Clover
 * @version 1.0.0
 * @since 2024/1/23 22:23
 */
@Repository
public class ContentViewDao {

  /**
   * 内容表对象
   */
  final EcmContent content = EcmContent.ECM_CONTENT.as("meta");

  /**
   * 内容WEB功能表对象
   */
  final EcmContentWebFunction webFunction = EcmContentWebFunction.ECM_CONTENT_WEB_FUNCTION.as("raw");

  @Resource
  private DefaultDSLContext dsl;

  public List<ContentWithWebFunctionView> queryContentWithWebFunctionView(final EcmContentPojo contentPojo) {

    final SelectWhereStep<?> select = dsl.select
            (
                // 内容元数据
                content.ID
                , content.NAME
                , content.DISPLAY_NAME
                , content.DESCRIPTION
                , content.TYPE
                // 内容对应原始数据
                , webFunction.ID
                , webFunction.CID
                , webFunction.TYPE
                , webFunction.PID
                , webFunction.HREF
                , webFunction.ICON
            )
        .from(content)
        .join(webFunction)
        .on(content.ID.eq(webFunction.CID));

    return dsl.select
            (
                // 内容元数据
                content.ID
                , content.NAME
                , content.DISPLAY_NAME
                , content.DESCRIPTION
                , content.TYPE
                // 内容对应原始数据
                , webFunction.ID
                , webFunction.CID
                , webFunction.TYPE
                , webFunction.PID
                , webFunction.HREF
                , webFunction.ICON
            )
        .from(content)
        .join(webFunction)
        .on(content.ID.eq(webFunction.CID))
        .fetchInto(ContentWithWebFunctionView.class);
  }

}
