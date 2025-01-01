package net.harunote.hellomessagequeue.step10;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {
    public static final String QUEUE_NAME = "publisher_confirms_queue";
    public static final String EXCHANGE_NAME = "publisher_confirms_exchange";
    public static final String DLQ_NAME = "dead_letter_queue";
    public static final String DLX_NAME = "dead_letter_exchange";

    @Bean
    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(ConnectionFactory connectionFactory) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setAcknowledgeMode(AcknowledgeMode.MANUAL); // Ack 모드를 수동으로 설정
        return factory;
    }

    @Bean
    public DirectExchange transactionExchange() {
        return new DirectExchange(EXCHANGE_NAME, true, false);
    }


    @Bean
    public TopicExchange deadLetterExchange() {
        return new TopicExchange(DLX_NAME, true, false);
    }

    @Bean
    public Queue transactionQueue() {
        return QueueBuilder.durable(QUEUE_NAME)
                .withArgument("x-dead-letter-exchange", DLX_NAME)
                .withArgument("x-dead-letter-routing-key", DLQ_NAME)
                .build();
    }

    @Bean
    public Queue deadLetterQueue() {
        return QueueBuilder.durable(DLQ_NAME).build();
    }

    @Bean
    public Binding bindingTransactionQueue() {
        return BindingBuilder.bind(transactionQueue()).to(transactionExchange()).with("transaction.key");
    }

    @Bean
    public Binding bindingDeadLetterQueue() {
        return BindingBuilder.bind(deadLetterQueue()).to(deadLetterExchange()).with(DLQ_NAME);
    }

}