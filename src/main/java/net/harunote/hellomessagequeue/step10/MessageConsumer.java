package net.harunote.hellomessagequeue.step10;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class MessageConsumer {

    @RabbitListener(queues = "myQueue")
    public void receiveMessage(String message) {

        System.out.println("Received message: " + message);
        if ("fail".equalsIgnoreCase(message)) {
            throw new RuntimeException("Message processing failed!");
        }
        System.out.println("Message processed successfully!");
    }
}
