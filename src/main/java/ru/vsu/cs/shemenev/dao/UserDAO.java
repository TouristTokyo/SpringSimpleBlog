package ru.vsu.cs.shemenev.dao;

import org.springframework.stereotype.Component;
import ru.vsu.cs.shemenev.models.User;
import ru.vsu.cs.shemenev.utils.Security;

import java.util.ArrayList;
import java.util.List;

@Component
public class UserDAO {
    private static int countID = 0;
    private List<User> users;

    {
        users = new ArrayList<>();
    }

    public List<User> getUsers() {
        return users;
    }

    public boolean isExist(User user) {
        for (User person : users) {
            if (user.getPassword().equals(person.getPassword())) {
                return true;
            }
        }
        return false;
    }


    public void adduser(User user) {
        countID++;
        user.setId(countID);
        user.setPassword(Security.hashPassword(user.getPassword()));
        users.add(user);
        System.out.println(user.getPassword());
    }
}
