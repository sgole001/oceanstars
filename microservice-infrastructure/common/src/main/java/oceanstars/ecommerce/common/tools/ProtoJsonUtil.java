package oceanstars.ecommerce.common.tools;

import com.google.protobuf.Descriptors.Descriptor;
import com.google.protobuf.Message;
import com.google.protobuf.TypeRegistry;
import com.google.protobuf.util.JsonFormat;
import com.google.protobuf.util.JsonFormat.Parser;
import com.google.protobuf.util.JsonFormat.Printer;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.springframework.util.CollectionUtils;

/**
 * Protobuf类型和Java Pojo类型转换类
 *
 * @author Clover
 * @version 1.0.0
 * @since 2021/12/16 9:52 下午
 */
public class ProtoJsonUtil {

  /**
   * 转换接口：Protobuf -> Json
   */
  private final Printer printer;

  /**
   * 转换接口：Json -> Protobuf
   */
  private final Parser parser;

  /**
   * Protobuf Any类型描述列表，用于转换格式说明注册
   */
  private List<Descriptor> anyFieldDescriptor;

  /**
   * 构造函数：根据构建器初始化成员转接口
   *
   * @param builder 构建器
   */
  private ProtoJsonUtil(Builder builder) {

    this.printer = JsonFormat.printer();
    this.parser = JsonFormat.parser();

    // 注册Any类型
    if (!CollectionUtils.isEmpty(builder.anyFieldDescriptor)) {
      this.anyFieldDescriptor = builder.anyFieldDescriptor;
      final TypeRegistry typeRegistry = TypeRegistry.newBuilder().add(this.anyFieldDescriptor).build();
      this.printer.usingTypeRegistry(typeRegistry);
      this.parser.usingTypeRegistry(typeRegistry);
    }
  }

  public static Builder newBuilder() {
    return new Builder();
  }

  public String toJson(Message sourceMessage) throws IOException {
    return this.printer.print(sourceMessage);
  }

  public Message toProto(Message.Builder targetBuilder, String json) throws IOException {
    this.parser.merge(json, targetBuilder);
    return targetBuilder.build();
  }

  public List<Descriptor> getAnyFieldDescriptor() {
    return anyFieldDescriptor;
  }

  /**
   * Protobuf类型和Java Pojo类型转换工具类构建器
   *
   * @author Clover
   * @version 1.0.0
   * @since 2021/12/16 9:58 下午
   */
  public static final class Builder {

    private List<Descriptor> anyFieldDescriptor;

    public Builder anyFieldDescriptor(Descriptor val) {

      if (CollectionUtils.isEmpty(anyFieldDescriptor)) {
        this.anyFieldDescriptor = new ArrayList<>();
      }
      anyFieldDescriptor.add(val);

      return this;
    }

    public ProtoJsonUtil build() {
      return new ProtoJsonUtil(this);
    }
  }
}
