package org.axzarian.timebot.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axzarian.timebot.dao.UserDao;
import org.axzarian.timebot.model.dto.UserDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserDao userDao;

    @PostMapping
    public ResponseEntity<Void> create() {
        final var user = UserDto.builder()
                                .telegramId(123L)
                                .firstName("John")
                                .lastName("Doe")
                                .userName("johndoe")
                                .build();

        userDao.save(user);

        userDao.findAll().forEach(userDto -> log.info(userDto.toString()));

        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
