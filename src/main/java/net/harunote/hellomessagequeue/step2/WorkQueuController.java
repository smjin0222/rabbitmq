package net.harunote.hellomessagequeue.step2;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class WorkQueuController {
    private final WorkQueueProducer workQueueProducer;

    public WorkQueuController(WorkQueueProducer workQueueProducer) {
        this.workQueueProducer = workQueueProducer;
    }

    @PostMapping("/workqueue")
    public String workQueue(@RequestParam String message, @RequestParam int duration) {
        workQueueProducer.sendWork(message, duration);
        return "Work queue sent = " + message + ", (" + duration + ")";
    }
}
