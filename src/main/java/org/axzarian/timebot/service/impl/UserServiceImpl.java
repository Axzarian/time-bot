package org.axzarian.timebot.service.impl;

import lombok.RequiredArgsConstructor;
import org.axzarian.timebot.dao.UserDao;
import org.axzarian.timebot.model.dto.UserDto;
import org.axzarian.timebot.service.UserService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserDao userDao;



    @Override
    public void create(UserDto userDto) {


    }
}
