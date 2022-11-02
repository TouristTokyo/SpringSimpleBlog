package ru.vsu.cs.shemenev.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.vsu.cs.shemenev.dao.UserDAO;
import ru.vsu.cs.shemenev.models.User;

@Component
public class UserValidator implements Validator {
    private final UserDAO userDAO;
    private Action action;

    public void setAction(Action action) {
        this.action = action;
    }

    @Autowired
    public UserValidator(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return User.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        User user = (User) target;
        if (action == Action.REGISTER) {
            if (userDAO.isExistHandle(user) != null) {
                errors.rejectValue("handle", "", "User with this handle already exists");
            }
            if (userDAO.isExistPassword(user) != null) {
                errors.rejectValue("password", "", "User with this password already exist");
            }
        } else if (action == Action.LOGIN) {
            if (userDAO.isExistHandle(user) == null) {
                errors.rejectValue("handle", "", "User with this handle does not exists");
            }
            User userFind = userDAO.isExistPassword(user);
            if (userFind == null) {
                errors.rejectValue("password", "", "User with this password does not exist");
            } else if (!userFind.getHandle().equals(user.getHandle())) {
                errors.rejectValue("password", "", "Invalid handle or password");
            }
        }
    }
}
