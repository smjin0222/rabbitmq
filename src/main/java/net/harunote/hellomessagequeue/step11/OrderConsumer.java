package net.harunote.hellomessagequeue.step11;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class OrderConsumer {

    private final StockRepository stockRepository;
    private final RabbitTemplate rabbitTemplate;
    private final ObjectMapper objectMapper;

    public OrderConsumer(StockRepository stockRepository, RabbitTemplate rabbitTemplate, ObjectMapper objectMapper) {
        this.stockRepository = stockRepository;
        this.rabbitTemplate = rabbitTemplate;
        this.objectMapper = objectMapper;
    }

    @RabbitListener(queues = RabbitMQConfig.ORDER_COMPLETED_QUEUE)
    public void processMessage(String message) {
        try {
            System.out.println("Processing message: " + message);

            // 메시지를 StockEntity로 변환
            StockEntity stock = objectMapper.readValue(message, StockEntity.class);

            // DB에 저장
            stock.setProcessed(true);
            stock.setCreatedAt(LocalDateTime.now());
            stockRepository.save(stock);

            System.out.println("Stock saved successfully: " + stock);
        } catch (Exception e) {
            System.err.println("Processing failed. Sending to DLQ: " + message);
            rabbitTemplate.convertAndSend(RabbitMQConfig.ORDER_TOPIC_DLX,
                    RabbitMQConfig.DEAD_LETTER_ROUTING_KEY, message);
        }
    }
}