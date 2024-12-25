package net.harunote.hellomessagequeue.step7;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/order")
public class OrderController {

    private final OrderProducer orderProducer;

    public OrderController(OrderProducer orderProducer) {
        this.orderProducer = orderProducer;
    }

    /**
     * curl -X POST "http://localhost:8080/api/order" \
     *      -H "Content-Type: application/json" \
     *      -d '{"message":"fail"}'
     * @param message
     * @return
     */

    @GetMapping
    public ResponseEntity<String> sendOrderMessage(@RequestParam String message) {

        orderProducer.sendShpping(message);
        return ResponseEntity.ok("Order Completed Message sent: " + message);
    }
}