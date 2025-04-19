package org.axzarian.timebot.configuartion;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.DefaultUriBuilderFactory;

@Configuration
@RequiredArgsConstructor
public class RestTemplateConfig {

    private final TelegramProperties telegramProperties;

    @Bean
    public RestTemplate telegramRestTemplate(RestTemplateBuilder builder) {
        return builder
            .uriTemplateHandler(new DefaultUriBuilderFactory(telegramProperties.getBaseUrl()))
            .build();
    }
}
