package ru.vsu.cs.shemenev.dao.mappers;

import org.springframework.jdbc.core.RowMapper;
import ru.vsu.cs.shemenev.models.Comment;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CommentMapper implements RowMapper<Comment> {
    @Override
    public Comment mapRow(ResultSet rs, int rowNum) throws SQLException {
        Comment comment = new Comment();

        comment.setId(rs.getInt("comment_id"));
        comment.setText(rs.getString("text"));
        comment.setPostId(rs.getInt("post_id"));
        comment.setUserId(rs.getInt("user_id"));

        return comment;
    }
}
