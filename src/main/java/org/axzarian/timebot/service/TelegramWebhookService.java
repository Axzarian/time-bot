package org.axzarian.timebot.service;

import org.telegram.telegrambots.meta.api.objects.Update;

public interface TelegramWebhookService {

    void onUpdateEvent(Update update);
}
