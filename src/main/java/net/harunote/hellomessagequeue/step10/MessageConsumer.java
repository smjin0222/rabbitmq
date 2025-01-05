package net.harunote.hellomessagequeue.step10;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class MessageConsumer {
    private final StockRepository stockRepository;

    public MessageConsumer(StockRepository stockRepository) {
        this.stockRepository = stockRepository;
    }

    @RabbitListener(queues = RabbitMQConfig.QUEUE_NAME, containerFactory = "rabbitListenerContainerFactory")
    public void receiveMessage(StockEntity stock,
                               @Header(AmqpHeaders.DELIVERY_TAG) long deliveryTag,
                               Channel channel) {
        try {
            System.out.println("[Consumer] Received message: " + stock);

            // 비즈니스 로직 처리
            stock.setProcessed(true);
            stock.setUpdatedAt(LocalDateTime.now());
            StockEntity updatedStock = stockRepository.save(stock);
            System.out.println("[Consumer] Stock processed and updated: " + updatedStock);

            // 메시지 처리 완료 후 Ack 전송
            channel.basicAck(deliveryTag, false); // Single message Ack
        } catch (Exception e) {
            System.out.println("[Consumer] Error processing message: " + e.getMessage());

            try {
                // 메시지 처리 실패 시 Nack 전송 (재처리 또는 DLQ로 이동)
                channel.basicNack(deliveryTag, false, false); // Don't requeue
            } catch (Exception nackException) {
                System.out.println("[Consumer] Error sending Nack: " + nackException.getMessage());
            }
        }
    }
}
