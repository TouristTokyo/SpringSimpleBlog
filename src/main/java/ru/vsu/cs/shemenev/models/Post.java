package ru.vsu.cs.shemenev.models;

import javax.validation.constraints.Size;

public class Post {
    private int id;
    @Size(min = 2, message = "Minimal length = 2")
    private String title;
    @Size(min = 2, message = "Minimal length = 2")
    private String body;
    private String imagePath;
    private  int userId;

    public Post(int id, String title, String body, String image, int userId) {
        this.id = id;
        this.title = title;
        this.body = body;
        this.imagePath = image;
        this.userId = userId;
    }

    public Post(){}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
   }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
