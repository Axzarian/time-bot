package org.axzarian.timebot.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TelegramMessageRequest {

    @JsonProperty("chat_id")
    private String               chatId;

    private String               text;

    @JsonProperty("reply_markup")
    private InlineKeyboardMarkup replyMarkup;
}
