package org.axzarian.timebot.dao;

import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.axzarian.timebot.model.entity.User;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class UserDao {

    private final JdbcTemplate               jdbcTemplate;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private final RowMapper<User>            userMapper = BeanPropertyRowMapper.newInstance(User.class);

    public List<User> findAll() {
        return jdbcTemplate.query("SELECT * FROM users", userMapper);
    }

    public boolean save(User user) {
        final var rows = jdbcTemplate.update(
            "INSERT INTO users (telegram_id, first_name, last_name, user_name) VALUES (?, ?, ?, ?) ",
            user.getTelegramId(), user.getFirstName(), user.getLastName(), user.getUserName()
        );
        return rows > 0;
    }

    public User findByTelegramId(Long telegramId) {
        return jdbcTemplate.queryForObject("SELECT * FROM users WHERE users.telegram_id = ?", userMapper, telegramId);
    }

    public void upsert(User user) {
        String sql = """
                     INSERT INTO users (telegram_id, first_name, last_name, user_name)
                     VALUES (:telegramId, :firstName, :lastName, :userName)
                     ON CONFLICT(telegram_id) DO UPDATE SET
                         first_name = excluded.first_name,
                         last_name = excluded.last_name,
                         user_name = excluded.user_name
                     """;

        Map<String, Object> params = Map.of(
            "telegramId", user.getTelegramId(),
            "firstName", user.getFirstName(),
            "lastName", user.getLastName(),
            "userName", user.getUserName()
        );

        namedParameterJdbcTemplate.update(sql, params);
    }


}
