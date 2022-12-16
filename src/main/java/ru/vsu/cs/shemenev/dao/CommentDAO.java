package ru.vsu.cs.shemenev.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.vsu.cs.shemenev.dao.mappers.CommentMapper;
import ru.vsu.cs.shemenev.models.Comment;

import java.util.List;

@Component
public class CommentDAO {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public CommentDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void addComment(Comment comment, int postId, int userId) {
        String sqlCommand = "insert into comments(text, post_id, user_id) values(?, ?, ?)";
        jdbcTemplate.update(sqlCommand, comment.getText(), postId, userId);
    }

    public List<Comment> getComments(int id) {
        String sqlCommand = "select * from comments where post_id = ?";
        return jdbcTemplate.query(sqlCommand, new Object[]{id}, new CommentMapper());
    }

    public void deleteComment(int id){
        String sqlCommand = "delete from comments where comment_id = ?";
        jdbcTemplate.update(sqlCommand, id);
    }
}
