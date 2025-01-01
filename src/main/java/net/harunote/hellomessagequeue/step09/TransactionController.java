package net.harunote.hellomessagequeue.step09;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/message")
public class TransactionController {

    private final TransactionalProducer transactionalProducer;

    public TransactionController(TransactionalProducer transactionalProducer) {
        this.transactionalProducer = transactionalProducer;
    }

    /**
     curl -X POST "http://localhost:8080/api/message" \
     -H "Content-Type: application/json" \
     -d '\"success\"'
     * @param message
     * @return
     */
    @PostMapping
    public ResponseEntity<String> sendMessage(@RequestBody String message) {
        try {
            transactionalProducer.sendTransactionalMessage(message);
            return ResponseEntity.ok("Message sent: " + message);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Message failed: " + e.getMessage());
        }
    }
}