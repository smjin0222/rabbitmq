package net.harunote.hellomessagequeue.step10;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class PublisherConfirmsConsumer {

    @RabbitListener(queues = RabbitMQConfig.QUEUE_NAME)
    public void consumeMessage(String message) {
        System.out.println("Received message: " + message);

        if ("fail".equalsIgnoreCase(message)) {
            throw new RuntimeException("Processing failed!");
        }

        System.out.println("Message processed successfully!");
    }
}