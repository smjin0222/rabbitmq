package net.harunote.hellomessagequeue.step10;

import jakarta.transaction.Transactional;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

@Component
public class MessageProducer {

    private final RabbitTemplate rabbitTemplate;
    private final StockRepository stockRepository;

    public MessageProducer(RabbitTemplate rabbitTemplate, StockRepository stockRepository) {
        this.rabbitTemplate = rabbitTemplate;
        this.stockRepository = stockRepository;
    }

    @Transactional
    public void sendMessage(StockEntity stockEntity, boolean testCase) {
        stockEntity.setProcessed(false);
        stockEntity.setCreatedAt(LocalDateTime.now());

        // 1. DB 저장
        StockEntity savedStock = stockRepository.save(stockEntity);
        System.out.println("# Stock saved: " + savedStock);

        if (stockEntity.getUserId() == null || stockEntity.getUserId().isEmpty()) {
            throw new IllegalArgumentException("Invalid StockEntity: userId is required");
        }


        try {
            // 2. 메시지 RabbitMQ에 전송
            CorrelationData correlationData = new CorrelationData(savedStock.getId().toString());
            rabbitTemplate.convertAndSend(
                    testCase ? "nonExistentExchange" : RabbitMQConfig.EXCHANGE_NAME,
                    testCase ? "invalidRoutingKey" : RabbitMQConfig.ROUTING_KEY,
                    savedStock,
                    correlationData
            );

            // 3. Publisher Confirms 확인
            if (correlationData.getFuture().get(5, TimeUnit.SECONDS).isAck()) {
                System.out.println("# Message confirmed: " + savedStock);
                savedStock.setProcessed(true);
                stockRepository.save(savedStock);
            } else {
                throw new RuntimeException("# Message not confirmed");
            }
        } catch (Exception e) {
            System.out.println("# Producer failed: " + e.getMessage());
            throw new RuntimeException("# Transaction rolled back", e);
        }
    }
}