package net.harunote.hellomessagequeue;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


/**
 * Step 2 Example of work distribution through competing consumer pattern
 *
 * @author : codevillain
 * @fileName : HelloMessageQueueApplication
 * @since : 12/5/24
 */

@SpringBootApplication
public class HelloMessageQueueApplication {

    public static void main(String[] args) {
        SpringApplication.run(HelloMessageQueueApplication.class, args);
    }

}
