package org.axzarian.timebot.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axzarian.timebot.model.domain.Stopwatch;
import org.axzarian.timebot.sender.TelegramClient;
import org.axzarian.timebot.service.TelegramUpdateService;
import org.axzarian.timebot.service.TelegramWebhookService;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;

@Slf4j
@Service
@RequiredArgsConstructor
public class TelegramWebhookServiceImpl implements TelegramWebhookService {

    private final TelegramUpdateService telegramUpdateService;
    private final TelegramClient        telegramClient;
    private final Stopwatch             stopwatch;


    @Override
    public void onUpdateEvent(Update update) {

        if (telegramUpdateService.hasTextMessage(update)) {

            final var chatId   = telegramUpdateService.getMessageChatId(update).toString();
            final var text     = telegramUpdateService.getMessageText(update);
            final var senderId = telegramUpdateService.getMessageSenderId(update).toString();

            switch (text) {
                case "/start" -> log.info("Update: {}", update);
                case "/time" -> telegramClient.sendWithButtons(chatId, senderId,stopwatch.formatUptime());
            }
        }

        if (update.hasCallbackQuery()) {

            final var data      = telegramUpdateService.getCallbackData(update);
            final var chatId    = telegramUpdateService.getCallbackChatId(update).toString();
            final var messageId = telegramUpdateService.getCallbackMessageId(update);
            final var senderId = telegramUpdateService.getCallbackSenderId(update).toString();

            switch (data) {
                case "refresh" -> telegramClient.editMessage(chatId, messageId, senderId, stopwatch.formatUptime());
                case "reset" -> { stopwatch.reset(); telegramClient.editMessage(chatId, messageId, senderId, stopwatch.formatUptime());
                }
            }

        }

    }
}
