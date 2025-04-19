package org.axzarian.timebot.sender;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class TelegramSender {

    private final RestTemplate telegramRestTemplate;

    private static final String SEND_MESSAGE_URL = "/sendMessage";
    private static final String EDIT_MESSAGE_URL = "/editMessageText";


    public void sendWithButtons(String chatId, String text) {

        Map<String, Object> keyboard = Map.of(
            "inline_keyboard", List.of(
                List.of(
                    Map.of("text", "⏱ Обновить", "callback_data", "refresh"),
                    Map.of("text", "❌Обнулить", "callback_data", "reset")
                )
            )
        );

        Map<String, Object> payload = Map.of(
            "chat_id", chatId,
            "text", text,
            "reply_markup", keyboard
        );

        telegramRestTemplate.postForObject(SEND_MESSAGE_URL, payload, String.class);

    }

    public void editMessage(String chatId, Integer messageId, String newText) {
        Map<String, Object> keyboard = Map.of(
            "inline_keyboard", List.of(
                List.of(
                    Map.of("text", "⏱ Обновить", "callback_data", "refresh"),
                    Map.of("text", "❌Обнулить", "callback_data", "reset")
                )
            )
        );

        Map<String, Object> payload = Map.of(
            "chat_id", chatId,
            "message_id", messageId,
            "text", newText,
            "reply_markup", keyboard
        );

        telegramRestTemplate.postForObject(EDIT_MESSAGE_URL, payload, String.class);
    }

}
