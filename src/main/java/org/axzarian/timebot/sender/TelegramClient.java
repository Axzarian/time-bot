package org.axzarian.timebot.sender;

import lombok.RequiredArgsConstructor;
import org.axzarian.timebot.configuartion.TelegramProperties;
import org.axzarian.timebot.model.dto.TelegramMessageRequest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;
import org.springframework.web.util.UriComponentsBuilder;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

@Component
@RequiredArgsConstructor
public class TelegramClient {

    private final RestTemplate       telegramRestTemplate;
    private final TelegramProperties telegramProperties;


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

        final var url = buildUrl("sendMessage");

        telegramRestTemplate.postForObject(url, payload, String.class);
    }

    public void sendWithButtonsClasses(String chatId, String text) {

        final var refreshButton = InlineKeyboardButton.builder()
                                                      .text("⏱ Обновить")
                                                      .callbackData("refresh")
                                                      .build();

        final var resetButtont = InlineKeyboardButton.builder()
                                                     .text("❌ Обнулить")
                                                     .callbackData("reset")
                                                     .build();

        final var markup = InlineKeyboardMarkup.builder()
                                              .keyboard(List.of(List.of(refreshButton, resetButtont)))
                                              .build();

        final var request = new TelegramMessageRequest(chatId, text, markup);


        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<TelegramMessageRequest> entity = new HttpEntity<>(request, headers);

        String url = buildUrl("sendMessage");

        telegramRestTemplate.postForObject(url, entity, String.class);
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

        final var url = buildUrl("editMessageText");

        telegramRestTemplate.postForObject(url, payload, String.class);
    }

    private String buildUrl(String method) {
        return UriComponentsBuilder.newInstance()
                                   .scheme("https")
                                   .host("api.telegram.org")
                                   .pathSegment("bot" + telegramProperties.getToken(), method)
                                   .build()
                                   .toUriString();
    }

}
