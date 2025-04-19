package org.axzarian.timebot.service.impl;

import org.axzarian.timebot.model.dto.UserDto;
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

    @Override
    public String getCallbackData(Update update) {
        return update.getCallbackQuery().getData();
    }

    @Override
    public Long getCallbackChatId(Update update) {
        return update.getCallbackQuery().getMessage().getChatId();
    }

    @Override
    public Integer getCallbackMessageId(Update update) {
        return update.getCallbackQuery().getMessage().getMessageId();
    }

    @Override
    public Long getCallbackSenderId(Update update) {
        return update.getCallbackQuery().getFrom().getId();
    }

    @Override
    public String getMessageFromFirstName(Update update) {
        return update.getMessage().getFrom().getFirstName();
    }

    @Override
    public String getMessageFromLastName(Update update) {
        return update.getMessage().getFrom().getLastName();
    }

    @Override
    public String getMessageFromUserName(Update update) {
        return update.getMessage().getFrom().getUserName();
    }

    @Override
    public UserDto getUserDtoFromMessageUpdate(Update update) {
        return UserDto.builder()
                      .telegramId(getMessageSenderId(update))
                      .firstName(getMessageFromFirstName(update))
                      .lastName(getMessageFromLastName(update))
                      .userName(getMessageFromUserName(update))
                      .build();
    }


}
