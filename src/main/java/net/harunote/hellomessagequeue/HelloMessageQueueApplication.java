package net.harunote.hellomessagequeue;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Step 3 Example of implementing real-time notifications using the Pub/Sub model (using WebSocket and STOMP)
 *
 * @author : codevillain
 * @fileName : HelloMessageQueueApplication
 * @since : 12/10/24
 */

@SpringBootApplication
public class HelloMessageQueueApplication {

    public static void main(String[] args) {
        SpringApplication.run(HelloMessageQueueApplication.class, args);
    }

}
