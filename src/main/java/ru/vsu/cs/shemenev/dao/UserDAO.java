package ru.vsu.cs.shemenev.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.vsu.cs.shemenev.models.User;

import java.util.List;

@Component
public class UserDAO {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public UserDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<User> getUsers() {
        String sqlCommand = "select * from users";
        return jdbcTemplate.query(sqlCommand, new BeanPropertyRowMapper<>(User.class));
    }

    public User isExistPassword(User user) {
        String sqlCommand = "select * from users where password=md5(?)";
        return jdbcTemplate.query(sqlCommand, new Object[]{user.getPassword()}, new BeanPropertyRowMapper<>(User.class))
                .stream()
                .findAny()
                .orElse(null);
    }

    public User isExistHandle(User user) {
        String sqlCommand = "select * from users where handle=?";
        return jdbcTemplate.query(sqlCommand, new Object[]{user.getHandle()}, new BeanPropertyRowMapper<>(User.class))
                .stream()
                .findAny()
                .orElse(null);
    }


    public void adduser(User user) {
        String sqlCommand = "insert into users(handle, password) values(?,md5(?))";
        jdbcTemplate.update(sqlCommand, user.getHandle(), user.getPassword());
    }
}
