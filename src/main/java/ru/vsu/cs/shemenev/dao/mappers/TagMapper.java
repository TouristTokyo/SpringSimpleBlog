package ru.vsu.cs.shemenev.dao.mappers;

import org.springframework.jdbc.core.RowMapper;
import ru.vsu.cs.shemenev.models.Tag;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TagMapper implements RowMapper<Tag> {
    @Override
    public Tag mapRow(ResultSet rs, int rowNum) throws SQLException {
        Tag tag = new Tag();
        tag.setId(rs.getInt("tag_id"));
        tag.setTagWorld(rs.getString("tag_world"));
        tag.setPostId(rs.getInt("post_id"));

        return tag;
    }
}
