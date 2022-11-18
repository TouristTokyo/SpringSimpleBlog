package ru.vsu.cs.shemenev.dao.mappers;

import org.springframework.jdbc.core.RowMapper;
import ru.vsu.cs.shemenev.models.Post;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PostMapper implements RowMapper<Post> {
    @Override
    public Post mapRow(ResultSet rs, int rowNum) throws SQLException {
        Post post = new Post();

        post.setId(rs.getInt("post_id"));
        post.setTitle(rs.getString("title"));
        post.setBody(rs.getString("body"));
        post.setImagePath(rs.getString("image"));
        post.setUserId(rs.getInt("user_id"));

        return post;
    }
}
