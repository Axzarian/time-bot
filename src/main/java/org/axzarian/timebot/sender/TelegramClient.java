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

        final var markup = getKeyboardMarkup();
        final var payload = TelegramMessageRequest.builder()
                                                  .chatId(chatId)
                                                  .text(text)
                                                  .replyMarkup(markup)
                                                  .build();

        final var entity = buildHttpRequest(payload);
        final var url    = buildUrl("sendMessage");

        telegramRestTemplate.postForObject(url, entity, String.class);
    }

    public void editMessage(String chatId, Integer messageId, String newText) {

        final var markup = getKeyboardMarkup();
        final var payload = TelegramMessageRequest.builder()
                                                  .chatId(chatId)
                                                  .text(newText)
                                                  .messageId(messageId)
                                                  .replyMarkup(markup)
                                                  .build();

        final var entity = buildHttpRequest(payload);
        final var url    = buildUrl("editMessageText");

        telegramRestTemplate.postForObject(url, entity, String.class);
    }

    protected InlineKeyboardMarkup getKeyboardMarkup() {

        final var firstRow  = List.of(getRefreshButton());
        final var secondRow = List.of(getResetButtont());

        return InlineKeyboardMarkup.builder()
                                   .keyboard(List.of(firstRow, secondRow))
                                   .build();
    }

    private static InlineKeyboardButton getResetButtont() {
        return InlineKeyboardButton.builder()
                                   .text("❌ Обнулить")
                                   .callbackData("reset")
                                   .build();
    }

    private static InlineKeyboardButton getRefreshButton() {
        return InlineKeyboardButton.builder()
                                   .text("⏱ Обновить")
                                   .callbackData("refresh")
                                   .build();
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
