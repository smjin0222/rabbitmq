package net.harunote.hellomessagequeue.step10;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/message")
public class MessageController {

    private final MessageProducer producer;

    public MessageController (MessageProducer producer) {
        this.producer = producer;
    }

    @GetMapping
    public String publishMessage(@RequestParam String message) {
        producer.sendMessage(message);
        return "Message sent to RabbitMQ";
    }
}