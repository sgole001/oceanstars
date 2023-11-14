package oceanstars.ecommerce.integration.domain.gateway.entity.valueobject;

import oceanstars.ecommerce.common.tools.JsonUtil;
import oceanstars.ecommerce.integration.domain.gateway.entity.ServiceEntity;

public class PosIntegrationRequestValueObject extends BaseIntegrationRequestValueObject {

  private final String orderId;
  private final Integer quantity;

  private PosIntegrationRequestValueObject(Builder builder) {
    super(builder);
    orderId = builder.orderId;
    quantity = builder.quantity;
  }

  public String getOrderId() {
    return orderId;
  }

  public Integer getQuantity() {
    return quantity;
  }

  @Override
  public BaseIntegrationResponseValueObject handle(ServiceEntity service) {
    System.out.println(service.getEndpoint());
    return BaseIntegrationResponseValueObject.newBuilder("Success").build();
  }

  public static final class Builder extends BaseBuilder {

    private final String orderId;
    private final Integer quantity;

    public Builder(String event, String group, String orderId, Integer quantity) {
      super(event, group);
      this.orderId = orderId;
      this.quantity = quantity;
    }

    public PosIntegrationRequestValueObject build() {
      return new PosIntegrationRequestValueObject(this);
    }
  }

  public static void main(String[] args) {
    final PosIntegrationRequestValueObject posIntegrationRequestValueObject = new PosIntegrationRequestValueObject.Builder(
        "test", "1", "2", 1).build();

    BaseIntegrationResponseValueObject baseIntegrationResponseValueObject = BaseIntegrationResponseValueObject.newBuilder(
        "Success").data(posIntegrationRequestValueObject).build();

    System.out.printf(JsonUtil.toStringWithLocalDateTime(baseIntegrationResponseValueObject));
  }
}
