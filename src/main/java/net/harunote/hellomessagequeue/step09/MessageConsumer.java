package net.harunote.hellomessagequeue.step09;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class MessageConsumer {

    @RabbitListener(queues = "transactionQueue")
    public void receiveMessage(String message) {
        System.out.println("Received message: " + message);
        // 메시지 처리 로직

        // todo 실패시 deadletterqueue 에 전송
    }
}
