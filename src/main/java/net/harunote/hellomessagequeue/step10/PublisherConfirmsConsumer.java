package net.harunote.hellomessagequeue.step10;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class PublisherConfirmsConsumer {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @RabbitListener(queues = RabbitMQConfig.QUEUE_NAME)
    public void consumeMessage(String message, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long tag) {
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
            System.err.println("Error consume message: " + e.getMessage());
            try {
                channel.basicReject(tag, false); // 실패 처리 (DLQ로 이동)
            } catch (IOException ex) {
                System.err.println("Failed to reject message: " + ex.getMessage());
            }
        }
    }
}