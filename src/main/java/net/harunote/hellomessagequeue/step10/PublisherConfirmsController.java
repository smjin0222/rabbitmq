package net.harunote.hellomessagequeue.step10;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController("/api/message")
public class PublisherConfirmsController {

    private final PublisherConfirmsProducer producer;

    public PublisherConfirmsController(PublisherConfirmsProducer producer) {
        this.producer = producer;
    }

    @PostMapping
    public ResponseEntity<String> sendMessage(@RequestBody String message) {
        producer.sendMessage(message);
        return ResponseEntity.ok("Message sent: " + message);
    }
}