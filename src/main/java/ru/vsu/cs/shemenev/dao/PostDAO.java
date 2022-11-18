package ru.vsu.cs.shemenev.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import ru.vsu.cs.shemenev.dao.mappers.LastPostIdMapper;
import ru.vsu.cs.shemenev.dao.mappers.PostMapper;
import ru.vsu.cs.shemenev.models.Post;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Component
@PropertySource("classpath:application.properties")
public class PostDAO {
    private final JdbcTemplate jdbcTemplate;
    @Value("${upload.path}")
    private String uploadPath;

    @Autowired
    public PostDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Post> getPosts() {
        String sqlCommand = "select * from posts";
        return jdbcTemplate.query(sqlCommand, new PostMapper());
    }

    public void addPost(Post post, MultipartFile file, int userId) {
        String imagePath = null;
        if (file != null && !file.getOriginalFilename().isEmpty()) {
            imagePath = saveImageInDirectory(file);
        }
        String sqlCommand = "insert into posts(title, body, image, user_id) values(?,?,?,?)";
        jdbcTemplate.update(sqlCommand, post.getTitle(), post.getBody(), imagePath, userId);
    }

    private String saveImageInDirectory(MultipartFile file) {
        File uploadDir = new File(uploadPath);
        System.out.println(uploadPath);
        if (!uploadDir.exists()) {
            uploadDir.mkdir();
        }
        String resultFileName = UUID.randomUUID() + file.getOriginalFilename();
        try {
            file.transferTo(new File(uploadPath + "/" + resultFileName));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return resultFileName;
    }

    public Integer getLastPostId() {
        String sqlCommand = "select last_value from posts_post_id_seq";
        return jdbcTemplate.query(sqlCommand, new LastPostIdMapper())
                .stream()
                .findAny()
                .orElse(null);
    }

    public Post getPostById(int id) {
        String sqlCommand = "select * from posts where post_id=?";
        return jdbcTemplate.query(sqlCommand, new Object[]{id}, new PostMapper())
                .stream()
                .findAny()
                .orElse(null);
    }

    public void deletePost(int id) {
        String sqlCommand = "delete from posts where post_id=?";
        if (deleteImageLocalDirectory(id)) {
            jdbcTemplate.update(sqlCommand, id);
        }
    }

    private boolean deleteImageLocalDirectory(int id) {
        Post post = getPostById(id);
        if(post.getImagePath()==null){
            return true;
        }
        File file = new File(uploadPath + "/" + post.getImagePath());
        return file.delete();
    }
}
