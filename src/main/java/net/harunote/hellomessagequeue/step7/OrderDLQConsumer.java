package net.harunote.hellomessagequeue.step7;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
public class OrderDLQConsumer {
    private final RabbitTemplate rabbitTemplate;

    public OrderDLQConsumer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @RabbitListener(queues = RabbitMQConfig.DLQ)
    public void processDeadLetter(String message) {
        System.out.println("[DLQ Received]: " + message);
        try {
            // 메시지 수정
            String fixedMessage = fixMessage(message);

            // 수정된 메시지 재전송
            rabbitTemplate.convertAndSend(
                    RabbitMQConfig.ORDER_EXCHANGE,
                    "order.completed.shipping",
                    fixedMessage
            );
            System.out.println("[DLQ] Message requeued with fixed content: " + fixedMessage);

        } catch (Exception e) {
            System.err.println("[DLQ] Failed to reprocess message: " + e.getMessage());
        }
    }

    private String fixMessage(String message) {
        // 메시지가 fail인 경우 success로 변경
        if ("fail".equalsIgnoreCase(message)) {
            System.out.println("[DLQ] Fixing message: " + message);
            return "success";
        }
        return message; // 다른 메시지는 그대로 반환
    }
}