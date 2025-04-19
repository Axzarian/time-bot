package org.axzarian.timebot.configuartion;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "telegram")
public class TelegramProperties {

    private String token;

    public String getBaseUrl() {
        return "https://api.telegram.org/bot" + token;
    }

    public String getSendMessageUrl() {
        return getBaseUrl() + "/sendMessage";
    }

    public String getEditMessageUrl() {
        return getBaseUrl() + "/editMessageText";
    }
}
