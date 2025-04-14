package org.axzarian.timebot.controller;

import lombok.RequiredArgsConstructor;
import org.axzarian.timebot.entity.Stopwatch;
import org.axzarian.timebot.sender.TelegramSender;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.telegram.telegrambots.meta.api.objects.Update;

@RestController
@RequestMapping("/telegram")
@RequiredArgsConstructor
public class TelegramWebhookController {

    private final TelegramSender telegramSender;
    private final Stopwatch stopwatch;


    @PostMapping
    public ResponseEntity<String> onUpdateReceived(@RequestBody Update update) {

        if (update.hasMessage() && update.getMessage().hasText()) {

            final var text = update.getMessage().getText();
            final var chatId = update.getMessage().getChatId();

            if ("/time".equals(text)) {
                telegramSender.sendWithButtons(chatId.toString(), stopwatch.formatUptime());
            }
        }

        if (update.hasCallbackQuery()) {
            final var data = update.getCallbackQuery().getData();
            final var chatId = update.getCallbackQuery().getMessage().getChatId();
            final var messageId = update.getCallbackQuery().getMessage().getMessageId();

            if ("refresh".equals(data)) {
                telegramSender.editMessage(chatId.toString(), messageId, stopwatch.formatUptime());
            }
        }
        return ResponseEntity.ok("OK");
    }
}
