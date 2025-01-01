package net.harunote.hellomessagequeue.step09;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class MessageProducer {

    private final RabbitTemplate rabbitTemplate;

    public MessageProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Transactional
    public void sendMessage(String message, boolean isFail) {
        System.out.println("producer message: " + message);
        rabbitTemplate.convertAndSend("transactionQueue", message);

        if (isFail) {
            throw new RuntimeException("Transaction Failed");

            // 여기에서 DB의 상태를 바꾸거나 후처리를 해주어야 함
        }
        System.out.println("Sent message: " + message);
    }
}
