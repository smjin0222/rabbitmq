package net.harunote.hellomessagequeue.step11;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class DeadLetterConsumer {

    private final SlackNotifier slackNotifier;

    public DeadLetterConsumer(SlackNotifier slackNotifier) {
        this.slackNotifier = slackNotifier;
    }

    @RabbitListener(queues = RabbitMQConfig.DLQ)
    public void processDeadLetter(String message) {
        System.out.println("Received from DLQ: " + message);
        try {
            slackNotifier.sendSlackNotification("DLQ Alert: Failed message -> " + message);
        } catch (Exception e) {
            System.err.println("Slack notification failed: " + e.getMessage());
        }
    }
}