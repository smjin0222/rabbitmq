package net.harunote.hellomessagequeue.step7;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class OrderConsumer {

    private static final int MAX_RETRIES = 3;
    private int retryCount = 0; // 재시도 횟수 관리

    // RabbitMQManualConfig에서 선언한 containerFactory 설정 추가
    @RabbitListener(queues = RabbitMQConfig.ORDER_COMPLETED_QUEUE, containerFactory = "rabbitListenerContainerFactory")
    public void processMessage(String message, Channel channel, @Header("amqp_deliveryTag") long tag) {
        try {
            // 실패를 강제로 유발
            if ("fail".equalsIgnoreCase(message)) {
                if (retryCount < MAX_RETRIES) {
                    System.err.println("# fail & retry = " + retryCount);
                    retryCount++;
                    throw new RuntimeException("- Processing failed. Retry count: " + retryCount);
                } else {
                    System.err.println("# Max retries reached. Moving to DLQ.");
                    retryCount = 0; // 재시도 기록 초기화
                    channel.basicNack(tag, false, false); // DLQ로 메시지 이동
                    return;
                }
            }
            // 성공 처리
            System.out.println("+ Message processed successfully: " + message);
            channel.basicAck(tag, false); // Ack 전송
            retryCount = 0; // 성공 시 재시도 기록 초기화

        } catch (Exception e) {
            System.err.println("# Error processing message: " + e.getMessage());
            try {
                // 실패 시 basicReject 처리하여 메시지를 다시 처리
                channel.basicReject(tag, true); // 재처리 가능하도록 Reject
            } catch (IOException ioException) {
                System.err.println("# Failed to reject message: " + ioException.getMessage());
            }
        }
    }
}