package org.axzarian.timebot.service;

import org.axzarian.timebot.model.dto.UserDto;
import org.telegram.telegrambots.meta.api.objects.Update;

public interface TelegramUpdateService {

    boolean hasTextMessage(Update update);

    Long getMessageSenderId(Update update);

    String getMessageText(Update update);

    Long getMessageChatId(Update update);

    String getCallbackData(Update update);

    Long getCallbackChatId(Update update);

    Integer getCallbackMessageId(Update update);

    Long getCallbackSenderId(Update update);

    String getMessageFromFirstName(Update update);

    String getMessageFromLastName(Update update);

    String getMessageFromUserName(Update update);

    UserDto getUserDtoFromMessageUpdate(Update update);


}
