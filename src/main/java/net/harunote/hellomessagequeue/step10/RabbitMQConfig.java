package net.harunote.hellomessagequeue.step10;


import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class RabbitMQConfig {

    @Bean
    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(ConnectionFactory connectionFactory) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setDefaultRequeueRejected(false); // Prevent requeuing on exception
        return factory;
    }

    @Bean
    public RabbitTemplate rabbitTemplate(CachingConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMandatory(true);
        rabbitTemplate.setConfirmCallback((correlation, ack, reason) -> {
            if (ack) {
                System.out.println("Message confirmed: " +
                        (correlation != null ? correlation.getId() : "null"));
            } else {
                System.out.println("Message not confirmed: " +
                        (correlation != null ? correlation.getId() : "null") + ", Reason: " + reason);
                // 여기에 예외 처리 혹은 재처리 로직을 호출
                handleFailedMessage(correlation, reason);
            }
        });

        // ReturnCallback 설정
        rabbitTemplate.setReturnsCallback(returnedMessage -> {
            System.out.println("Returned message: " + new String(returnedMessage.getMessage().getBody()));
            System.out.println("Exchange: " + returnedMessage.getExchange());
            System.out.println("Routing Key: " + returnedMessage.getRoutingKey());
        });


        return rabbitTemplate;
    }

    private void handleFailedMessage(CorrelationData correlationData, String reason) {
        System.err.println("Handling failed message. CorrelationId: " +
                (correlationData != null ? correlationData.getId() : "null") + ", Reason: " + reason);

        // 필요한 경우 예외를 던져 Exception 블록에서 처리

    }

    @Bean
    public Queue myQueue() {
        return new Queue("myQueue", false); // Non-durable queue
    }
}