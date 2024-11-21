package net.harunote.hellomessagequeue;

import org.springframework.context.annotation.Configuration;

@Configuration
public class Receiver {
    public void receiveMessage(String message) {
        System.out.println("[#] Received: " + message);
    }
}
