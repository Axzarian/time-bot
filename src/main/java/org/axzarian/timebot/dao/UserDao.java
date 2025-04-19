package org.axzarian.timebot.dao;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.axzarian.timebot.model.dto.UserDto;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class UserDao {

    private final JdbcTemplate       jdbcTemplate;
    private final RowMapper<UserDto> userMapper = BeanPropertyRowMapper.newInstance(UserDto.class);

    public List<UserDto> findAll() {
        return jdbcTemplate.query("SELECT * FROM user", userMapper);
    }

    public void save(UserDto userDto) {
        jdbcTemplate.update(
            "INSERT INTO user (telegram_id, first_name, last_name, user_name) VALUES (?, ?, ?, ?) ",
            userDto.getTelegramId(), userDto.getFirstName(), userDto.getLastName(), userDto.getUserName()
        );
    }


}
