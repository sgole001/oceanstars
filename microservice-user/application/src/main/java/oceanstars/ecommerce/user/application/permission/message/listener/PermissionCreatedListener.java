//package oceanstars.ecommerce.user.application.permission.message.listener;
//
//import oceanstars.ecommerce.user.api.message.payload.permission.PermissionCreatedPayload;
//import org.springframework.kafka.annotation.KafkaListener;
//import org.springframework.kafka.listener.adapter.ConsumerRecordMetadata;
//import org.springframework.kafka.support.Acknowledgment;
//import org.springframework.kafka.support.KafkaHeaders;
//import org.springframework.messaging.Message;
//import org.springframework.messaging.MessageHeaders;
//import org.springframework.stereotype.Component;
//
///**
// * 权限资源已创建事件监听器
// *
// * @author Clover
// * @version 1.0.0
// * @since 2023/12/10 13:10
// */
//@Component
//public class PermissionCreatedListener {
//
//  @KafkaListener(id = "permission-created", topics = "oceanstars.notification.user.permission.created")
//  public void listen(Message<?> message, ConsumerRecordMetadata meta, Acknowledgment ack) {
//
//    final MessageHeaders headers = message.getHeaders();
//
//    final PermissionCreatedPayload payload = (PermissionCreatedPayload) message.getPayload();
//
//    System.out.println(headers.get(KafkaHeaders.TOPIC));
//    System.out.println(headers.get(KafkaHeaders.TIMESTAMP));
//    System.out.println(payload.permission());
//
//    ack.acknowledge();
//  }
//}
