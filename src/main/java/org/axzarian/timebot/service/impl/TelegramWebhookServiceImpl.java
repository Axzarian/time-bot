package org.axzarian.timebot.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axzarian.timebot.sender.TelegramSender;
import org.axzarian.timebot.service.TelegramUpdateService;
import org.axzarian.timebot.service.TelegramWebhookService;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;

@Slf4j
@Service
@RequiredArgsConstructor
public class TelegramWebhookServiceImpl implements TelegramWebhookService {

    private final TelegramUpdateService telegramUpdateService;
    private final TelegramSender        telegramSender;


    @Override
    public void onUpdateEvent(Update update) {

        if (telegramUpdateService.hasTextMessage(update)) {

            final var chatId   = telegramUpdateService.getMessageChatId(update);
            final var text     = telegramUpdateService.getMessageText(update);
            final var senderId = telegramUpdateService.getMessageSenderId(update);

            switch (text) {
                case "/start" -> log.info("Update: {}", update);
                case "/time" ->telegramSender.sendWithButtons(chatId.toString(), text);
            }
        }

    }
}
