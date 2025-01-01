package net.harunote.hellomessagequeue.step09;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.springframework.stereotype.Component;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

@Component
public class TransactionalProducer {

    private static final String EXCHANGE_NAME = RabbitMQConfig.EXCHANGE_NAME;
    private static final String ROUTING_KEY = "transaction.key";


    private final RabbitTemplate rabbitTemplate;

    public TransactionalProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendTransactionalMessage(String message) {
        try {
            rabbitTemplate.execute(channel -> {
                // 트랜잭션 시작
                channel.txSelect();
                try {
                    channel.basicPublish(EXCHANGE_NAME, ROUTING_KEY, null, message.getBytes());
                    System.out.println("Message 전송 : " + message);

                    // 트랜잭션 커밋
                    channel.txCommit();
                    System.out.println("트랜잭션 커밋 !");
                } catch (Exception e) {
                    // 트랜잭션 롤백
                    System.out.println("트랜잭션 실패, 롤백 처리!");
                    channel.txRollback();
                }
                return null;
            });
        } catch (Exception e) {
            System.out.println("Producer Error 발생 : " + e.getMessage());
        }
    }
}