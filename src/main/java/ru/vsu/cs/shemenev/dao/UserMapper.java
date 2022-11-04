package ru.vsu.cs.shemenev.dao;

import org.springframework.jdbc.core.RowMapper;
import ru.vsu.cs.shemenev.models.User;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserMapper implements RowMapper<User> {
    @Override
    public User mapRow(ResultSet rs, int rowNum) throws SQLException {
        User user = new User();
        user.setId(rs.getInt("user_id"));
        user.setHandle(rs.getString("handle"));
        user.setPassword(rs.getString("password"));
        return user;
    }
}
