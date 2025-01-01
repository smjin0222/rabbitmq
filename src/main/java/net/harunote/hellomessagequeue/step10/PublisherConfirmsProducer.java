package net.harunote.hellomessagequeue.step10;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
public class PublisherConfirmsProducer {

    private final RabbitTemplate rabbitTemplate;

    public PublisherConfirmsProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;

        // Publisher Confirms 콜백 설정
        this.rabbitTemplate.setConfirmCallback((correlationData, ack, cause) -> {
            if (ack) {
                System.out.println("Message successfully delivered to exchange!");
            } else {
                System.err.println("Message delivery failed: " + cause);
            }
        });
    }

    public void sendMessage(String message) {
        rabbitTemplate.convertAndSend(
                RabbitMQConfig.EXCHANGE_NAME,
                "publisher.key",
                message);
        System.out.println("Message sent: " + message);
    }
}