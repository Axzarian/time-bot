package org.axzarian.timebot.converter;

import org.axzarian.timebot.model.dto.UserDto;
import org.axzarian.timebot.model.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserConverter {

    public static UserDto toUserDto(User user) {
        return UserDto.builder()
                      .id(user.getId())
                      .telegramId(user.getTelegramId())
                      .firstName(user.getFirstName())
                      .lastName(user.getLastName())
                      .userName(user.getUserName())
                      .build();
    }

    public static User toUser(UserDto userDto) {
        return User.builder()
                   .id(userDto.getId())
                   .telegramId(userDto.getTelegramId())
                   .firstName(userDto.getFirstName())
                   .lastName(userDto.getLastName())
                   .userName(userDto.getUserName())
                   .build();
    }
}
