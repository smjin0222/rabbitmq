package net.harunote.hellomessagequeue.step10;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/message") // 올바르게 설정
public class PublisherConfirmsController {

    private final PublisherConfirmsProducer producer;

    public PublisherConfirmsController(PublisherConfirmsProducer producer) {
        this.producer = producer;
    }

    @PostMapping
    public ResponseEntity<String> sendMessage(@RequestBody String message) {
        try {
            producer.sendMessage(message);
            return ResponseEntity.ok("Message sent: " + message);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error sending message: " + e.getMessage());
        }
    }
}