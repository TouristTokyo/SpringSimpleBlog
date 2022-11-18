package ru.vsu.cs.shemenev.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.vsu.cs.shemenev.dao.mappers.PostMapper;
import ru.vsu.cs.shemenev.dao.mappers.UserMapper;
import ru.vsu.cs.shemenev.models.Post;
import ru.vsu.cs.shemenev.models.User;

import java.util.List;

@Component
public class UserDAO {
    private final JdbcTemplate jdbcTemplate;
    private boolean isLogin = false;
    private User currentUser;

    @Autowired
    public UserDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<User> getUsers() {
        String sqlCommand = "select * from users";
        return jdbcTemplate.query(sqlCommand, new UserMapper());
    }

    public User getUserOnPassword(String password) {
        String sqlCommand = "select * from users where password=md5(?)";
        return jdbcTemplate.query(sqlCommand, new Object[]{password}, new UserMapper())
                .stream()
                .findAny()
                .orElse(null);
    }

    public User getUserOnHandle(String handle) {
        String sqlCommand = "select * from users where handle=?";
        return jdbcTemplate.query(sqlCommand, new Object[]{handle}, new UserMapper())
                .stream()
                .findAny()
                .orElse(null);
    }


    public void adduser(User user) {
        String sqlCommand = "insert into users(handle, password) values(?,md5(?))";
        jdbcTemplate.update(sqlCommand, user.getHandle(), user.getPassword());
    }

    public User getUserOnId(int id) {
        String sqlCommand = "select * from users where user_id=?";
        return jdbcTemplate.query(sqlCommand, new Object[]{id}, new UserMapper())
                .stream()
                .findAny()
                .orElse(null);
    }

    public boolean isLogin() {
        return isLogin;
    }

    public void signIn(String handle) {
        assert currentUser == null;
        currentUser = getUserOnHandle(handle);
        isLogin = true;
    }

    public void signOut() {
        currentUser = null;
        isLogin = false;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public void editHandle(User user, int id) {
        String sqlCommand = "update users set handle=? where user_id=?";
        jdbcTemplate.update(sqlCommand, user.getHandle(), id);
    }

    public void deleteUser(int id) {
        String sqlCommand = "delete from users where user_id=?";
        jdbcTemplate.update(sqlCommand, id);
    }

    public List<Post> getUserPosts(int id){
        String sqlCommand = "select * from posts where user_id = ?";
        return jdbcTemplate.query(sqlCommand, new Object[]{id}, new PostMapper());
    }
}
