package net.harunote.hellomessagequeue;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


/**
 * Step 1 Example of Simple message send(Producer to Consumer)
 *
 * @author : codevillain
 * @fileName : HelloMessageQueueApplication
 * @since : 12/1/24
 */
@SpringBootApplication
public class HelloMessageQueueApplication {

    public static void main(String[] args) {
        SpringApplication.run(HelloMessageQueueApplication.class, args);
    }

}
