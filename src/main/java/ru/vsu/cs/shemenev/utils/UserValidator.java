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
            if (userDAO.getUserOnHandle(user.getHandle()) != null) {
                errors.rejectValue("handle", "", "User with this handle already exists");
            }
            if (userDAO.getUserOnPassword(user.getPassword()) != null) {
                errors.rejectValue("password", "", "User with this password already exist");
            }
        } else if (action == Action.LOGIN) {
            if (userDAO.getUserOnHandle(user.getHandle()) == null) {
                errors.rejectValue("handle", "", "User with this handle does not exists");
                return;
            }
            if (userDAO.getUserOnPassword(user.getPassword()) == null) {
                errors.rejectValue("password", "", "Invalid password");
            }
        } else if (action == Action.EDIT) {
            if (userDAO.getUserOnHandle(user.getHandle()) != null) {
                errors.rejectValue("handle", "", "Use a new and unique handle");
            }
        }
    }
}
