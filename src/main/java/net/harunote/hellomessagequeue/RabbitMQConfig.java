package net.harunote.hellomessagequeue;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class RabbitMQConfig {

    // 큐 이름을 지정한다.
    public static final String QUEUE_NAME = "hellomessagequeue";

    /**
     * 1. Queue queue() : Queue 인스턴스를 생성하고, 애플리케이션이 사용할 큐를 정의, 메시지를 전달하고 처리하는 기본 큐 세팅
     * 2. RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) : RabbitMQ와 통신하기 위한 템플릿 인스턴스 생성, 메시지 송수신용
     * 3. SimpleMessageListenerContainer container(ConnectionFactory connectionFactory, MessageListenerAdapter listenerAdapter) :
     * RabbitMQ 메시지를 비동기적으로 수신하기 위해 SimpleMessageListenerContainer 를 생성, 이 컨테이너가 특정 큐를 지속적으로 모니터링하고 매시지를 수신하면 지정된 리스너(MessageListenerAdapter)를 통해 처리
     * 4. MessageListenerAdapter listenerAdapter(Receiver receiver) : 수신한 메시지를 특정 클래스의 특정 메서드로 전달하는 어댑터, 인자로 전달된 메서드를 자동으로 호출
     */

    @Bean
    public Queue queue() {
        return new Queue(QUEUE_NAME, false);
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        return new RabbitTemplate(connectionFactory);
    }

    @Bean
    public SimpleMessageListenerContainer container(ConnectionFactory connectionFactory,
                                                    MessageListenerAdapter messageListenerAdapter) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setQueueNames(QUEUE_NAME);
        container.setMessageListener(messageListenerAdapter);
        return container;
    }

    @Bean
    public MessageListenerAdapter messageListenerAdapter(Receiver receiver) {
        return new MessageListenerAdapter(receiver, "receiveMessage");
    }
}
