package net.harunote.hellomessagequeue;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Step 6 Routing model implementing LogCollector (TOPIC_EXCHANGE, DIRECT_EXCHANGE)
 *
 * @author : codevillain
 * @fileName : HelloMessageQueueApplication
 * @since : 12/21/24
 */

@SpringBootApplication
public class HelloMessageQueueApplication {

    public static void main(String[] args) {
        SpringApplication.run(HelloMessageQueueApplication.class, args);
    }

}
