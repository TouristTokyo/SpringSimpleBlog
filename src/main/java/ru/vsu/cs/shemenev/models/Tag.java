package ru.vsu.cs.shemenev.models;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class Tag {
    private int id;
    @Size(min = 2, message = "Minimal length = 2")
    @Pattern(regexp = "#[A-Za-zА-Яа-я0-9]+", message = "Incorrect tag. Example: #tag, #my1tag, #FirstTag")
    private String tagWorld;
    private int postId;

    public Tag(int id, String tagWorld, int postId) {
        this.id = id;
        this.tagWorld = tagWorld;
        this.postId = postId;
    }

    public Tag() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTagWorld() {
        return tagWorld;
    }

    public void setTagWorld(String tagWorld) {
        this.tagWorld = tagWorld;
    }

    public int getPostId() {
        return postId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }
}
