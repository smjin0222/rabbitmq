package net.harunote.hellomessagequeue.step8;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.stereotype.Component;

@Component
public class OrderConsumer {

    private final RabbitTemplate rabbitTemplate;
    private final RetryTemplate retryTemplate;

    public OrderConsumer(RabbitTemplate rabbitTemplate, RetryTemplate retryTemplate) {
        this.rabbitTemplate = rabbitTemplate;
        this.retryTemplate = retryTemplate;
    }

    @RabbitListener(queues = RabbitMQConfig.ORDER_COMPLETED_QUEUE)
    public void processMessage(String message) {
        retryTemplate.execute(context -> {
            try {
                System.out.println("# Received message: " + message + ", Retry Count: " + context.getRetryCount());
                // 실패 조건
                if ("fail".equalsIgnoreCase(message)) {
                    throw new RuntimeException(message);
                }
                // 성공 처리
                System.out.println("메시지 처리 성공 : " + message);
            } catch (Exception e) {
                System.out.println("###### " + e.getMessage());
                if (context.getRetryCount() >= 2) {
                    rabbitTemplate.convertAndSend(RabbitMQConfig.ORDER_TOPIC_DLX,
                            RabbitMQConfig.DEAD_LETTER_ROUTING_KEY, message);
                } else {
                    throw e;
                }
            }
            return null;
        });
    }
}