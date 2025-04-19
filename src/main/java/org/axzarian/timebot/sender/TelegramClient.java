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
import org.springframework.web.util.UriComponentsBuilder;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

@Component
@RequiredArgsConstructor
public class TelegramClient {

    private final RestTemplate       telegramRestTemplate;
    private final TelegramProperties telegramProperties;

    public void sendWithButtons(String chatId, String text) {

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

        final var request = TelegramMessageRequest.builder()
                                                  .chatId(chatId)
                                                  .text(text)
                                                  .replyMarkup(markup)
                                                  .build();

        final var payload = buildHttpRequest(request);
        final var url     = buildUrl("sendMessage");

        telegramRestTemplate.postForObject(url, payload, String.class);
    }

    public void editMessage(String chatId, Integer messageId, String newText) {
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

        final var request = TelegramMessageRequest.builder()
                                                  .chatId(chatId)
                                                  .text(newText)
                                                  .messageId(messageId)
                                                  .replyMarkup(markup)
                                                  .build();

        final var payload = buildHttpRequest(request);
        final var url     = buildUrl("editMessageText");

        telegramRestTemplate.postForObject(url, payload, String.class);
    }

    private HttpEntity<TelegramMessageRequest> buildHttpRequest(TelegramMessageRequest telegramMessageRequest) {
        var headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return new HttpEntity<>(telegramMessageRequest, headers);
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
