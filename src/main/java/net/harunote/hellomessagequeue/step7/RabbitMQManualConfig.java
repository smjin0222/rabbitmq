package net.harunote.hellomessagequeue.step7;

import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * RabbitMQ 리스너의 설정을 커스터마이징 한다.
 */
@EnableRabbit
@Configuration
public class RabbitMQManualConfig {

    @Bean
    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(ConnectionFactory connectionFactory) {
        // RabbitMQ 리스너 컨테이너를 생성하는 팩토리 클래스
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        // 수동 Ack 모드 설정, 개발자는 메시지 처리 로직에서 명시적으로 메시지를 확인하거나 거부 처리 가능
        factory.setAcknowledgeMode(AcknowledgeMode.MANUAL);
        return factory;
    }
}