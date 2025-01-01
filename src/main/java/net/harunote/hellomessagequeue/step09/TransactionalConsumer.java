package net.harunote.hellomessagequeue.step09;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class TransactionalConsumer {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @RabbitListener(queues = RabbitMQConfig.QUEUE_NAME)
    public void consumeMessage(String message) throws JsonProcessingException {
        try {
            System.out.println("Received Raw Message: [" + message + "]");

            String parsedMessage = objectMapper.readValue(message, String.class); // JSON 역직렬화
            System.out.println("Parsed Message: [" + parsedMessage + "]");

            if ("fail".equalsIgnoreCase(parsedMessage)) {
                throw new RuntimeException("메시지 처리 실패!");
            }


            System.out.println("메시지 처리 성공!!!!");
        } catch (Exception e) {
            System.out.println("### Error consume message: " + e.getMessage());
            throw e; // 메시지가 DLQ로 이동하도록 예외를 다시 던짐
        }
    }
}