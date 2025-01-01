package net.harunote.hellomessagequeue.step09;


import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;


@RestController
@RequestMapping("/api/message")
public class TransactionController {

    private MessageProducer messageProducer;

    public TransactionController(MessageProducer messageProducer) {
        this.messageProducer = messageProducer;
    }

    @PostMapping("/success")
    public ResponseEntity<String> successTransaction(@RequestBody String message) {
        messageProducer.sendMessage(message, false);
        return ResponseEntity.ok("Transaction processed successfully");
    }

    @PostMapping("/fail")
    public ResponseEntity<String> failTransaction(@RequestBody String message) {
        try {
            messageProducer.sendMessage(message, true);
            return ResponseEntity.ok("Transaction should have failed");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Transaction failed as expected: " + e.getMessage());
        }
    }
}
