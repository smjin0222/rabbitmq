package net.harunote.hellomessagequeue.step8;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    public static final String TOPIC_EXCHANGE = "order.topic.exchange";
    public static final String TOPIC_QUEUE = "order.completed.queue";
    public static final String TOPIC_DLQ = "order.topic.dlq";
    public static final String TOPIC_DLX = "order.topic.dlx";
    public static final String DEAD_LETTER_ROUTING_KEY = "dlx.#";

    @Bean
    public TopicExchange exchange() {
        return new TopicExchange(TOPIC_EXCHANGE);
    }

    @Bean
    public TopicExchange deadLetterExchange() {
        return new TopicExchange(TOPIC_DLX);
    }

    @Bean
    public Queue queue() {
        return QueueBuilder.durable(TOPIC_QUEUE)
                .withArgument("x-dead-letter-exchange", TOPIC_DLX)
                .withArgument("x-dead-letter-routing-key", DEAD_LETTER_ROUTING_KEY)
                .build();
    }

    @Bean
    public Queue deadLetterQueue() {
        return new Queue(TOPIC_DLQ);
    }

    @Bean
    public Binding binding() {
        return BindingBuilder.bind(queue()).to(exchange()).with("order.completed.*");
    }

    @Bean
    public Binding deadLetterBinding() {
        return BindingBuilder.bind(deadLetterQueue()).to(deadLetterExchange()).with(DEAD_LETTER_ROUTING_KEY);
    }

}
