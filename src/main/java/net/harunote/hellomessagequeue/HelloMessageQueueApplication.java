package net.harunote.hellomessagequeue;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Step 4 Subscribing to news using the Pub/Sub model (FANOUT_EXCHANGE)
 *
 * @author : codevillain
 * @fileName : HelloMessageQueueApplication
 * @since : 12/20/24
 */
@SpringBootApplication
public class HelloMessageQueueApplication {

    public static void main(String[] args) {
        SpringApplication.run(HelloMessageQueueApplication.class, args);
    }

}
