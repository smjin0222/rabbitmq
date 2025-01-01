package net.harunote.hellomessagequeue.step09;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.amqp.core.*;

@Configuration
public class RabbitMQConfig {

    public static final String EXCHANGE_NAME = "transaction_exchange";
    public static final String QUEUE_NAME = "transaction_queue";
    public static final String DLQ_NAME = "dead_letter_queue";
    public static final String DLX_NAME = "dead_letter_exchange";


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