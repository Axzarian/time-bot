package org.axzarian.timebot.sender;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@Component
public class TelegramSender {

    private final RestTemplate restTemplate = new RestTemplate();

    @Value("${telegram.token}")
    private String botToken;

    public void sendWithButtons(String chatId, String text) {
        final var url = String.format("https://api.telegram.org/bot%s/sendMessage", botToken);

        Map<String, Object> keyboard = Map.of(
                "inline_keyboard", List.of(
                        List.of(Map.of("text", "⏱ Обновить", "callback_data", "refresh"))
                )
        );

        Map<String, Object> payload = Map.of(
                "chat_id", chatId,
                "text", text,
                "reply_markup", keyboard
        );

        restTemplate.postForObject(url, payload, String.class);
    }

    public void editMessage(String chatId, Integer messageId, String newText) {
        final var url = String.format("https://api.telegram.org/bot%s/editMessageText", botToken);

        Map<String, Object> keyboard = Map.of(
                "inline_keyboard", List.of(
                        List.of(Map.of("text", "⏱ Обновить", "callback_data", "refresh"))
                )
        );

        Map<String, Object> payload = Map.of(
                "chat_id", chatId,
                "message_id", messageId,
                "text", newText,
                "reply_markup", keyboard
        );

        restTemplate.postForObject(url, payload, String.class);
    }


}
