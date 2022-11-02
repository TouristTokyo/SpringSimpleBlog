package ru.vsu.cs.shemenev.models;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class User {
    private int id;
    @NotEmpty(message = "Handle is empty")
    @Size(min = 2, message = "Minimal length = 2")
    private String handle;
    @NotEmpty(message = "Password is empty")
    @Size(min = 8, message = "Minimal length = 8")
    private String password;

    public User(int id, String handle, String password) {
        this.id = id;
        this.handle = handle;
        this.password = password;
    }

    public User() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getHandle() {
        return handle;
    }

    public void setHandle(String handle) {
        this.handle = handle;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
