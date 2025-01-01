package net.harunote.hellomessagequeue.step11;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class SlackNotifier {

    private final String apiToken;
    private final String channelId;
    private final WebClient webClient;

    public SlackNotifier(
            @Value("${slack.api-token}") String apiToken,
            @Value("${slack.channel-id}") String channelId) {
        this.apiToken = apiToken;
        this.channelId = channelId;
        this.webClient = WebClient.builder()
                .baseUrl("https://slack.com/api")
                .defaultHeader("Authorization", "Bearer " + apiToken)
                .defaultHeader("Accept", "application/json")
                .build();
    }

    public void sendSlackNotification(String message) {
        webClient.post()
                .uri(uriBuilder -> uriBuilder
                        .path("/chat.postMessage")
                        .queryParam("channel", channelId)
                        .queryParam("text", message)
                        .build())
                .retrieve()
                .toBodilessEntity()
                .block();
        System.out.println("Slack notification sent: " + message);
    }
}