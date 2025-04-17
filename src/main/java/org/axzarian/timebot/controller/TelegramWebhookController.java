package org.axzarian.timebot.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axzarian.timebot.entity.Stopwatch;
import org.axzarian.timebot.sender.TelegramSender;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.telegram.telegrambots.meta.api.objects.Update;

@Slf4j
@RestController
@RequestMapping("/telegram")
@RequiredArgsConstructor
public class TelegramWebhookController {

    private final TelegramSender telegramSender;
    private final Stopwatch stopwatch;

    private static final String IVAN_ID = "";
    private static final String MY_ID = "1065966054";


    @PostMapping
    public ResponseEntity<String> onUpdateReceived(@RequestBody Update update) {

        var validUser = true;

        if (update.hasMessage() && update.getMessage().hasText()) {

            validUser = isValidUser(update.getMessage().getFrom().getId());

            if ("/start".equals(update.getMessage().getText())) {
                log.info("Get update: {}", update);
            }

            final var text = update.getMessage().getText();
            final var chatId = update.getMessage().getChatId();

            if ("/time".equals(text) && validUser) {
                final var message = getMessage();
                telegramSender.sendWithButtonsForIvan(chatId.toString(), message);
            } else if ("/time".equals(text)) {
                final var message = getMessage();
                telegramSender.sendWithButtons(chatId.toString(), message);
            }
        }

        if (update.hasCallbackQuery()) {
            final var data = update.getCallbackQuery().getData();
            final var chatId = update.getCallbackQuery().getMessage().getChatId();
            final var messageId = update.getCallbackQuery().getMessage().getMessageId();

            if ("refresh".equals(data) && validUser ) {
                final var message = getMessage();
                telegramSender.editMessageForIvan(chatId.toString(), messageId, message);
            } else if ("refresh".equals(data)) {
                final var message = getMessage();
                telegramSender.editMessage(chatId.toString(), messageId, message);
            } else if ("reset".equals(data) && validUser) {
                stopwatch.reset();
                final var message = getMessage();
                telegramSender.editMessageForIvan(chatId.toString(), messageId, message);
            } else if ("reset".equals(data)) {
                stopwatch.reset();
                final var message = getMessage();
                telegramSender.editMessage(chatId.toString(), messageId, message);
            }
        }
        return ResponseEntity.ok("OK");
    }

    private boolean isValidUser(Long userId) {
        if (MY_ID.equals(userId.toString())) {
            return true;
        }
        return IVAN_ID.equals(userId.toString());
    }

    private String getMessage() {
        return "Газелька бегает без ремонта уже: %n%n %s ".formatted(stopwatch.formatUptime());
    }
}
