package net.harunote.hellomessagequeue.step8;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.retry.RetryCallback;
import org.springframework.retry.RetryContext;
import org.springframework.retry.RetryListener;
import org.springframework.retry.policy.SimpleRetryPolicy;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.stereotype.Component;

@Component
public class OrderConsumer {

    private int retryCount;

    @RabbitListener(queues = RabbitMQConfig.TOPIC_QUEUE)
    public void processMessage(String message) {
        System.out.println("Received message: "+ message + "count : " + retryCount++);
        if ("fail".equals(message)) {
            throw new RuntimeException("- Processing failed. Retry" );
        }
        System.out.println("Message processed successfully: " + message);
    }
}
