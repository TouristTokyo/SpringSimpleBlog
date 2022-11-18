package ru.vsu.cs.shemenev.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.vsu.cs.shemenev.dao.mappers.TagMapper;
import ru.vsu.cs.shemenev.models.Tag;

@Component
public class TagDAO {
    private final JdbcTemplate jdbcTemplate;


    @Autowired
    public TagDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void addTag(int postId, Tag tag) {
        String sqlCommand = "insert into tags(tag_world, post_id) values(?,?)";
        jdbcTemplate.update(sqlCommand, tag.getTagWorld(), postId);
    }

    public Tag getTagByPostId(int id){
        String sqlCommand = "select * from tags where post_id=?";
        return jdbcTemplate.query(sqlCommand, new Object[]{id}, new TagMapper())
                .stream()
                .findAny()
                .orElse(null);
    }

}
