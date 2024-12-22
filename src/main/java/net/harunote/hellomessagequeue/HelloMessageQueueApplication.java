package net.harunote.hellomessagequeue;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Step 7 Execute retry processing when message processing fails (Dead Letter Queue)
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
