package net.harunote.hellomessagequeue.step10;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class PublisherConfirmsConsumer {

    @RabbitListener(queues = RabbitMQConfig.QUEUE_NAME)
    public void consumeMessage(String message) throws JsonProcessingException {
        System.out.println("Received message: " + message);

        try {
            if ("fail".equalsIgnoreCase(message)) {
                throw new RuntimeException("Processing failed!");
            }
            System.out.println("Message processed successfully!");
        } catch (Exception e) {
            System.out.println("Error consume message: " + e.getMessage());
            throw e; // 메시지가 Dead Letter Queue로 이동하도록 예외를 다시 던짐
        }
    }
}