package net.harunote.hellomessagequeue.step10;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/message")
public class MessageController {

    private final MessageProducer producer;

    public MessageController (MessageProducer producer) {
        this.producer = producer;
    }

    @PostMapping
    public String publishMessage(@RequestBody StockEntity stockEntity, @RequestParam boolean testCase) {
        producer.sendMessage(stockEntity, testCase);
        return "Message sent to RabbitMQ";
    }
}