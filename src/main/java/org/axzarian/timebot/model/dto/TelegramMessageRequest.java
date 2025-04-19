package org.axzarian.timebot.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TelegramMessageRequest {

    private String text;

    @JsonProperty("chat_id")
    private String chatId;


    @JsonProperty("message_id")
    private Integer messageId;

    @JsonProperty("reply_markup")
    private InlineKeyboardMarkup replyMarkup;
}
