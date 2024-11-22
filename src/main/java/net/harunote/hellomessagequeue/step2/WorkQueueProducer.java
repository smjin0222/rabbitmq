package net.harunote.hellomessagequeue.step2;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
public class WorkQueueProducer {
    private final RabbitTemplate rabbitTemplate;

    public WorkQueueProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendWork(String message, int duration) {
        String messages = message + "| " + duration;
        rabbitTemplate.convertAndSend(RabbitMQConfig.QUEUE_NAME, messages);
        System.out.println("# sent " + messages + " to " + RabbitMQConfig.QUEUE_NAME);
    }
}
