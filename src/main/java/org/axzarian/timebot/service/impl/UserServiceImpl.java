package org.axzarian.timebot.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axzarian.timebot.converter.UserConverter;
import org.axzarian.timebot.dao.UserDao;
import org.axzarian.timebot.model.dto.UserDto;
import org.axzarian.timebot.service.TelegramUpdateService;
import org.axzarian.timebot.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserDao               userDao;
    private final TelegramUpdateService telegramUpdateService;


    @Override
    public void create(UserDto userDto) {


    }

    @Override
    @Transactional
    public void upsert(UserDto userDto) {
        userDao.upsert(UserConverter.toUser(userDto));
    }
}
