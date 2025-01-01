package net.harunote.hellomessagequeue.step10;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class PublisherConfirmsConsumer {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @RabbitListener(queues = RabbitMQConfig.QUEUE_NAME, containerFactory = "rabbitListenerContainerFactory")
    public void consumeMessage(String message, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long tag) throws IOException {
        System.out.println("Received message: " + message);

        try {
            String parsedMessage = objectMapper.readValue(message, String.class);
            System.out.println("Parsed Message: [" + parsedMessage + "]");

            if ("fail".equalsIgnoreCase(parsedMessage)) {
                throw new RuntimeException("Processing failed!");
            }

            System.out.println("Message processed successfully!");
            channel.basicAck(tag, false); // 성공 처리 (Ack 전송)

        } catch (Exception e) {
            System.out.println("Error consume message: " + e.getMessage());
            try {
                channel.basicReject(tag, false); // 실패 처리 (DLQ로 이동)
            } catch (IOException ex) {
                System.out.println("Failed to reject message: " + ex.getMessage());
            }
        }
    }
}