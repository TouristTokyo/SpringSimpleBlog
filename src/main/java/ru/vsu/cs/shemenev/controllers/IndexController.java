package ru.vsu.cs.shemenev.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import ru.vsu.cs.shemenev.dao.UserDAO;
import ru.vsu.cs.shemenev.models.User;
import ru.vsu.cs.shemenev.utils.Action;
import ru.vsu.cs.shemenev.utils.UserValidator;

import javax.validation.Valid;

@Controller
public class IndexController {
    private final UserDAO userDAO;
    private final UserValidator userValidator;

    @Autowired
    public IndexController(UserDAO users, UserValidator userValidator) {
        this.userDAO = users;
        this.userValidator = userValidator;
    }

    @GetMapping("/")
    public String index(Model model) {
        userDAO.signOut();
        model.addAttribute("user", new User());
        return "authorization";
    }

    @GetMapping("/menu")
    public String menu() {
        if (!userDAO.isLogin()) {
            return "authorization";
        }
        return "menu";
    }

    @GetMapping("/users")
    public String userShow(Model model) {
        if (!userDAO.isLogin()) {
            return index(model);
        }
        model.addAttribute("users", userDAO.getUsers());
        return "users";
    }

    @PostMapping(value = "/menu", params = {"register"})
    public String create(@ModelAttribute("user") @Valid User user, BindingResult bindingResult) {
        userValidator.setAction(Action.REGISTER);
        userValidator.validate(user, bindingResult);
        if (bindingResult.hasErrors()) {
            return "authorization";
        }
        userDAO.adduser(user);
        userDAO.signIn(user.getHandle());
        return "redirect:/menu";
    }

    @PostMapping(value = "/menu", params = {"login"})
    public String signIn(@ModelAttribute("user") @Valid User user, BindingResult bindingResult) {
        userValidator.setAction(Action.LOGIN);
        userValidator.validate(user, bindingResult);
        if (bindingResult.hasErrors()) {
            return "authorization";
        }
        userDAO.signIn(user.getHandle());
        return "redirect:/menu";
    }

    @GetMapping("/users/{id}")
    public String showUser(@PathVariable("id") int id, Model model) {
        if (!userDAO.isLogin()) {
            return index(model);
        }
        User user = userDAO.getUserOnId(id);
        if (user == null) {
            System.out.println("NOT FOUND");
        }
        model.addAttribute("user", user);
        model.addAttribute("currentUser", userDAO.getCurrentUser());
        return "showUser";
    }
}
