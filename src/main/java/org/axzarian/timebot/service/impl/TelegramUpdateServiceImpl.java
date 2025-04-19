package org.axzarian.timebot.service.impl;

import org.axzarian.timebot.service.TelegramUpdateService;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;

@Service
public class TelegramUpdateServiceImpl implements TelegramUpdateService {

    @Override
    public boolean hasTextMessage(Update update) {
        return (update.hasMessage() && update.getMessage().hasText());
    }

    @Override
    public Long getMessageSenderId(Update update) {
        return update.getMessage().getFrom().getId();
    }

    @Override
    public String getMessageText(Update update) {
        return update.getMessage().getText();
    }

    @Override
    public Long getMessageChatId(Update update) {
        return update.getMessage().getChatId();
    }


}
