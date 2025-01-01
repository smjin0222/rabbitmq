package net.harunote.hellomessagequeue.step10;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {
    public static final String QUEUE_NAME = "publisher_confirms_queue";
    public static final String EXCHANGE_NAME = "publisher_confirms_exchange";

    @Bean
    public Queue publisherConfirmsQueue() {
        return new Queue(QUEUE_NAME, true); // Durable Queue
    }

    @Bean
    public DirectExchange publisherConfirmsExchange() {
        return new DirectExchange(EXCHANGE_NAME);
    }

    @Bean
    public Binding bindingPublisherConfirmsQueue() {
        return BindingBuilder.bind(publisherConfirmsQueue()).to(publisherConfirmsExchange()).with("publisher.key");
    }
}